package icu.weboys.fileview.boot.op;

import icu.weboys.fileview.boot.abs.AbsView;
import icu.weboys.fileview.boot.abs.AbsViewFile;
import icu.weboys.fileview.boot.ano.ViewImpl;
import icu.weboys.fileview.boot.impl.IFile;
import icu.weboys.fileview.boot.impl.IView;
import icu.weboys.fileview.boot.util.conv.ConvertUtils;
import icu.weboys.fileview.boot.util.file.FPUtils;
import icu.weboys.fileview.boot.util.page.ViewUtils;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class PdfViewImpl extends AbsView {

    @Override
    public FileInputStream open(IFile file) throws IOException {
       return super.open(file);
    }

    @Override
    public String view(IFile file) throws IOException {
        // view 转图片
        try{
            return ViewUtils.cvHtml(ConvertUtils.pdfToImageBase64(file.getFile()));
        }catch (IOException e){
           throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String download(IFile file) {
        return null;
    }
}
