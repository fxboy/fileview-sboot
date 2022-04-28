package io.github.fxboy.fileview.sboot.configuration;

import io.github.fxboy.fileview.sboot.bean.FileViewRun;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "fileview")
@Configuration
public class FileViewConfig {
    Save save;
    Office office;

    public Save getSave() {
        return save;
    }

    public void setSave(Save save) {
        this.save = save;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getOfficePath() {
        if(office == null) {
            return null;
        }
        FileViewRun.config = true;
        return office.getPath();
    }

    public String getOfficeCommand() {
        if(office == null) {
            return null;
        }
        FileViewRun.config = true;
        return office.getCommand();
    }


    public String getOfficeHost() {
        if(office == null) {
            return null;
        }
        FileViewRun.config = true;
        return office.getHost();
    }


    public Integer getOfficePort() {
        if(office == null) {
            return null;
        }
        FileViewRun.config = true;
        return office.getPort();
    }

    public String getSavePath() {
        if(save == null) {
            return null;
        }
        FileViewRun.config = true;
        return save.getPath();
    }




}

class Save{
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

class Office{
    String path;
    String command;
    String host;
    Integer port;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
