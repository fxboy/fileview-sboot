package icu.weboys.fileview.boot.handler;

import icu.weboys.fileview.boot.config.FViewConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@ComponentScan(basePackages = {"icu.weboys.fileview.boot.config","icu.weboys.fileview.boot"})
@EnableConfigurationProperties(FViewConfig.class)
@Configuration
public class ApplicationImport {

}
