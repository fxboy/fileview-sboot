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
    public String view(IFile file) throws IOException {
        byte[] bytes = new byte[1024];
        FileInputStream inputStream = file.getInputStream();
        inputStream.available();
        if(inputStream.available() != -1){
            inputStream.read(bytes);
        }
        return new String(bytes, "UTF-8");
    }

    @Override
    public String download(IFile file) {
        return null;
    }
}
