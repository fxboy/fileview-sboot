package icu.weboys.fileview.boot.emu;

import icu.weboys.fileview.boot.impl.IView;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TpDefinition {
    public static Map<String,Object> ENABLE_IMAGE_VIEW_TYPE = new ConcurrentHashMap<>(16);
    public static Map<String,String> APPLICATION_TYPE =  new ConcurrentHashMap<String, String>(84){{
        put("jpg","image/jpeg");
        put("jpeg","image/jpeg");
        put("png","image/png");
        put("gif","image/gif");
        put("bmp","image/bmp");
        put("jpeg","image/jpeg");
        put("ico","image/x-icon");
        put("tif","image/tiff");
        put("tiff","image/tiff");
        put("svg","image/svg+xml");
        put("svgz","image/svg+xml");
        put("webp","image/webp");
        put("pdf","application/pdf");
        put("ps","application/postscript");
        put("ai","application/postscript");
        put("eps","application/postscript");
        put("psd","application/octet-stream");
        put("zip","application/zip");
        put("rar","application/x-rar-compressed");
        put("7z","application/x-7z-compressed");
        put("tar","application/x-tar");
        put("gz","application/x-gzip");
        put("bz2","application/x-bzip2");
        put("doc","application/msword");
        put("docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        put("xls","application/vnd.ms-excel");
        put("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        put("ppt","application/vnd.ms-powerpoint");
        put("pptx","application/vnd.openxmlformats-officedocument.presentationml.presentation");
        put("odt","application/vnd.oasis.opendocument.text");
        put("ods","application/vnd.oasis.opendocument.spreadsheet");
        put("odp","application/vnd.oasis.opendocument.presentation");
        put("sxw","application/vnd.sun.xml.writer");
        put("stw","application/vnd.sun.xml.writer.template");
        put("sxc","application/vnd.sun.xml.calc");
        put("stc","application/vnd.sun.xml.calc.template");
        put("sxd","application/vnd.sun.xml.draw");
        put("std","application/vnd.sun.xml.draw.template");
        put("sxi","application/vnd.sun.xml.impress");
        put("sti","application/vnd.sun.xml.impress.template");
        put("sxg","application/vnd.sun.xml.writer.global");
        put("sxm","application/vnd.sun.xml.math");
        put("tar.gz","application/x-gtar");
        put("tgz","application/x-gtar");
        put("tar.bz2","application/x-bzip2");
        put("tbz","application/x-bzip2");
        put("mp3","audio/mpeg");
        put("wav","audio/x-wav");
        put("aif","audio/x-aiff");
        put("aiff","audio/x-aiff");
        put("aifc","audio/x-aiff");
        put("ram","audio/x-pn-realaudio");
        put("rm","audio/x-pn-realaudio");
        put("ra","audio/x-realaudio");
        put("rmm","audio/x-pn-realaudio");
        put("rmp","audio/x-pn-realaudio");
        put("m3u","audio/x-mpegurl");
        put("wma","audio/x-ms-wma");
        put("wax","audio/x-ms-wax");
        put("ogg","audio/ogg");
        put("midi","audio/midi");
        put("mid","audio/midi");
        put("kar","audio/midi");
        put("mp4a","audio/mp4");
        put("m4a","audio/mp4");
        put("m4b","audio/mp4");
        put("m4p","audio/mp4");
        put("mpga","audio/mpeg");
        put("mp2","audio/mpeg");
        put("mp2a","audio/mpeg");
        put("m2a","audio/mpeg");
        put("m3a","audio/mpeg");
        put("oga","audio/ogg");
        put("spx","audio/ogg");
        put("flac","audio/flac");
        put("aac","audio/x-aac");
        put("amr","audio/amr");
        put("mka","audio/x-matroska");
        put("wpl","application/vnd.ms-wpl");
        put("wvx","video/x-ms-wvx");
        put("avi","video/x-msvideo");
        put("movie","video/x-sgi-movie");
        put("mpv","video/x-matroska");
        put("mkv","video/x-matroska");
        put("ice","x-conference/x-cooltalk");
        put("html","text/html");
        put("xml","text/xml");
        put("htm","text/html");
        put("htmls","text/html");
        put("txt","text/plain");
        put("ini","text/plain");
        put("sql","text/plain");
        put("java","text/plain");
        put("css","text/css");
        put("javascript","text/plain");
        put("js","text/plain");
        put("log","text/plain");
        put("vue","text/plain");
        put("c","text/plain");
        put("cpp","text/plain");
        put("cs","text/plain");
    }};

    public static Map<String, IView> APPLICATION_IVIEW_ONLINE =  new ConcurrentHashMap<String,IView>(84){{

    }};
    public static Map<String, IView> APPLICATION_IVIEW =  new ConcurrentHashMap<String,IView>(84){{

    }};


    public static String ROOT_PATH = null;
    {{
        String ROOT_PATH = this.getClass().getResource("/").getPath().substring(1) + "/" + "FILEVIEW_TEMP/";
    }}
}
