package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ScriptExecutor {

    public ScriptExecutor() throws IOException {
        /**
         * 1.load compiled lib
         * 2.create file in temp dir
         * 3.copy lib to temp dir
         * 4.load the lib in temp file
         */
        InputStream inputStream = getClass().getResourceAsStream("/util/libmiddleware.so");//load lib as text
        File tempf = File.createTempFile("libmiddleware", "so");
        FileOutputStream fos = new FileOutputStream(tempf);//establish pipe to tempf
        int length = 0;
        byte[] buffer = new byte[1024];
        while ((length = inputStream.read(buffer)) != -1) {
            fos.write(buffer, 0, length);
        }
        inputStream.close();
        fos.close();
        System.load(tempf.getAbsolutePath());
    }

    public native String execute(String filename, String[] arg);

}
