package icu.weboys.fileview.boot.abs;

import icu.weboys.fileview.boot.emu.TpDefinition;
import icu.weboys.fileview.boot.impl.IFile;
import icu.weboys.fileview.boot.util.file.FPUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.FpUtils;

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
    private String openPath;
    // 使用的主题
    private String theme;
    // 是否开启模板预览模式
    private Boolean enableView = false;
    // 预览模式转成后HTML文件名称
    private String viewFileName;
    // 转换后的文件存在的路径
    private File convertFile;

    private FileInputStream inputStream;

    public AbsViewFile(String filePath) {
        try{
            this.isStream = false;
            this.filePath = filePath;
            this.file = new File(filePath);
            this.fileName = FPUtils.getFileName(filePath);
            this.type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            this.inputStream = new FileInputStream(file);
            this.outFileName = FPUtils.fileMd5Hash(file);
            this.init();
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
            this.outFileName = FPUtils.fileMd5Hash(file);
            this.init();
        } catch (Exception e) {
            this.enable = false;
        }
    }

    public AbsViewFile(MultipartFile file) {
        if (file == null) {
            this.enable = false;
        }
        try {
            this.type = FPUtils.getFileType(file);
            this.isStream = true;
            this.inputStream = (FileInputStream) file.getInputStream();
            String md5 = FPUtils.fileMd5Hash(this.inputStream);
            Boolean save = true;
            this.filePath = FPUtils.getTempName(md5, this.type);
            this.file = new File(this.filePath);
            // 根据md5判断文件是否存在，如果不存在则保存
            if (this.file.exists()) {
                save = false;
            }
            if (save) {
                file.transferTo(this.file);
            }
            this.fileName = FPUtils.getFileName(filePath);
            this.outFileName = md5;
            this.init();
        } catch (IOException e) {
            this.enable = false;
        }
    }
    
    public void init() {
        // 初始化预览开关
        if (TpDefinition.ENABLE_IMAGE_VIEW_TYPE.containsKey(this.type)) {
            this.enableView = true;
            this.theme = TpDefinition.ENABLE_IMAGE_VIEW_TYPE.get(this.type).toString();
        }
        this.viewFileName = this.outFileName;
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

    @Override
    public String getViewFileName() {
        return this.viewFileName;
    }

    @Override
    public String getMdTypeName(String type) {
        return this.fileName.replace(this.type,type);
    }

    @Override
    public String getMdTypePath(String type) {
        // 如果是流的话，文件路径和之前的路径是一样的，但是如果是查找的文件，则路径应该去存档文件夹找
        if (isStream) {
            return this.filePath.replace(String.format("%s.%s", this.fileName, this.type),
                    String.format("%s.%s", this.fileName, type));
        } else {
            return FPUtils.getTempName(this.outFileName, type);
        }
    }
    
    @Override
    public String getTheme() {
        return this.theme;
    }

    @Override
    public Boolean isEnableThemeView() {
        return this.enableView;
    }

    @Override
    public void setEndFilePath(String filepath) {
        this.openPath = filepath;
    }

    @Override
    public String getEndFilePath() {
        return this.openPath;
    }

}
