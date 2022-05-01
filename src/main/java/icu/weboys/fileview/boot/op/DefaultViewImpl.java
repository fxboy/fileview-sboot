package icu.weboys.fileview.boot.op;

import icu.weboys.fileview.boot.abs.AbsView;
import icu.weboys.fileview.boot.abs.AbsViewFile;
import icu.weboys.fileview.boot.ano.ViewImpl;
import icu.weboys.fileview.boot.impl.IFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

public class DefaultViewImpl extends AbsView {
    @Override
    public FileInputStream open(IFile file) throws IOException {
        return super.open(file);
    }

    @Override
    public String view(IFile file) throws IOException {
        return super.view(file);
    }

    @Override
    public String download(IFile file) {
        return super.download(file);
    }
}
