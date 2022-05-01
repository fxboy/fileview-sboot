package icu.weboys.fileview.boot.impl;

import java.io.File;
import java.io.FileInputStream;


public interface IFile {
    String getFileName();

    String getFilePath();

    File getFile();

    String getType();

    Boolean getStream();

    Boolean getEnable();

    String getOutFileName();

    FileInputStream getInputStream();

    String getMdTypeName(String type);

    String getMdTypePath(String type);
}
