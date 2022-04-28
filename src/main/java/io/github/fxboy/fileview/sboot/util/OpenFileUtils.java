package io.github.fxboy.fileview.sboot.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Component
public class OpenFileUtils {
    public void run(String type, InputStream in, HttpServletRequest request, HttpServletResponse response){
        OutputStream os = null;
        try {
            response.setContentType(type);
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
        }finally {

        }
    }

    public void openHtml(String filePath, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        run("text/html;charset=utf-8", new FileInputStream(filePath), request, response);
    }

    public void openPdf(String filePath, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        run("application/pdf", new FileInputStream(filePath), request, response);
    }

    public void openImg(String filePath,HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        run("image/jpeg", new FileInputStream(filePath), request, response);
    }

    public void openGif(String filePath, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        run("image/gif", new FileInputStream(filePath), request, response);
    }

    public void dwn(String filePath, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        response.setHeader("Content-Disposition", "attachment;filename=" + new File(filePath).getName());
        run("application/octet-stream", new FileInputStream(filePath), request, response);
    }



    public void openPdf(InputStream in, HttpServletRequest request, HttpServletResponse response) {
        run("application/pdf", in, request, response);
    }

    public void openImg(InputStream in, HttpServletRequest request, HttpServletResponse response){
        run("image/jpeg", in, request, response);
    }

    public void openGif(InputStream in, HttpServletRequest request, HttpServletResponse response){
        run("image/gif",in, request, response);
    }

    public void openHtml(InputStream in, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        run("text/html;charset=utf-8",in, request, response);
    }
    public void dwn(String type,InputStream in, HttpServletRequest request, HttpServletResponse response)  {
        response.setHeader("Content-Disposition", "attachment;filename="+ System.currentTimeMillis() + "." + type);
        run("application/octet-stream", in, request, response);
    }

}
