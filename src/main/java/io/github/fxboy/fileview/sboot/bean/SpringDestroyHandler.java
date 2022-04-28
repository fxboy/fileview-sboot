package io.github.fxboy.fileview.sboot.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

/**
 * @author: Fanxing
 * @time: 2022/4/25 12:03
 * @description: Springboot 容器销毁时调用的类 !!!
 * At first, only God and I knew what it meant. [2022/4/25 12:03]
 * Now, only God knows what it means. Oh, no, God doesn't know what it means. [Later]
 */
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
        return 0;
    }
}
