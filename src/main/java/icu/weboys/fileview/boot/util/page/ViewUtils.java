package icu.weboys.fileview.boot.util.page;


import icu.weboys.fileview.boot.emu.TpDefinition;
import icu.weboys.fileview.boot.impl.IView;
import icu.weboys.fileview.boot.op.ViewFile;
import icu.weboys.fileview.boot.op.ViewStream;
import icu.weboys.fileview.boot.util.file.FPUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Component
public class ViewUtils {
    public static String view(File file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.view(new ViewFile(file));
    }

    public static String download(File file){
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.download(new ViewFile(file));
    }

    public static FileInputStream open(File file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.open(new ViewFile(file));
    }

    public static String view(String file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.view(new ViewFile(file));
    }

    public static String download(String file){
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.download(new ViewFile(file));
    }

    public static FileInputStream open(String file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.open(new ViewFile(file));
    }

    public static String view(MultipartFile file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.view(new ViewStream(file));
    }

    public static String download(MultipartFile file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.download(new ViewStream(file));
    }

    public static FileInputStream open(MultipartFile file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.open(new ViewStream(file));
    }

    public static String cvHtml(String content){
        return TpDefinition.HTML_THEME_USE.replace("LET[%LET_IMGS%]",content);
    }

    public void run(String type, InputStream in, HttpServletResponse response) {
        OutputStream os = null;
        try {
            //response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            //response.setContentType("application/octet-stream");
            //response.setHeader("Content-Disposition", "attachment; filename="  + FPUtils.getRandomName(ope));
            type = getFileType(type);
            response.setContentType(String.format("%s;%s", type, "charset=utf-8"));
            os = response.getOutputStream();
            byte[] b = new byte[1024];
            while (in.read(b) != -1) {
                os.write(b);
            }
            in.close();
            os.flush();
            os.close();
        } catch (Exception e) {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                if (null != os) {
                    os.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } finally {

        }
    }



    // HTML专属，确切的说是 view 
    public void run(InputStream in, HttpServletResponse response) {
        OutputStream os = null;
        try {
            // response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/octet-stream");
            // response.setHeader("Content-Disposition", "attachment; filename=" +
            // FPUtils.getRandomName(ope));
            String type = getFileType("html");
            response.setContentType(String.format("%s;%s", type, "charset=utf-8"));
            os = response.getOutputStream();
            byte[] b = new byte[1];
            while (in.read(b) != -1) {
                os.write(b);
            }
            in.close();
            os.flush();
            os.close();
        } catch (Exception e) {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                if (null != os) {
                    os.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } finally {

        }
    }
    


    // 处理器
    public void process(String type, String content, HttpServletResponse response) {
        if (TpDefinition.RETURN_PROCESSOR != null) {
            run(type, new ByteArrayInputStream(
                    TpDefinition.RETURN_PROCESSOR.process(type, content, response).getBytes(StandardCharsets.UTF_8)),
                    response);
            return;
        }
        run(type, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), response);
    }


    // 预览模式调用的这个方法
    public void process(String filepath, HttpServletResponse response) {
        try{
            // if (TpDefinition.RETURN_PROCESSOR != null) {
            //     run(new FileInputStream(new File(TpDefinition.RETURN_PROCESSOR.process(null,filepath,response))),
            //         response);
            //     return;
            // }
            run(new FileInputStream(new File(filepath)), response);
        } catch (Exception ex) {
        ex.printStackTrace();
        }
    }
    
    public void process(String type, String content, String theme ,HttpServletResponse response) {
        if (TpDefinition.RETURN_PROCESSOR != null) {
            run(type, new ByteArrayInputStream(
                    TpDefinition.RETURN_PROCESSOR.process(type, content, response).getBytes(StandardCharsets.UTF_8)),
                    response);
            return;
        }
        run(type, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), response);
    }

    public void run(String type, byte[] b, HttpServletResponse response){
       PrintWriter os = null;
        try {
            response.setCharacterEncoding("UTF-8");
            type = getFileType(type);
            response.setContentType(String.format("%s;%s",type,"charset=utf-8"));
            os = response.getWriter();
            os.print(b);
            os.flush();
            os.close();
        } catch (Exception e) {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }finally {

        }
    }


    public String getFileType(String type){
        if("doc".equals(type) || "docx".equals(type)){
            type = "pdf";
        }
        if(TpDefinition.APPLICATION_TYPE.containsKey(type)){
            return TpDefinition.APPLICATION_TYPE.get(type);
        }else{
            return TpDefinition.APPLICATION_TYPE.get("txt");
        }
    }
}
