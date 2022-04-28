package io.github.fxboy.fileview.sboot.controller;

import io.github.fxboy.fileview.sboot.util.OpenFileUtils;
import io.github.fxboy.fileview.sboot.util.ViewUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

/**
 * @author: Fanxing
 * @time: 2022/4/25 11:07
 * @description: This is a class object !!!
 * At first, only God and I knew what it meant. [2022/4/25 11:07]
 * Now, only God knows what it means. Oh, no, God doesn't know what it means. [Later]
 */
@Controller
@RequestMapping("/fileview")
public class FileViewController {
    @Resource
    ViewUtils viewUtils;
    @Resource
    HttpServletRequest request;
    @Resource
    HttpServletResponse response;

    @Resource
    OpenFileUtils openFile;

    @RequestMapping("/view")
    public String view(String file) throws UnsupportedEncodingException {
        // urldecode
        // 设置utf8 编码
        response.setCharacterEncoding("UTF-8");

        file = URLDecoder.decode(file, "UTF-8");
        OutputStream os = null;
        // 判断文件是否是pdf或者doc，图片还是其他文件
        String type = "";
        String export = "";
        Boolean dwn = false;
        // 先判断文件是否存在
        File fp = new File(file);
        try{
            if (!fp.exists()) {
                os = response.getOutputStream();  //创建输出流
                os.write("FILEVIEW:file does not exist.".getBytes());
                os.flush();
                os.close();
                return null;
            }
        }catch (Exception e){
            return null;
        }
        try{
            String ft = file.substring(file.lastIndexOf(".") + 1);
            // 后缀地址做小写处理
            file = file.replace(ft,ft.toLowerCase());
            if (file.endsWith(".pdf")) {
                export = viewUtils.pdf(file);
                openFile.openPdf(export, request, response);
            }else if (file.endsWith(".doc") || file.endsWith(".docx")) {
                export = viewUtils.word(file);
                openFile.openPdf(export, request, response);
            }else if (file.endsWith(".jpg") || file.endsWith(".jpeg") || file.endsWith(".png") || file.endsWith(".bmp")) {
                export = viewUtils.img(file);
                openFile.openImg(export, request, response);
            }else if(file.endsWith(".gif")) {
                export = viewUtils.img(file);
                openFile.openGif(export, request, response);
            }
            else {
                // 创建下载进程
                openFile.dwn(file, request, response);
            }
        }catch (Exception e){
           try{
               os = response.getOutputStream();  //创建输出流
               // 设置utf8 编码
               os.write(e.getMessage().getBytes());
               os.flush();
               os.close();
           }finally {
               return null;
           }
        }
        if(dwn){
            return null;
        }
        return null;
    }
}
