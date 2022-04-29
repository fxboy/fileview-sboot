package icu.weboys.fileview.boot.handler;

import icu.weboys.fileview.boot.config.FViewConfig;
import icu.weboys.fileview.boot.emu.TpDefinition;
import icu.weboys.fileview.boot.impl.IView;
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
        String[] tps = fViewConfig.getRootImgTps() == null?new String[0]:fViewConfig.getRootImgTps().split(",");
        for (String tp : tps) {
            TpDefinition.ENABLE_IMAGE_VIEW_TYPE.put(tp, true);
        }
        Set<String> set = TpDefinition.APPLICATION_TYPE.keySet();
        IView defaultView = new DefaultViewImpl();
        for (String t : set) {

            char[] chars = t.toLowerCase(Locale.ROOT).toCharArray();
            if(chars[0] >= 97){
                chars[0] -= 32;
            }
            t = String.valueOf(chars);
            try{
                Class<?> clazz = Class.forName(String.format("%s.%sViewImpl", PACKAGE_NAME, t));
                IView iv = (IView) clazz.newInstance();
                TpDefinition.APPLICATION_IVIEW.put(t.toLowerCase(Locale.ROOT), iv);
            }catch (Exception ex){
                TpDefinition.APPLICATION_IVIEW.put(t.toLowerCase(Locale.ROOT),defaultView);
            }
        }
        System.out.print(String.format("Currently, online preview only supports the following file types: %s", String.join(",", TpDefinition.APPLICATION_IVIEW.keySet())));
        //Open OpenOffice
        OpenOfficeUtils.open(fViewConfig);

        // 设置根路径
        TpDefinition.ROOT_PATH = fViewConfig.getRootDir().replaceAll("\\\\", "/");
    }
}
