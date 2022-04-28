package io.github.fxboy.fileview.sboot.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;


@Component
public class SpringDestroyHandler implements DisposableBean, ExitCodeGenerator {

    @Override
    public void destroy() throws Exception {
        if(FileViewRun.process != null){
            FileViewRun.process.destroy();
            System.out.println("FILEVIEW::Office process closed.");
        }
    }

    @Override
    public int getExitCode() {
        return -1;
    }
}
