package icu.weboys.fileview.boot.abs;

import icu.weboys.fileview.boot.impl.ReturnProcessor;
import icu.weboys.fileview.boot.util.file.FPUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: Fanxing
 * @time: 2022/5/1 17:26
 * @description: This is a class object !!!
 * At first, only God and I knew what it meant. [2022/5/1 17:26]
 * Now, only God knows what it means. Oh, no, God doesn't know what it means. [Later]
 */
public abstract class AbsReturnProcessor implements ReturnProcessor {

    @Override
    public String process(String type, String returnString, HttpServletResponse response) {
        if(type.equals("html")){
            String pth = response.getHeader("fpt");
            return returnString.replace("LET[%LET_FILENAME%]", FPUtils.getFileName(pth)).replace("LET[%LET_FILEPATH%]",pth);

        }
        return returnString;
    }
}
