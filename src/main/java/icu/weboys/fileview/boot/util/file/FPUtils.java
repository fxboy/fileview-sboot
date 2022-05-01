package icu.weboys.fileview.boot.util.file;

import icu.weboys.fileview.boot.emu.TpDefinition;
import org.apache.commons.codec.binary.Hex;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.sql.rowset.spi.SyncResolver;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.UUID;

public class FPUtils {
    public static String imageEncodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);
            bos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return String.format("data:%s;base64,%s", TpDefinition.APPLICATION_TYPE.get(type), imageString);
    }

    public static BufferedImage BaseDecodeToImage(String imageString) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static String getFileType(String file){
        return file.substring(file.lastIndexOf(".")+1).toLowerCase(Locale.ROOT);
    }
    public static String getFileType(File file){
        return file.getName().substring(file.getName().lastIndexOf(".")+1).toLowerCase(Locale.ROOT);
    }
    public static String getFileType(MultipartFile file){
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1).toLowerCase(Locale.ROOT);
    }
    public static String getFileName(String file){
        return getFileName(new File(file)).toLowerCase(Locale.ROOT);
    }
    public static String getFileName(File file){
        return file.getName().substring(0,file.getName().lastIndexOf(".")).toLowerCase(Locale.ROOT);
    }
    public static String getFileName(MultipartFile file){
        return file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf(".")).toLowerCase(Locale.ROOT);
    }

    public static String getTempName(String type){
        return String.format("%s/%s.%s",TpDefinition.ROOT_PATH.endsWith("/")?TpDefinition.ROOT_PATH:TpDefinition.ROOT_PATH + "/", UUID.randomUUID(),type);
    }

    public static String getTempName(String name,String type){
        return String.format("%s/%s.%s",TpDefinition.ROOT_PATH.endsWith("/")?TpDefinition.ROOT_PATH:TpDefinition.ROOT_PATH + "/",name ,type);
    }

    public static String getRandomName(String type){
        return String.format("%s.%s",UUID.randomUUID(),type);
    }

    //public static File getTempFile(String type){
    //    return new File(getTempName(type));
    //}


    public static File getTempFile(String type){
        return new File(getTempName(type));
    }





    public static File getTempFile(String name,String type){
        return new File(getTempName(name,type));
    }

    // 计算md5
    public static String fileMd5Hash(FileInputStream fileInputStream ){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(md5.digest()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 计算md5
    public static String fileMd5Hash(File file) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return fileMd5Hash(fileInputStream);
    }

    public static String fileMd5Hash(String file) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File(file));
        return fileMd5Hash(fileInputStream);
    }
}
