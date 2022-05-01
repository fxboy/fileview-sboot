package icu.weboys.fileview.boot.handler;

import icu.weboys.fileview.boot.config.FViewConfig;
import icu.weboys.fileview.boot.emu.TpDefinition;
import icu.weboys.fileview.boot.impl.IView;
import icu.weboys.fileview.boot.op.DefaultReturnProcessor;
import icu.weboys.fileview.boot.op.DefaultViewImpl;
import icu.weboys.fileview.boot.util.OpenOfficeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;
import java.util.Set;


@Component
public class ApplicationRunHandler implements ApplicationRunner {

    @Autowired
    FViewConfig fViewConfig;
    public final String PACKAGE_NAME = "icu.weboys.fileview.boot.op";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //  FViewConfig fViewConfig = ApplicationImport.f;
        //Get all types of keys
        if (fViewConfig.getRoot() == null || fViewConfig.getOffice() == null) {
            System.out.println("OnlineFileView is not Start...,Please set the root and office path in the application.properties");
            return;
        }
        if (fViewConfig.getRootDir() != null) {
            TpDefinition.ROOT_PATH = fViewConfig.getRootDir().replaceAll("\\\\", "/");
        }
        // 设置默认的视图
        TpDefinition.HTML_THEME_USE = TpDefinition.HTML_THEME.get("default");
        // 设置返回内容处理器
        TpDefinition.RETURN_PROCESSOR = new DefaultReturnProcessor();
        String[] tps = fViewConfig.getRootImgTps() == null ? new String[0] : fViewConfig.getRootImgTps().split(",");
        for (String tp : tps) {
            TpDefinition.ENABLE_IMAGE_VIEW_TYPE.put(tp, true);
        }
        Set<String> set = TpDefinition.APPLICATION_TYPE.keySet();
        IView defaultView = new DefaultViewImpl();

        // 此处改个版。。。改成查找注解的方式
        // 插件内置解析view 优先级最低，如果没找到自定义的，则使用默认的
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
        System.out.print(String.format("Currently, online preview only supports the following file types: %s", String.join(",", TpDefinition.APPLICATION_IVIEW.keySet())));
        //Open OpenOffice
        OpenOfficeUtils.open(fViewConfig);
    }
}
