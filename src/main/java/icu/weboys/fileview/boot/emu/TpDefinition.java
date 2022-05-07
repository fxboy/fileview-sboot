package icu.weboys.fileview.boot.emu;

import icu.weboys.fileview.boot.impl.IView;
import icu.weboys.fileview.boot.impl.ReturnProcessor;

import javax.swing.text.html.HTML;

import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TpDefinition {
    public static Map<String,Object> ENABLE_IMAGE_VIEW_TYPE = new ConcurrentHashMap<>(16);
    public static Map<String,String> APPLICATION_TYPE =  new ConcurrentHashMap<String, String>(84){{
        put("jpg","image/jpeg");
        put("jpeg","image/jpeg");
        put("png","image/png");
        put("gif","image/gif");
        put("bmp","image/bmp");
        put("jpeg","image/jpeg");
        put("ico","image/x-icon");
        put("tif","image/tiff");
        put("tiff","image/tiff");
        put("svg","image/svg+xml");
        put("svgz","image/svg+xml");
        put("webp","image/webp");
        put("pdf","application/pdf");
        put("ps","application/postscript");
        put("ai","application/postscript");
        put("eps","application/postscript");
        put("psd","application/octet-stream");
        put("zip","application/zip");
        put("rar","application/x-rar-compressed");
        put("7z","application/x-7z-compressed");
        put("tar","application/x-tar");
        put("gz","application/x-gzip");
        put("bz2","application/x-bzip2");
        put("doc","application/msword");
        put("docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        put("xls","application/vnd.ms-excel");
        put("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        put("ppt","application/vnd.ms-powerpoint");
        put("pptx","application/vnd.openxmlformats-officedocument.presentationml.presentation");
        put("odt","application/vnd.oasis.opendocument.text");
        put("ods","application/vnd.oasis.opendocument.spreadsheet");
        put("odp","application/vnd.oasis.opendocument.presentation");
        put("sxw","application/vnd.sun.xml.writer");
        put("stw","application/vnd.sun.xml.writer.template");
        put("sxc","application/vnd.sun.xml.calc");
        put("stc","application/vnd.sun.xml.calc.template");
        put("sxd","application/vnd.sun.xml.draw");
        put("std","application/vnd.sun.xml.draw.template");
        put("sxi","application/vnd.sun.xml.impress");
        put("sti","application/vnd.sun.xml.impress.template");
        put("sxg","application/vnd.sun.xml.writer.global");
        put("sxm","application/vnd.sun.xml.math");
        put("tar.gz","application/x-gtar");
        put("tgz","application/x-gtar");
        put("tar.bz2","application/x-bzip2");
        put("tbz","application/x-bzip2");
        put("mp3","audio/mpeg");
        put("wav","audio/x-wav");
        put("aif","audio/x-aiff");
        put("aiff","audio/x-aiff");
        put("aifc","audio/x-aiff");
        put("ram","audio/x-pn-realaudio");
        put("rm","audio/x-pn-realaudio");
        put("ra","audio/x-realaudio");
        put("rmm","audio/x-pn-realaudio");
        put("rmp","audio/x-pn-realaudio");
        put("m3u","audio/x-mpegurl");
        put("wma","audio/x-ms-wma");
        put("wax","audio/x-ms-wax");
        put("ogg","audio/ogg");
        put("midi","audio/midi");
        put("mid","audio/midi");
        put("kar","audio/midi");
        put("mp4a","audio/mp4");
        put("m4a","audio/mp4");
        put("m4b","audio/mp4");
        put("m4p","audio/mp4");
        put("mpga","audio/mpeg");
        put("mp2","audio/mpeg");
        put("mp2a","audio/mpeg");
        put("m2a","audio/mpeg");
        put("m3a","audio/mpeg");
        put("oga","audio/ogg");
        put("spx","audio/ogg");
        put("flac","audio/flac");
        put("aac","audio/x-aac");
        put("amr","audio/amr");
        put("mka","audio/x-matroska");
        put("wpl","application/vnd.ms-wpl");
        put("wvx","video/x-ms-wvx");
        put("avi","video/x-msvideo");
        put("movie","video/x-sgi-movie");
        put("mpv","video/x-matroska");
        put("mkv","video/x-matroska");
        put("ice","x-conference/x-cooltalk");
        put("html","text/html");
        put("xml","text/xml");
        put("htm","text/html");
        put("htmls","text/html");
        put("txt","text/plain");
        put("ini","text/plain");
        put("sql","text/plain");
        put("java","text/plain");
        put("css","text/css");
        put("javascript","text/plain");
        put("js","text/plain");
        put("log","text/plain");
        put("vue","text/plain");
        put("c","text/plain");
        put("cpp","text/plain");
        put("cs","text/plain");
    }};

    public static Map<String, IView> APPLICATION_IVIEW_ONLINE =  new ConcurrentHashMap<String,IView>(84){{

    }};
    public static Map<String, IView> APPLICATION_IVIEW =  new ConcurrentHashMap<String,IView>(84){{

    }};

    public static ReturnProcessor RETURN_PROCESSOR = null;

    public static String ROOT_PATH = null;
    {{
        String ROOT_PATH = this.getClass().getResource("/").getPath().substring(1) + "/" + "FILEVIEW_TEMP/";
    }}



    public static Map<String,String> HTML_THEME = new HashMap<String, String>(16){{
        put("default","<html><head>    <meta charset=\"utf-8\">    <title>LET[%LET_FILENAME%]</title>    <style>        body{        }        #view{            text-align: center;            padding:80px;        }        .vimg{            margin-bottom: 30px;            padding-bottom: 30px;            border-bottom: 1px solid #EEEEEE        }        #dwn-btn {            position: fixed;            z-index: 100;            width: 25px;            right: 0px;            top: 20%;            color: rgb(255, 255, 255);            background: #fff;            cursor: pointer;            border-bottom-left-radius: 10px;            padding: 10px 6px;            border-top-left-radius: 10px;            font-size: 16px;            letter-spacing: 4px;            box-shadow: 0px 2px 12px 0px rgba(123, 123, 123, 0.26);            color: #484848;            font-size: 12px;            width: 25px;            display: block;            text-align: center;            word-break: break-all;            line-height: 18px;            font-weight: 600;        }</style></head><body><div id=\"view\">LET[%LET_IMGS%]</div><div id=\"dwn-btn\"><span>保存本地</span></div><!-- <div id=\"gd-btn\"><span>关灯</span></div> --></body><script>document.getElementById(\"dwn-btn\").onclick = function(){let pth = 'LET[%LET_FILEPATH%]';}</script></html>");
    }};


    public static String HTML_THEME_USE = "";



    // 文件转入模板视图
    public final static Configuration FTL_CONFIGURATION = new Configuration();

    public static String FILE_STATIC_PATH = "";
    
   static {
        FTL_CONFIGURATION.setDefaultEncoding("UTF-8");
        // 定义默认的主题位置文件目录，暂时设置为空
        try {
            // 未对linux系统兼容
            String path = (Thread.currentThread().getContextClassLoader().getResource("").getPath() + "theme/")
                    .substring(1);
            FILE_STATIC_PATH = (Thread.currentThread().getContextClassLoader().getResource("").getPath() + "templates/")
                    .substring(1);
            if (!new File(FILE_STATIC_PATH).exists())
                new File(FILE_STATIC_PATH).mkdirs();
            FTL_CONFIGURATION.setDirectoryForTemplateLoading(new File(path));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
