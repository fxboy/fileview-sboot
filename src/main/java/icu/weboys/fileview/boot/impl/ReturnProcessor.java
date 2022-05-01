package icu.weboys.fileview.boot.impl;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: Fanxing
 * @time: 2022/5/1 17:24
 * @description: This is a class Interface !!!
 * At first, only God and I knew what it meant. [2022/5/1 17:24]
 * Now, only God knows what it means. Oh, no, God doesn't know what it means. [Later]
 */
public interface ReturnProcessor {
    public String process(String type,String returnString, HttpServletResponse response);
}
