package io.github.fxboy.fileview.sboot.bean;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.github.fxboy.fileview.sboot.configuration.FileViewConfig;
import io.github.fxboy.fileview.sboot.util.ConvertUtils;
import io.github.fxboy.fileview.sboot.util.OpenFileUtils;
import io.github.fxboy.fileview.sboot.util.ViewUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Fanxing
 * @time: 2022/4/28 16:08
 * @description: This is a class object !!!
 * At first, only God and I knew what it meant. [2022/4/28 16:08]
 * Now, only God knows what it means. Oh, no, God doesn't know what it means. [Later]
 */
@EnableConfigurationProperties(FileViewConfig.class)
@ComponentScan(basePackages = "io.github.fxboy.fileview.sboot")
@Configuration
public class FileViewRun {
    public static Process process;
    public static Boolean config = false;
}
