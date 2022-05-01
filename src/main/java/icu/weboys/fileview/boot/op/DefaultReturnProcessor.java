package icu.weboys.fileview.boot.op;

import icu.weboys.fileview.boot.abs.AbsReturnProcessor;
import icu.weboys.fileview.boot.impl.ReturnProcessor;

import javax.servlet.http.HttpServletResponse;


// 默认返回处理器,由于都是以String类型返回，所以，在此处对其做最后的处理
public class DefaultReturnProcessor extends AbsReturnProcessor {
    @Override
    public String process(String type, String returnString, HttpServletResponse response) {
        return super.process(type, returnString, response);
    }
}
