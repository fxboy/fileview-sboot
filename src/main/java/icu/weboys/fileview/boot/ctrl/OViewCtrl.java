package icu.weboys.fileview.boot.ctrl;

import icu.weboys.fileview.boot.emu.TpDefinition;
import icu.weboys.fileview.boot.impl.IFile;
import icu.weboys.fileview.boot.util.file.FPUtils;
import icu.weboys.fileview.boot.util.page.ViewUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.joran.conditional.IfAction;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.nio.charset.StandardCharsets;

@CrossOrigin
@RestController
@RequestMapping("/fview")
public class OViewCtrl {
    @Resource
    HttpServletRequest request;
    @Resource
    HttpServletResponse response;
    @Resource
    ViewUtils viewUtils;

    @RequestMapping({ "/file/{type}" })
    @ResponseBody
    public String open(@PathVariable("type") String type, @RequestParam("file") String path) {
        String var4 = "";
        try {
            // 设置好文件信息
            File file = new File(path);
            this.response.addHeader("fileName", file.getName());
            this.response.addDateHeader("fileLength", file.length());
            switch (type) {
                case "open":
                    this.viewUtils.run(FPUtils.getFileType(path), ViewUtils.open(path), this.response);
                    break;
                case "view":
                    IFile iFile = ViewUtils.view(path);
                    String filePath = iFile.getEndFilePath();
                    if (filePath == null) {
                        throw new RuntimeException("::file not found");
                    }
                    if (iFile.isEnableThemeView()) {
                        this.viewUtils.process(filePath, response);
                    } else {
                        this.viewUtils.process(iFile, response);
                    }
                    break;
                case "down":
                    this.viewUtils.download(ViewUtils.download(file), response);
                    break;
                default:
                    var4 = "The current type is not supported!";
                    break;
            }
        } catch (Exception var9) {
            var4 = String.format("Something went wrong,%s", var9.getMessage());
        }
        return var4;
    }

    @PostMapping({"/stream/{type}"})
    public String view(@PathVariable("type") String type, @RequestParam("file") MultipartFile file) {
        String var4 = "";
        this.response.addHeader("fileName", file.getOriginalFilename());
        this.response.addDateHeader("fileLength", file.getSize());
        try {
            switch (type) {
                case "open":
                    this.viewUtils.run(FPUtils.getFileType(file), ViewUtils.open(file), this.response);
                case "view":
                    IFile iFile = ViewUtils.view(file);
                    String filePath = iFile.getEndFilePath();
                    if (filePath == null) {
                        throw new RuntimeException("::file not found");
                    }
                    if (iFile.isEnableThemeView()) {
                        this.viewUtils.process(filePath, response);
                    } else {
                        this.viewUtils.process(iFile, response);
                    }
                    break;
                case "down":
                    this.viewUtils.download(ViewUtils.download(file), response);
                    break;
                default:
                    var4 = "The current type is not supported!";
                    break;
            }
        } catch (Exception var9) {
            var4 = String.format("Something went wrong,%s", var9.getMessage());
        }
        return var4;
    }


}
