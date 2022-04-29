package icu.weboys.fileview.boot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.stream.Stream;

@Primary
@ConfigurationProperties(prefix = "fileview")
@Configuration
public class FViewConfig {
    Office office;
    Root root;

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }




    public String getOfficeHome(){
        return office.getHome();
    }

    public int[] getOfficePorts(){
        if(office.getPorts() == null){
            return new int[]{8100};
        }
        return Stream.of(office.ports).mapToInt(Integer::intValue).toArray();
    }

    public String getOfficeHost(){
        return office.getHost();
    }

    public String getRootDir(){
        return root.getDir();
    }

    public String getRootImgTps(){
        return root.getEnableImgTps();
    }
}

class Office{
    String home;
    Integer[] ports;
    String host;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public Integer[] getPorts() {
        return ports;
    }

    public void setPorts(Integer[] ports) {
        this.ports = ports;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}

class Root{
    String dir;
    String enableImgTps;
    public String getEnableImgTps(){
        return enableImgTps;
    }
    public void setEnableImgTps(String enableImgTps) {
        this.enableImgTps = enableImgTps;
    }
    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

}

