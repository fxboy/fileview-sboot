package icu.weboys.fileview.boot.util.page;


import icu.weboys.fileview.boot.emu.TpDefinition;
import icu.weboys.fileview.boot.impl.IFile;
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
    public static IFile view(File file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.view(new ViewFile(file));
    }

    public static IFile download(File file){
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.download(new ViewFile(file));
    }

    public static FileInputStream open(File file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.open(new ViewFile(file));
    }

    public static IFile view(String file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.view(new ViewFile(file));
    }

    public static IFile download(String file){
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.download(new ViewFile(file));
    }

    public static FileInputStream open(String file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.open(new ViewFile(file));
    }

    public static IFile view(MultipartFile file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.view(new ViewStream(file));
    }

    public static IFile download(MultipartFile file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.download(new ViewStream(file));
    }

    public static FileInputStream open(MultipartFile file) throws IOException {
        IView iView = TpDefinition.APPLICATION_IVIEW.get(FPUtils.getFileType(file));
        return iView.open(new ViewStream(file));
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
        try {
            // if (TpDefinition.RETURN_PROCESSOR != null) {
            // run(new FileInputStream(new
            // File(TpDefinition.RETURN_PROCESSOR.process(null,filepath,response))),
            // response);
            // return;
            // }
            run(new FileInputStream(new File(filepath)), response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 预览模式调用的这个方法
    public void process(IFile file, HttpServletResponse response) {
        try {
            // if (TpDefinition.RETURN_PROCESSOR != null) {
            // run(new FileInputStream(new
            // File(TpDefinition.RETURN_PROCESSOR.process(null,filepath,response))),
            // response);
            // return;
            // }
            run(file.getType(),new FileInputStream(new File(file.getEndFilePath())), response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void process(String type, String content, String theme, HttpServletResponse response) {
        if (TpDefinition.RETURN_PROCESSOR != null) {
            run(type, new ByteArrayInputStream(
                    TpDefinition.RETURN_PROCESSOR.process(type, content, response).getBytes(StandardCharsets.UTF_8)),
                    response);
            return;
        }
        run(type, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), response);
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                if (null != os) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }

            System.gc();
        }
    }
    

    public void run(String type, InputStream in, HttpServletResponse response) {
        OutputStream os = null;
        try {
            // response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/octet-stream");
            // response.setHeader("Content-Disposition", "attachment; filename=" +
            // FPUtils.getRandomName(ope));
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
            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                if (null != os) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }

            System.gc();
        }
    }

    public void run(String type, byte[] b, HttpServletResponse response) {
        PrintWriter os = null;
        try {
            response.setCharacterEncoding("UTF-8");
            type = getFileType(type);
            response.setContentType(String.format("%s;%s", type, "charset=utf-8"));
            os = response.getWriter();
            os.print(b);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.flush();
                    os.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            System.gc();
        }
    }
    

    public void download(IFile file,HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename="  + String.format("%s",
                file.getFileName()));
        byte[] buff = new byte[1];
        //创建缓冲输入流
        BufferedInputStream bis = null;
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(file.getFile()));
            int read = bis.read(buff);
            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String getFileType(String type) {
        if ("doc".equals(type) || "docx".equals(type)) {
            type = "pdf";
        }
        if (TpDefinition.APPLICATION_TYPE.containsKey(type)) {
            return TpDefinition.APPLICATION_TYPE.get(type);
        } else {
            return TpDefinition.APPLICATION_TYPE.get("txt");
        }
    }
    
    public String getFileType(IFile file) {
        if (TpDefinition.APPLICATION_TYPE.containsKey(file.getType())) {
            return TpDefinition.APPLICATION_TYPE.get(file.getType());
        } else {
            return TpDefinition.APPLICATION_TYPE.get("txt");
        }
    }
}
