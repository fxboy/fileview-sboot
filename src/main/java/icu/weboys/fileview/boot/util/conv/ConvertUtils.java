package icu.weboys.fileview.boot.util.conv;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import icu.weboys.fileview.boot.emu.TpDefinition;
import icu.weboys.fileview.boot.impl.IFile;
import icu.weboys.fileview.boot.util.file.FPUtils;

import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.local.JodConverter;
import org.springframework.web.multipart.MultipartFile;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ConvertUtils {
    public static Boolean conver(String sourceFile, String destFile) {
        File inputFile = new File(sourceFile);
        File outputFile = new File(destFile);
        try {
            JodConverter
                    .convert(inputFile)
                    .to(outputFile)
                    .execute();
            return true;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Boolean conver(File in, File out) {
        DocumentFormat inputFormat = DefaultDocumentFormatRegistry.getFormatByExtension(FPUtils.getFileType(in));
        DocumentFormat outputFormat = DefaultDocumentFormatRegistry.getFormatByExtension(FPUtils.getFileType(out));
        try {
            JodConverter
                    .convert(in)
                    .as(inputFormat)
                    .to(out)
                    .as(outputFormat)
                    .execute();
            return true;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Boolean conver(FileInputStream in,String destFile,DocumentFormat inputFormat) {
        FileOutputStream out = null;
        DocumentFormat outputFormat = DefaultDocumentFormatRegistry.getFormatByExtension(FPUtils.getFileType(destFile));
        try{
            out = new FileOutputStream(destFile);
            JodConverter
                    .convert(in)
                    .as(inputFormat)
                    .to(out)
                    .as(outputFormat)
                    .execute();
            return true;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public Boolean conver(MultipartFile in, String destFile) {
        FileOutputStream out = null;
        DocumentFormat inputFormat = DefaultDocumentFormatRegistry.getFormatByExtension(FPUtils.getFileType(in));
        DocumentFormat outputFormat = DefaultDocumentFormatRegistry.getFormatByExtension(FPUtils.getFileType(destFile));
        try{
            out = new FileOutputStream(destFile);
            JodConverter
                    .convert(in.getInputStream())
                    .as(inputFormat)
                    .to(out)
                    .as(outputFormat)
                    .execute();
            return true;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static BufferedImage[] pdfToImage(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("FILEVIEW::File conversion exception,key pdf");
        }
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdffile = new PDFFile(buf);
        //获取PDF页码
        int jumlahhalaman = pdffile.getNumPages();
        BufferedImage[] bufferedImages = new BufferedImage[jumlahhalaman];
        //遍历生成单页图片
        for (int i = 1; i <= jumlahhalaman; i++) {
            PDFPage page = pdffile.getPage(i);
            //创建图片
            Rectangle rect = new Rectangle(0, 0, (int) page.getWidth(), (int) page.getHeight());
            Image img = page.getImage(
                    rect.width, rect.height,
                    rect,
                    null,
                    true,
                    true
            );
            BufferedImage bufferedImage = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.createGraphics();
                    g.drawImage(img, 0, 0, null);
                    g.dispose();
            bufferedImages[i - 1] = bufferedImage;
        }
        return bufferedImages;
    }

    public static String[] pdfToImageBase64(File file) throws IOException {
        BufferedImage[] bss = ConvertUtils.pdfToImage(file);
        String[] imgs = new String[bss.length];
        for (int i = 0; i < bss.length; i++) {
            imgs[i] = FPUtils.imageEncodeToString(bss[i],"jpg").replaceAll("\\r\\n","");
        }
        return imgs;
    }

    public static File wordToPdf(IFile file) throws IOException {
        File out = new File(file.getMdTypePath("pdf"));
        // 如果存档路径下未找到文件，则进行转换，否则直接返回存在的文件
        if (!out.exists()) {
            ConvertUtils.conver(file.getFile(), out);
        }
        return out;
    }
    

    public static String getFTLTheme(IFile file, Map<String, Object> data) {
        Writer out = null;
        try {
            Template template = TpDefinition.FTL_CONFIGURATION.getTemplate(file.getTheme());
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("lf", data);
            String filename = String.format("%s%s.html", TpDefinition.FILE_STATIC_PATH, file.getViewFileName());
            if((new File(filename)).exists()){
                return filename;
            }
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
            template.process(map, out);
            return filename;
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
       
        return null;
    }

}
