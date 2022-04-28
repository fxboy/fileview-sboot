package io.github.fxboy.fileview.sboot.util;

import io.github.fxboy.fileview.sboot.bean.FileViewRun;
import io.github.fxboy.fileview.sboot.configuration.FileViewConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.function.BooleanSupplier;


@Component
public class ViewUtils {
    @Resource
    ConvertUtils convertUtils;

    @Resource
    FileViewConfig  fileViewConfig;

    public String pdf(String file){
        return file;
    }

    public String word(String file) throws IOException {
        String fpt = fileViewConfig.getSavePath();
        if(!FileViewRun.config) throw new IOException("FILEVIEW::Fileview configuration information not found, key: Office");
        if(fpt.startsWith("/") && !fpt.endsWith("/")){
            fpt+= "/";
        }
        else if(!fpt.startsWith("/") && !fpt.endsWith("//") && fpt.endsWith("/")){
            fpt+= "/";
        }
        else if(!fpt.startsWith("/") && !fpt.endsWith("//")){
            // 如果不是单斜杠，则说明是windows系统（后期换正则或者匹配操作系统）
            fpt+= "//";
        }
        String tmp = fpt;
        File o = new File(file);
        String fileName = o.getName().substring(0, o.getName().lastIndexOf(".")) + ".pdf";
        File n = new File(tmp+fileName);

        if(!n.exists()){
            try{
                Boolean i = convertUtils.tst(file, n.getPath());
                if(!i){
                    throw new IOException("FILEVIEW::File conversion exception");
                }
            }catch (Exception e){
                if(e.getMessage().contains("could not save output document")){
                    // 检测文档是否是html。。。。
                    return FileViewRun.IS_WORD_HTML_FLAG + getHtmlForDoc(file);
                }
            }
        }
        return n.getPath();
    }

    public String word(File file) throws IOException {
        String fileName = file.getPath().substring(0, file.getPath().lastIndexOf(".")) + ".pdf";
        File n = new File(fileName);
        if(!n.exists()){
            Boolean i = convertUtils.tst(file.getPath(), n.getPath());
            if(!i){
                throw new IOException("FILEVIEW::File conversion exception");
            }
        }
        return n.getPath();
    }

    public String img(String file){
        return file;
    }


    public void writeError(String msg, HttpServletResponse response){
        OutputStream os = null;
        try{
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            os = response.getOutputStream();  //创建输出流
            os.write(msg.getBytes());
            os.flush();
            os.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String saveWord(String file, MultipartFile ins) throws IOException {
        String fpt = fileViewConfig.getSavePath();
        if(!FileViewRun.config) throw new IOException("FILEVIEW::Fileview configuration information not found, key: Office");
        if(fpt.startsWith("/") && !fpt.endsWith("/")){
            fpt+= "/";
        }
        else if(!fpt.startsWith("/") && !fpt.endsWith("//") && fpt.endsWith("/")){
            fpt+= "/";
        }
        else if(!fpt.startsWith("/") && !fpt.endsWith("//")){
            // 如果不是单斜杠，则说明是windows系统（后期换正则或者匹配操作系统）
            fpt+= "//";
        }
        try{
            if(new File(fpt).exists()){
                new File(fpt).mkdirs();
            }
            file = fpt + file;
            ins.transferTo(new File(file));
            return word(new File(file));
        }catch (Exception ex){
            if(ex.getMessage().contains("could not save output document")){
                // 检测文档是否是html。。。。
               return FileViewRun.IS_WORD_HTML_FLAG + getHtmlForDoc(file);
            }
            throw new IOException("FILEVIEW::File conversion exception,key save");
        }
    }

    public Boolean saveFile(String fileName, InputStream inputStream) {
        OutputStream os = null;
        try{
            if(new File(fileName).exists()){
                new File(fileName).mkdir();
            }
            os =  new FileOutputStream(fileName);
            byte[] bs = new byte[1024];
            int len;
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.close();
            return true;
        }catch (Exception e){
            return false;
        }finally {
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getHtmlForDoc(String file) throws IOException {
        return file;
    }

}
