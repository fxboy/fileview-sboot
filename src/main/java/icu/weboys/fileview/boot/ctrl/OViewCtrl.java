package icu.weboys.fileview.boot.ctrl;

import icu.weboys.fileview.boot.emu.TpDefinition;
import icu.weboys.fileview.boot.impl.IFile;
import icu.weboys.fileview.boot.op.ViewFile;
import icu.weboys.fileview.boot.util.file.FPUtils;
import icu.weboys.fileview.boot.util.page.ViewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping("/file/{type}")
    @ResponseBody
    public String open(@PathVariable("type") String type, @RequestParam("file") String path) {
        try{
            switch (type) {
                case "open":
                    viewUtils.run(FPUtils.getFileType(path),ViewUtils.open(path),response);
                case "view":
                    viewUtils.run(TpDefinition.ENABLE_IMAGE_VIEW_TYPE.containsKey(FPUtils.getFileType(path))?"html":FPUtils.getFileType(path),ViewUtils.view(path).getBytes(StandardCharsets.UTF_8),response);
                case "down":
                    return ViewUtils.download(path);
                default:
                    return "The current type is not supported!";
            }
        }catch (Exception e){
            return String.format("Something went wrong,%s",e.getMessage());
        }finally {
            return "";
        }
    }

    @PostMapping("/stream/{type}")
    public String view(@PathVariable("type") String type, @RequestParam("file") MultipartFile file) {
        try{
            switch (type) {
                case "open":
                    viewUtils.run(FPUtils.getFileType(file),ViewUtils.open(file),response);
                case "view":
                    viewUtils.run(TpDefinition.ENABLE_IMAGE_VIEW_TYPE.containsKey(FPUtils.getFileType(file))?"html":FPUtils.getFileType(file),ViewUtils.view(file).getBytes(StandardCharsets.UTF_8),response);
                case "down":
                    return ViewUtils.download(file);
                default:
                    return "The current type is not supported!";
            }
        }catch (Exception e){
            return String.format("Something went wrong,%s",e.getMessage());
        }finally {
            return "";
        }
    }


}
