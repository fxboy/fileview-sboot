package icu.weboys.fileview.boot.abs;

import icu.weboys.fileview.boot.impl.ReturnProcessor;
import icu.weboys.fileview.boot.util.file.FPUtils;

import javax.servlet.http.HttpServletResponse;


public abstract class AbsReturnProcessor implements ReturnProcessor {

    @Override
    public String process(String type, String returnString, HttpServletResponse response) {
        return returnString;
    }
}
