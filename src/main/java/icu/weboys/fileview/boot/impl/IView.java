package icu.weboys.fileview.boot.impl;

import icu.weboys.fileview.boot.abs.AbsViewFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;


public interface IView {
    FileInputStream open(IFile file) throws IOException;
    IFile view(IFile file) throws IOException;
    IFile download(IFile file);
}
