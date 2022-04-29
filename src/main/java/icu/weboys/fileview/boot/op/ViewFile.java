package icu.weboys.fileview.boot.op;


import icu.weboys.fileview.boot.abs.AbsViewFile;

import java.io.File;

public class ViewFile extends AbsViewFile {

    public ViewFile(String filePath) {
        super(filePath);
    }

    public ViewFile(File file) {
        super(file);
    }
}
