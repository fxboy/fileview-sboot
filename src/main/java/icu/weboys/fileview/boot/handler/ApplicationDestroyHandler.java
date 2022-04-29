package icu.weboys.fileview.boot.handler;

import icu.weboys.fileview.boot.util.OpenOfficeUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;


@Component
public class ApplicationDestroyHandler implements DisposableBean, ExitCodeGenerator {

    @Override
    public void destroy() throws Exception {
        OpenOfficeUtils.stop();
    }

    @Override
    public int getExitCode() {
        return -1;
    }
}
