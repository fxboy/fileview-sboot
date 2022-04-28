package io.github.fxboy.fileview.sboot.util;

import io.github.fxboy.fileview.sboot.bean.FileViewRun;
import io.github.fxboy.fileview.sboot.configuration.FileViewConfig;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.JodConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.Locale;

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
       try{
           LocalOfficeManager manager = LocalOfficeManager.builder().hostName(OpenOffice_HOST).portNumbers(OpenOffice_PORT).officeHome(OpenOffice_HOME).install().build();
           manager.start();
           System.out.println("FILEVIEW::OpenOffice is running...");
       }catch (Exception ex){
               ex.printStackTrace();
               System.out.println("FILEVIEW::OpenOffice is not running...,reason:"+ex.getMessage());
       }
    }



    public boolean tst(String sourceFile, String destFile){
        return conver(sourceFile, destFile);
    }

    public boolean tst(FileInputStream in,String  sourceFile, FileOutputStream out, String destFile){
        return conver(in,sourceFile,out,destFile);
    }

    public Boolean conver(String sourceFile, String destFile){
        File inputFile = new File(sourceFile);
        File outputFile = new File(destFile);

        try{
            JodConverter
                    .convert(inputFile)
                    .to(outputFile)
                    .execute();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }

    public Boolean conver(FileInputStream in,String  sourceFile, FileOutputStream out, String destFile){
        DocumentFormat inputFormat = DefaultDocumentFormatRegistry.getFormatByExtension(sourceFile.substring(sourceFile.lastIndexOf(".")+1).toLowerCase());
        DocumentFormat outputFormat = DefaultDocumentFormatRegistry.getFormatByExtension(destFile.substring(destFile.lastIndexOf(".")+1).toLowerCase());
        try{
            JodConverter
                    .convert(in)
                    .as(inputFormat)
                    .to(out)
                    .as(outputFormat)
                    .execute();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }
}
