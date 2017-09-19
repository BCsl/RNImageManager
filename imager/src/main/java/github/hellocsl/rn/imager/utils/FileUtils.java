package github.hellocsl.rn.imager.utils;

/**
 * Created by chensuilun on 2017/9/18.
 */
public class FileUtils {

    /**
     * 剔除后缀
     *
     * @param filename
     * @return
     */
    public static String getNameNoSuffix(String filename) {
        int dotPosition = filename.lastIndexOf('.');
        if (dotPosition != -1) {
            return filename.substring(0, dotPosition);
        }
        return filename;
    }


    public static String getSuffix(String filename) {
        int dotPosition = filename.lastIndexOf('.');
        if (dotPosition != -1) {
            return filename.substring(dotPosition, filename.length());
        }
        return "";
    }
}
