package icu.weboys.fileview.boot.op;

import icu.weboys.fileview.boot.abs.AbsViewFile;
import org.springframework.web.multipart.MultipartFile;

public class ViewStream extends AbsViewFile {

    public ViewStream(MultipartFile file) {
        super(file);
    }
}
