package icu.weboys.fileview.boot.handler;

import icu.weboys.fileview.boot.config.FViewConfig;
import icu.weboys.fileview.boot.emu.TpDefinition;
import icu.weboys.fileview.boot.impl.IView;
import icu.weboys.fileview.boot.op.DefaultReturnProcessor;
import icu.weboys.fileview.boot.op.view.DefaultViewImpl;
import icu.weboys.fileview.boot.util.OpenOfficeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;


@Component
public class ApplicationRunHandler implements ApplicationRunner {

    @Autowired
    FViewConfig fViewConfig;
    public final String PACKAGE_NAME = "icu.weboys.fileview.boot.op.view";

    @Override
    public void run(ApplicationArguments args) throws Exception {
       init();
    }
    public void init() throws IOException {
        if (!checkConfigEmp())
            return;
        definition();    
        filePath();
        bindViewThemeConfig();
        bindProcessorConfig();
        openOfficeConfig();
    }

    // 判断是否正常启动
    public Boolean checkConfigEmp() {

        if (fViewConfig.getRoot() == null || fViewConfig.getOffice() == null) {
            System.out.println(
                    "OnlineFileView is not Start...,Please set the root and office path in the application.properties");
            return false;
        }


        return true;
    }


    // 绑定文件格式预览模板
    public void bindViewThemeConfig() {
        // 校验是否含有开启预览格式的文件格式
        String[] tps = fViewConfig.getRootImgTps() == null ? new String[0] : fViewConfig.getRootImgTps().split(",");
        for (String tp : tps) {
            if (tp.equals("|") && !tp.endsWith("|")) {
                TpDefinition.ENABLE_IMAGE_VIEW_TYPE.put(tp.split("=")[0].toLowerCase(), tp.split("=")[1]);
                continue;
            }
            // 如果不含有，使用默认主题
            TpDefinition.ENABLE_IMAGE_VIEW_TYPE.put(tp, "default.ftl");
        }
    }

    // 启动openOffice
    public void openOfficeConfig() {
        // Open OpenOffice
        OpenOfficeUtils.open(fViewConfig);
    }
    
    // 绑定处理实现类
    public void bindProcessorConfig() {
        // 设置返回内容处理器
        TpDefinition.RETURN_PROCESSOR = new DefaultReturnProcessor();
        Set<String> set = TpDefinition.APPLICATION_TYPE.keySet();
        IView defaultView = new DefaultViewImpl();

        // 此处改个版。。。改成查找注解的方式
        // 插件内置解析view 优先级最低，如果没找到自定义的，则使用默认的
        // 【runner内，还没找到什么可靠的可以获取到某注解的方法，先继续反射插件内的实现类】
        for (String t : set) {
            char[] chars = t.toLowerCase(Locale.ROOT).toCharArray();
            if (chars[0] >= 97) {
                chars[0] -= 32;
            }
            t = String.valueOf(chars);
            try {
                Class<?> clazz = Class.forName(String.format("%s.%sViewImpl", PACKAGE_NAME, t));
                IView iv = (IView) clazz.newInstance();
                TpDefinition.APPLICATION_IVIEW.put(t.toLowerCase(Locale.ROOT), iv);
            } catch (Exception ex) {
                TpDefinition.APPLICATION_IVIEW.put(t.toLowerCase(Locale.ROOT), defaultView);
            }
        }
        System.out.print(String.format("Currently, online preview only supports the following file types: %s",
                String.join(",", TpDefinition.APPLICATION_IVIEW.keySet())));
    }

    public void filePath() throws IOException {
        // 转换路径格式
        if (fViewConfig.getRootDir() != null) {
            TpDefinition.ROOT_PATH = fViewConfig.getRootDir().replaceAll("\\\\", "/");
            if (!TpDefinition.ROOT_PATH.endsWith("/")) {
                TpDefinition.ROOT_PATH += "/";
            }
        } else {
            TpDefinition.ROOT_PATH = this.getClass().getResource("/").getPath().substring(1) + "/" + "filetemp/";
        }

        if (!new File(TpDefinition.ROOT_PATH).exists()) {
            new File(TpDefinition.ROOT_PATH).mkdirs();
        }
        // 创建相应的文件夹
        TpDefinition.FILE_STATIC_PATH = TpDefinition.ROOT_PATH + "static/";
        String THEME_PATH = TpDefinition.ROOT_PATH + "theme/";
        if (!new File(TpDefinition.FILE_STATIC_PATH).exists()) {
            new File(TpDefinition.FILE_STATIC_PATH).mkdirs();
        }
        if (!new File(THEME_PATH).exists()) {
            new File(THEME_PATH).mkdirs();
        }

        //定义模板引擎导出的编码格式
        TpDefinition.FTL_CONFIGURATION.setDefaultEncoding("UTF-8");
        TpDefinition.FTL_CONFIGURATION.setDirectoryForTemplateLoading(new File(THEME_PATH));
    }

