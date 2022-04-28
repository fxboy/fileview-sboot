package io.github.fxboy.fileview.sboot.anno;

import io.github.fxboy.fileview.sboot.bean.FileViewRun;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
// 使用自动导入配置信息注解，并将我们的入口类放入
@ImportAutoConfiguration(FileViewRun.class)
public @interface EnableFileView {
}
