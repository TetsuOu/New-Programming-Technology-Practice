import java.io.*;
import java.util.Properties;
public class PropertyUtil {
    public static String getValue(String key){
        String ret = null;
        try{
            InputStream in = PropertyUtil.class.getResourceAsStream("config.properties");
            Properties properties = new Properties();
            properties.load(in);
            ret = properties.getProperty(key);
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
}