    public void definition() {
        TpDefinition.APPLICATION_TYPE.put("jpg", "image/jpeg");
        TpDefinition.APPLICATION_TYPE.put("jpeg", "image/jpeg");
        TpDefinition.APPLICATION_TYPE.put("png", "image/png");
        TpDefinition.APPLICATION_TYPE.put("gif", "image/gif");
        TpDefinition.APPLICATION_TYPE.put("bmp", "image/bmp");
        TpDefinition.APPLICATION_TYPE.put("jpeg", "image/jpeg");
        TpDefinition.APPLICATION_TYPE.put("ico", "image/x-icon");
        TpDefinition.APPLICATION_TYPE.put("tif", "image/tiff");
        TpDefinition.APPLICATION_TYPE.put("tiff", "image/tiff");
        TpDefinition.APPLICATION_TYPE.put("svg", "image/svg+xml");
        TpDefinition.APPLICATION_TYPE.put("svgz", "image/svg+xml");
        TpDefinition.APPLICATION_TYPE.put("webp", "image/webp");
        TpDefinition.APPLICATION_TYPE.put("pdf", "application/pdf");
        TpDefinition.APPLICATION_TYPE.put("ps", "application/postscript");
        TpDefinition.APPLICATION_TYPE.put("ai", "application/postscript");
        TpDefinition.APPLICATION_TYPE.put("eps", "application/postscript");
        TpDefinition.APPLICATION_TYPE.put("psd", "application/octet-stream");
        TpDefinition.APPLICATION_TYPE.put("zip", "application/zip");
        TpDefinition.APPLICATION_TYPE.put("rar", "application/x-rar-compressed");
        TpDefinition.APPLICATION_TYPE.put("7z", "application/x-7z-compressed");
        TpDefinition.APPLICATION_TYPE.put("tar", "application/x-tar");
        TpDefinition.APPLICATION_TYPE.put("gz", "application/x-gzip");
        TpDefinition.APPLICATION_TYPE.put("bz2", "application/x-bzip2");
        TpDefinition.APPLICATION_TYPE.put("doc", "application/msword");
        TpDefinition.APPLICATION_TYPE.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        TpDefinition.APPLICATION_TYPE.put("xls", "application/vnd.ms-excel");
        TpDefinition.APPLICATION_TYPE.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        TpDefinition.APPLICATION_TYPE.put("ppt", "application/vnd.ms-powerpoint");
        TpDefinition.APPLICATION_TYPE.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        TpDefinition.APPLICATION_TYPE.put("odt", "application/vnd.oasis.opendocument.text");
        TpDefinition.APPLICATION_TYPE.put("ods", "application/vnd.oasis.opendocument.spreadsheet");
        TpDefinition.APPLICATION_TYPE.put("odp", "application/vnd.oasis.opendocument.presentation");
        TpDefinition.APPLICATION_TYPE.put("sxw", "application/vnd.sun.xml.writer");
        TpDefinition.APPLICATION_TYPE.put("stw", "application/vnd.sun.xml.writer.template");
        TpDefinition.APPLICATION_TYPE.put("sxc", "application/vnd.sun.xml.calc");
        TpDefinition.APPLICATION_TYPE.put("stc", "application/vnd.sun.xml.calc.template");
        TpDefinition.APPLICATION_TYPE.put("sxd", "application/vnd.sun.xml.draw");
        TpDefinition.APPLICATION_TYPE.put("std", "application/vnd.sun.xml.draw.template");
        TpDefinition.APPLICATION_TYPE.put("sxi", "application/vnd.sun.xml.impress");
        TpDefinition.APPLICATION_TYPE.put("sti", "application/vnd.sun.xml.impress.template");
        TpDefinition.APPLICATION_TYPE.put("sxg", "application/vnd.sun.xml.writer.global");
        TpDefinition.APPLICATION_TYPE.put("sxm", "application/vnd.sun.xml.math");
        TpDefinition.APPLICATION_TYPE.put("tar.gz", "application/x-gtar");
        TpDefinition.APPLICATION_TYPE.put("tgz", "application/x-gtar");
        TpDefinition.APPLICATION_TYPE.put("tar.bz2", "application/x-bzip2");
        TpDefinition.APPLICATION_TYPE.put("tbz", "application/x-bzip2");
        TpDefinition.APPLICATION_TYPE.put("mp3", "audio/mpeg");
        TpDefinition.APPLICATION_TYPE.put("wav", "audio/x-wav");
        TpDefinition.APPLICATION_TYPE.put("aif", "audio/x-aiff");
        TpDefinition.APPLICATION_TYPE.put("aiff", "audio/x-aiff");
        TpDefinition.APPLICATION_TYPE.put("aifc", "audio/x-aiff");
        TpDefinition.APPLICATION_TYPE.put("ram", "audio/x-pn-realaudio");
        TpDefinition.APPLICATION_TYPE.put("rm", "audio/x-pn-realaudio");
        TpDefinition.APPLICATION_TYPE.put("ra", "audio/x-realaudio");
        TpDefinition.APPLICATION_TYPE.put("rmm", "audio/x-pn-realaudio");
        TpDefinition.APPLICATION_TYPE.put("rmp", "audio/x-pn-realaudio");
        TpDefinition.APPLICATION_TYPE.put("m3u", "audio/x-mpegurl");
        TpDefinition.APPLICATION_TYPE.put("wma", "audio/x-ms-wma");
        TpDefinition.APPLICATION_TYPE.put("wax", "audio/x-ms-wax");
        TpDefinition.APPLICATION_TYPE.put("ogg", "audio/ogg");
        TpDefinition.APPLICATION_TYPE.put("midi", "audio/midi");
        TpDefinition.APPLICATION_TYPE.put("mid", "audio/midi");
        TpDefinition.APPLICATION_TYPE.put("kar", "audio/midi");
        TpDefinition.APPLICATION_TYPE.put("mp4a", "audio/mp4");
        TpDefinition.APPLICATION_TYPE.put("m4a", "audio/mp4");
        TpDefinition.APPLICATION_TYPE.put("m4b", "audio/mp4");
        TpDefinition.APPLICATION_TYPE.put("m4p", "audio/mp4");
        TpDefinition.APPLICATION_TYPE.put("mpga", "audio/mpeg");
        TpDefinition.APPLICATION_TYPE.put("mp2", "audio/mpeg");
        TpDefinition.APPLICATION_TYPE.put("mp2a", "audio/mpeg");
        TpDefinition.APPLICATION_TYPE.put("m2a", "audio/mpeg");
        TpDefinition.APPLICATION_TYPE.put("m3a", "audio/mpeg");
        TpDefinition.APPLICATION_TYPE.put("oga", "audio/ogg");
        TpDefinition.APPLICATION_TYPE.put("spx", "audio/ogg");
        TpDefinition.APPLICATION_TYPE.put("flac", "audio/flac");
        TpDefinition.APPLICATION_TYPE.put("aac", "audio/x-aac");
        TpDefinition.APPLICATION_TYPE.put("amr", "audio/amr");
        TpDefinition.APPLICATION_TYPE.put("mka", "audio/x-matroska");
        TpDefinition.APPLICATION_TYPE.put("wpl", "application/vnd.ms-wpl");
        TpDefinition.APPLICATION_TYPE.put("wvx", "video/x-ms-wvx");
        TpDefinition.APPLICATION_TYPE.put("avi", "video/x-msvideo");
        TpDefinition.APPLICATION_TYPE.put("movie", "video/x-sgi-movie");
        TpDefinition.APPLICATION_TYPE.put("mpv", "video/x-matroska");
        TpDefinition.APPLICATION_TYPE.put("mkv", "video/x-matroska");
        TpDefinition.APPLICATION_TYPE.put("ice", "x-conference/x-cooltalk");
        TpDefinition.APPLICATION_TYPE.put("html", "text/html");
        TpDefinition.APPLICATION_TYPE.put("xml", "text/xml");
        TpDefinition.APPLICATION_TYPE.put("htm", "text/html");
        TpDefinition.APPLICATION_TYPE.put("htmls", "text/html");
        TpDefinition.APPLICATION_TYPE.put("txt", "text/plain");
        TpDefinition.APPLICATION_TYPE.put("ini", "text/plain");
        TpDefinition.APPLICATION_TYPE.put("sql", "text/plain");
        TpDefinition.APPLICATION_TYPE.put("java", "text/plain");
        TpDefinition.APPLICATION_TYPE.put("css", "text/css");
        TpDefinition.APPLICATION_TYPE.put("javascript", "text/plain");
        TpDefinition.APPLICATION_TYPE.put("js", "text/plain");
        TpDefinition.APPLICATION_TYPE.put("log", "text/plain");
        TpDefinition.APPLICATION_TYPE.put("vue", "text/plain");
        TpDefinition.APPLICATION_TYPE.put("c", "text/plain");
        TpDefinition.APPLICATION_TYPE.put("cpp", "text/plain");
        TpDefinition.APPLICATION_TYPE.put("cs", "text/plain");
    }
}
