package Utilities;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class utilityClasses {

    //TODO :: Get Latest Log File
    public static File getLastFileLog(String pathFolder)
    {
        File folder  = new File(pathFolder);
        File [] files = folder.listFiles();
        if (files.length == 0) return null;
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }

}
