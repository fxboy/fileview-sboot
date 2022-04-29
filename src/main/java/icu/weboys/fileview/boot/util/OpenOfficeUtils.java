package icu.weboys.fileview.boot.util;

import icu.weboys.fileview.boot.config.FViewConfig;
import org.jodconverter.local.office.LocalOfficeManager;

import java.util.stream.Stream;

public class OpenOfficeUtils {
    public static LocalOfficeManager officeManager = null;
    public static void open(FViewConfig fViewConfig){
        System.out.println("");
        try {
            String OpenOffice_HOME = fViewConfig.getOfficeHome();
            String OpenOffice_HOST = fViewConfig.getOfficeHost();
            int[] ports = fViewConfig.getOfficePorts();
            officeManager = LocalOfficeManager.builder().hostName(OpenOffice_HOST).portNumbers(ports).officeHome(OpenOffice_HOME).install().build();
            officeManager.start();
            System.out.println("FILEVIEW::OpenOffice is running...");
        } catch (NullPointerException e) {
            System.out.println("FILEVIEW::OpenOffice is not configured, which will affect the online preview word function.");
        } catch (Exception ex) {
            System.out.println("FILEVIEW::OpenOffice is not running...,reason:" + ex.getMessage());
        }
    }

    public static void stop(){
        try{
            officeManager.stop();
            System.out.println("FILEVIEW::OpenOffice is stopped...");
        }catch (Exception ex){
            System.out.println("FILEVIEW::OpenOffice is not stopped...,reason:" + ex.getMessage());
        }
    }
}
