package icu.weboys.fileview.boot.impl;

import javax.servlet.http.HttpServletResponse;

public interface ReturnProcessor {
    public String process(String type,String returnString, HttpServletResponse response);
}
