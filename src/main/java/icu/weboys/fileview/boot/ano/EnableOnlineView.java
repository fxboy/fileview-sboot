package icu.weboys.fileview.boot.ano;

import icu.weboys.fileview.boot.handler.ApplicationImport;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration(ApplicationImport.class)
public @interface EnableOnlineView {
}