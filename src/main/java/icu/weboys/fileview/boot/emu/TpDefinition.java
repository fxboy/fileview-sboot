package icu.weboys.fileview.boot.emu;

import icu.weboys.fileview.boot.impl.IView;
import icu.weboys.fileview.boot.impl.ReturnProcessor;

import javax.swing.text.html.HTML;

import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TpDefinition {
    public final static Configuration FTL_CONFIGURATION = new Configuration();
    public final static Map<String,Object> ENABLE_IMAGE_VIEW_TYPE = new ConcurrentHashMap<>(16);
    public final static Map<String, String> APPLICATION_TYPE = new ConcurrentHashMap<String, String>(100);
    public final static Map<String, IView> APPLICATION_IVIEW_ONLINE = new ConcurrentHashMap<String, IView>(84);
    public final static Map<String, IView> APPLICATION_IVIEW = new ConcurrentHashMap<String, IView>(84);
    public static ReturnProcessor RETURN_PROCESSOR = null;
    public static String ROOT_PATH = null;
    public static String FILE_STATIC_PATH = null;
}
