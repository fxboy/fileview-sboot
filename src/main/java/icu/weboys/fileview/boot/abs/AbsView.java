package icu.weboys.fileview.boot.abs;

import icu.weboys.fileview.boot.impl.IFile;
import icu.weboys.fileview.boot.impl.IView;

import java.io.FileInputStream;
import java.io.IOException;

public class AbsView implements IView {

    @Override
    public FileInputStream open(IFile file) throws IOException {
        return file.getInputStream();
    }

    @Override
    public IFile view(IFile file) throws IOException {
        file.setEndFilePath(file.getFilePath());
        return file;
    }

    @Override
    public IFile download(IFile file) {
        return file;
    }
}
