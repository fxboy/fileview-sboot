package io.github.fxboy.fileview.sboot.bean;

import io.github.fxboy.fileview.sboot.util.ConvertUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: Fanxing
 * @time: 2022/4/27 14:23
 * @description: Spring启动后，首先执行bean!!!
 * At first, only God and I knew what it meant. [2022/4/27 14:23]
 * Now, only God knows what it means. Oh, no, God doesn't know what it means. [Later]
 */
@Component
public class SpringRunHandler implements ApplicationRunner {

    @Resource
    ConvertUtils convertUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        convertUtils.open();
    }
}
