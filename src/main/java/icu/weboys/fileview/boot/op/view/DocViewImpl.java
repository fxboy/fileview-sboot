package icu.weboys.fileview.boot.op.view;

import icu.weboys.fileview.boot.abs.AbsView;
import icu.weboys.fileview.boot.abs.AbsViewFile;
import icu.weboys.fileview.boot.impl.IFile;
import icu.weboys.fileview.boot.impl.IView;
import icu.weboys.fileview.boot.util.conv.ConvertUtils;
import icu.weboys.fileview.boot.util.page.ViewUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

public class DocViewImpl extends AbsView {


    @Override
    public FileInputStream open(IFile file) throws IOException {
       return new FileInputStream(ConvertUtils.wordToPdf(file));
    }

    @Override
    public IFile view(IFile file) throws IOException {
        if (!file.isEnableThemeView()) {
            // 开启图片转换
            return null;
        }
        String[] imgs = ConvertUtils.pdfToImageBase64(ConvertUtils.wordToPdf(file));
        file.setEndFilePath(ConvertUtils.getFTLTheme(file, new HashMap<String, Object>() {
            {
                put("views", imgs);
                put("title", file.getFileName() + "- 在线预览");
                put("size", imgs.length);
                put("download", URLEncoder.encode(file.getFilePath(), "UTF-8"));
            }
        }));
        return file;
        //return ViewUtils.cvHtml(ConvertUtils.pdfToImageBase64(ConvertUtils.wordToPdf(file)));
    }

    @Override
    public IFile download(IFile file) {
        return super.download(file);
    }
}

