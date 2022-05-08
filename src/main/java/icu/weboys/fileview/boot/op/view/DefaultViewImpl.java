package icu.weboys.fileview.boot.op.view;

import icu.weboys.fileview.boot.abs.AbsView;
import icu.weboys.fileview.boot.impl.IFile;

import java.io.FileInputStream;
import java.io.IOException;

public class DefaultViewImpl extends AbsView {
    @Override
    public FileInputStream open(IFile file) throws IOException {
        return super.open(file);
    }

    @Override
    public IFile view(IFile file) throws IOException {
        return super.view(file);
    }

    @Override
    public IFile download(IFile file) {
        return super.download(file);
    }
}
