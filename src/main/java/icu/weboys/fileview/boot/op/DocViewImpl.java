package icu.weboys.fileview.boot.op;

import icu.weboys.fileview.boot.abs.AbsView;
import icu.weboys.fileview.boot.abs.AbsViewFile;
import icu.weboys.fileview.boot.ano.ViewImpl;
import icu.weboys.fileview.boot.impl.IFile;
import icu.weboys.fileview.boot.impl.IView;
import icu.weboys.fileview.boot.util.conv.ConvertUtils;
import icu.weboys.fileview.boot.util.page.ViewUtils;

import java.io.FileInputStream;
import java.io.IOException;

public class DocViewImpl extends AbsView {


    @Override
    public FileInputStream open(IFile file) throws IOException {
       return new FileInputStream(ConvertUtils.wordToPdf(file));
    }

    @Override
    public String view(IFile file) throws IOException {
        return ViewUtils.cvHtml(ConvertUtils.pdfToImageBase64(ConvertUtils.wordToPdf(file)));
    }

    @Override
    public String download(IFile file) {
        return null;
    }
}

