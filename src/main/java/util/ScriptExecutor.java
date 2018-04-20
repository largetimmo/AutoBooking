package util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class ScriptExecutor {
    private final static String OS = System.getProperty("os.name").toLowerCase();

    public ScriptExecutor() throws IOException {
        /**
         * 1.load compiled lib
         * 2.create file in temp dir
         * 3.copy lib to temp dir
         * 4.load the lib in temp file
         */
        String libname;
        switch (OS) {
            case "mac os x":
                libname = "/lib/mac.so";
                break;
            case "linux":
                libname = "/lib/linux.so";
                break;
            default:
                libname = "lib/linux.so";
                break;
        }
        Resource resource = new ClassPathResource(libname);
        System.load(resource.getFile().getAbsolutePath());
    }

    public native String execute(String filename, String[] arg);
}
