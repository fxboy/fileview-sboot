package icu.weboys.fileview.boot.abs;

import icu.weboys.fileview.boot.impl.IFile;
import icu.weboys.fileview.boot.util.file.FPUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class AbsViewFile implements IFile {
    private String fileName;
    private String filePath;
    private File file;
    private String type;
    private Boolean isStream;
    private Boolean enable = true;
    private String outFileName;

    private FileInputStream inputStream;

    public AbsViewFile(String filePath) {
        try{
            this.isStream = false;
            this.filePath = filePath;
            this.file = new File(filePath);
            this.fileName = file.getName();
            this.type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            this.inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            this.enable = false;
        }
    }

    public AbsViewFile(File file) {
        if(!file.exists()){
            this.enable = false;
        }
        try{
            this.isStream = false;
            this.filePath = file.getPath();
            this.file = file;
            this.fileName = file.getName();
            this.type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            this.inputStream = new FileInputStream(file);
        } catch (Exception e) {
            this.enable = false;
        }
    }

    public AbsViewFile(MultipartFile file) {
        if(file == null){
            this.enable = false;
        }
        try{
            this.isStream = true;
            this.inputStream = (FileInputStream) file.getInputStream();
            this.filePath = FPUtils.getTempName(FPUtils.getFileType(file));
            this.fileName = FPUtils.getFileName(filePath);
            this.file = new File(this.filePath);
            file.transferTo(this.file);
            this.type = file.getName().substring(file.getName().lastIndexOf(".") + 1);

        } catch (IOException e) {
            this.enable = false;
        }
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public String getFilePath() {
        return this.filePath;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public Boolean getStream() {
        return this.isStream;
    }

    @Override
    public Boolean getEnable() {
        return this.enable;
    }

    @Override
    public String getOutFileName() {
        return this.outFileName;
    }

    @Override
    public FileInputStream getInputStream() {
        return this.inputStream;
    }
}
