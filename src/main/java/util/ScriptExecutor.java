package util;

public class ScriptExecutor {

    public ScriptExecutor(){
        /**
         * 1.load compiled lib
         * 2.create file in temp dir
         * 3.copy lib to temp dir
         * 4.load the lib in temp file
         */

    }
    public native String execute(String filename, String[] arg);

}
