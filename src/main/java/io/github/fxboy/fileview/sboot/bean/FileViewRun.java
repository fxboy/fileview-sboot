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


@EnableConfigurationProperties(FileViewConfig.class)
@ComponentScan(basePackages = "io.github.fxboy.fileview.sboot")
@Configuration
public class FileViewRun {
    public static Process process;
    public static Boolean config = false;
    public final static String IS_WORD_HTML_FLAG = "[msf_html_***_abs_xst_word]";

}
