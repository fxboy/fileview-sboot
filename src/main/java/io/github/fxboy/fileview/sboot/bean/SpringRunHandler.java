package io.github.fxboy.fileview.sboot.bean;

import io.github.fxboy.fileview.sboot.util.ConvertUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class SpringRunHandler implements ApplicationRunner {

    @Resource
    ConvertUtils convertUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        convertUtils.open();
    }
}
