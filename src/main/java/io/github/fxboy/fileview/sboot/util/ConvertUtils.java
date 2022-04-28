package io.github.fxboy.fileview.sboot.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import io.github.fxboy.fileview.sboot.bean.FileViewRun;
import io.github.fxboy.fileview.sboot.configuration.FileViewConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@Component
public class ConvertUtils {
    @Resource
    FileViewConfig fileViewConfig;


    public void open(){
        if(fileViewConfig.getOfficePath() == null || fileViewConfig.getOfficePath().trim().equals("")){
            System.out.println("FILE_VIEW::OpenOffice information is not configured, which will affect the online preview word function.");
            return;
        }
        String OpenOffice_HOME = fileViewConfig.getOfficePath();
        String OpenOffice_HOST = fileViewConfig.getOfficeHost();
        int OpenOffice_PORT = fileViewConfig.getOfficePort();
        String OpenOffice_COMMAND = fileViewConfig.getOfficeCommand();
        try{
            if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
                OpenOffice_HOME += "\\";
            }
            // 启动OpenOffice的服务
            String command = OpenOffice_HOME
                    + OpenOffice_COMMAND;
            FileViewRun.process = Runtime.getRuntime().exec(command);
            System.out.println("FILEVIEW::OpenOffice is running...");
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("FILEVIEW::OpenOffice is not running...,reason:"+ex.getMessage());
        }
    }



    public boolean tst(String sourceFile, String destFile){
        String OpenOffice_HOST = fileViewConfig.getOfficeHost();
        int OpenOffice_PORT = fileViewConfig.getOfficePort();
        try {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
                return false;
            }
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(
                    OpenOffice_HOST, OpenOffice_PORT);
            connection.connect();
            DocumentConverter converter = new OpenOfficeDocumentConverter(
                    connection);
            converter.convert(inputFile, outputFile);
            connection.disconnect();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
