package io.github.fxboy.fileview.sboot.controller;

import io.github.fxboy.fileview.sboot.bean.FileViewRun;
import io.github.fxboy.fileview.sboot.util.OpenFileUtils;
import io.github.fxboy.fileview.sboot.util.ViewUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.Locale;
import java.util.UUID;


@Controller
@CrossOrigin
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
        response.setContentType("text/html;charset=utf-8");
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
                viewUtils.writeError("FILEVIEW:file does not exist.",response);
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
            }
            else if(type.equals(".html") || type.equals(".html")) {
                openFile.openHtml(file, request, response);
            }
            else if (file.endsWith(".doc") || file.endsWith(".docx")) {
                export = viewUtils.word(file);
                if(export.startsWith(FileViewRun.IS_WORD_HTML_FLAG)){
                    openFile.openHtml(export.replace(FileViewRun.IS_WORD_HTML_FLAG,""), request, response);
                    return null;
                }
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
            viewUtils.writeError(e.getMessage(),response);
            return null;
        }
        if(dwn){
            return null;
        }
        return null;
    }

    @PostMapping("/stream/{type}")
    public String viewMit(@PathVariable("type") String type, MultipartFile file)  {
        if(file == null){
            viewUtils.writeError("FILEVIEW:file stream not found, please confirm whether to upload",response);
        }
        if(type == null || type.trim().equals("")){
            viewUtils.writeError("FILEVIEW:The type parameter cannot be empty",response);
        }

        if(!"file".equals(type)){
            type = "." + type;
        }else{
            String name = file.getOriginalFilename();
            type = name.substring(name.lastIndexOf("."));
        }
        type = type.toLowerCase(Locale.ROOT);
        try{
            if (type.equals(".pdf")) {
                openFile.openPdf(file.getInputStream(), request, response);
            }
            else if(type.equals(".html") || type.equals(".htm")) {
                openFile.openHtml(file.getInputStream(), request, response);
            }
            else if (type.equals(".doc") || type.equals(".docx")) {
                String export = viewUtils.word(file, file.getOriginalFilename());
                        //viewUtils.saveWord(UUID.randomUUID().toString()+type,file);
                if(export.startsWith(FileViewRun.IS_WORD_HTML_FLAG)){
                    openFile.openHtml(export.replace(FileViewRun.IS_WORD_HTML_FLAG,""), request, response);
                    return null;
                }
                openFile.openPdf(export, request, response);
            }else if (type.equals(".jpg") || type.equals(".jpeg") || type.equals(".png") || type.equals(".bmp")) {
                openFile.openImg(file.getInputStream(), request, response);
            }else if(type.equals(".gif")) {
                openFile.openGif(file.getInputStream(), request, response);
            }
            else {
                openFile.dwn(type,file.getInputStream(), request, response);
            }
        } catch (FileNotFoundException e) {
            viewUtils.writeError("FILEVIEW:" + e.getMessage(),response);
        } catch (IOException e) {
            viewUtils.writeError("FILEVIEW:" + e.getMessage(),response);
        }
        return null;
    }
}
