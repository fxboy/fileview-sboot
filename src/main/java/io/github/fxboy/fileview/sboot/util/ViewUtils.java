package io.github.fxboy.fileview.sboot.util;

import io.github.fxboy.fileview.sboot.bean.FileViewRun;
import io.github.fxboy.fileview.sboot.configuration.FileViewConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @author: Fanxing
 * @time: 2022/4/25 11:11
 * @description: This is a class object !!!
 * At first, only God and I knew what it meant. [2022/4/25 11:11]
 * Now, only God knows what it means. Oh, no, God doesn't know what it means. [Later]
 */
@Component
public class ViewUtils {
    @Resource
    ConvertUtils convertUtils;

    @Resource
    FileViewConfig  fileViewConfig;

    public String pdf(String file){
        return file;
    }

    public String word(String file) throws IOException {
        String fpt = fileViewConfig.getSavePath();
        if(!FileViewRun.config) throw new IOException("FILEVIEW::Fileview configuration information not found, key: Office");
        if(fpt.startsWith("/") && !fpt.endsWith("/")){
            fpt+= "/";
        }else if(!fpt.startsWith("/") && !fpt.endsWith("//")){
            // 如果不是单斜杠，则说明是windows系统（后期换正则或者匹配操作系统）
            fpt+= "//";
        }
        String tmp = fpt;
        File o = new File(file);
        String fileName = o.getName().substring(0, o.getName().lastIndexOf(".")) + ".pdf";
        File n = new File(tmp+fileName);

        if(!n.exists()){
            Boolean i = convertUtils.tst(file, n.getPath());
            if(!i){
                throw new IOException("FILEVIEW::File conversion exception");
            }
        }
        return n.getPath();
    }
    public String img(String file){
        return file;
    }
}
