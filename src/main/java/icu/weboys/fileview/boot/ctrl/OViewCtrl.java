package icu.weboys.fileview.boot.ctrl;

import icu.weboys.fileview.boot.emu.TpDefinition;
import icu.weboys.fileview.boot.util.file.FPUtils;
import icu.weboys.fileview.boot.util.page.ViewUtils;
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
    @RequestMapping({"/file/{type}"})
    @ResponseBody
    public String open(@PathVariable("type") String type, @RequestParam("file") String path) {
        String var4 = "";
        try {
            this.response.setHeader("fpt", path);
            switch (type) {
                case "open":
                    this.viewUtils.run(FPUtils.getFileType(path), ViewUtils.open(path), this.response);
                    break;
                case "view":
                    this.viewUtils.process(TpDefinition.ENABLE_IMAGE_VIEW_TYPE.containsKey(FPUtils.getFileType(path)) ? "html" : FPUtils.getFileType(path), ViewUtils.view(path), this.response);
                    break;
                case "down":
                    ViewUtils.download(path);
                    break;
                default:
                    var4 = "The current type is not supported!";
                    break;
            }
        } catch (Exception var9) {
            var4 = String.format("Something went wrong,%s", var9.getMessage());
        }finally {
            return var4;
        }
    }

    @PostMapping({"/stream/{type}"})
    public String view(@PathVariable("type") String type, @RequestParam("file") MultipartFile file) {
        String var4 = "";
        try {
            this.response.setHeader("fpt", file.getOriginalFilename());
            switch (type) {
                case "open":
                    this.viewUtils.run(FPUtils.getFileType(file), ViewUtils.open(file), this.response);
                case "view":
                    this.viewUtils.process(TpDefinition.ENABLE_IMAGE_VIEW_TYPE.containsKey(FPUtils.getFileType(file)) ? "html" : FPUtils.getFileType(file), ViewUtils.view(file), this.response);
                case "down":
                    ViewUtils.download(file);
                    break;
                default:
                    var4 = "The current type is not supported!";
                    break;
            }
        } catch (Exception var9) {
            var4 = String.format("Something went wrong,%s", var9.getMessage());
            return "";
        } finally {
            return var4;
        }
    }


}
