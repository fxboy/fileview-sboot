package icu.weboys.fileview.boot.op.view;

import icu.weboys.fileview.boot.abs.AbsView;
import icu.weboys.fileview.boot.impl.IFile;
import icu.weboys.fileview.boot.util.conv.ConvertUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
public class PdfViewImpl extends AbsView {

    @Override
    public FileInputStream open(IFile file) throws IOException {
       return super.open(file);
    }

    @Override
    public IFile view(IFile file) throws IOException {
        // view 转图片
        try {
            if (!file.isEnableThemeView()) {
                // 开启图片转换
                return null;
            }
            String[] imgs = ConvertUtils.pdfToImageBase64(file.getFile());
            file.setEndFilePath(ConvertUtils.getFTLTheme(file, new HashMap<String, Object>() {
                {
                    put("views", imgs);
                    put("title", file.getFileName() + "." + file.getType() + "- 在线预览");
                    put("size", imgs.length);
                    put("download", URLEncoder.encode(file.getFilePath(), "UTF-8"));
                }
            }));
            return file;
        }catch (IOException e){
           throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public IFile download(IFile file) {
        return super.download(file);
    }
}
