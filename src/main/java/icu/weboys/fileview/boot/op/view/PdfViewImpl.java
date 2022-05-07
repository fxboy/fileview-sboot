package icu.weboys.fileview.boot.op.view;

import icu.weboys.fileview.boot.abs.AbsView;
import icu.weboys.fileview.boot.abs.AbsViewFile;
import icu.weboys.fileview.boot.impl.IFile;
import icu.weboys.fileview.boot.impl.IView;
import icu.weboys.fileview.boot.util.conv.ConvertUtils;
import icu.weboys.fileview.boot.util.file.FPUtils;
import icu.weboys.fileview.boot.util.page.ViewUtils;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PdfViewImpl extends AbsView {

    @Override
    public FileInputStream open(IFile file) throws IOException {
       return super.open(file);
    }

    @Override
    public String view(IFile file) throws IOException {
        // view 转图片
        try {
            if (!file.isEnableThemeView()) {
                // 开启图片转换
                return null;
            }

            String[] imgs = ConvertUtils.pdfToImageBase64(file.getFile());
            return ConvertUtils.getFTLTheme(file,new HashMap<String,Object>(){{
                    put("views", imgs);
                    put("title", file.getFileName() + "." + file.getType() + "- 在线预览");
                    put("size", imgs.length);
                    put("download", file.getFilePath());
            }});
        }catch (IOException e){
           throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String download(IFile file) {
        return null;
    }




    public void nullTest(){
        try{
            String name = null;
            name.trim();
        }catch (Exception ex){
            new RuntimeException(ex.getMessage());
        }
    }
}
