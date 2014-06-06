package manageryzy.stg.engine.hal.basicDrawing;  

import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.net.URL;  
  
public class ResourceRetriever {  
    // ͨ���ļ���������ص��ļ�,���� URL  
    public static URL getResource(final String filename) throws IOException {  
        // ���Լ���jar ��Դ  
        URL url = ResourceRetriever.class.getClassLoader().getResource(filename);  
        // If not found in jar, then load from disk  
        //���û�з������jar�ļ������Ӳ������  
        if (url == null) {  
            return new URL("file", "localhost", filename);  
        } else {  
            return url;  
        }  
    }  
  
    // ͨ���ļ����õ���ص�������  
    public static InputStream getResourceAsStream(final String filename)   
                                    throws IOException {  
        // Try to load resource from jar  
         //���Լ���jar ��Դ , ��filename�е�'\\'��'/'�滻  
        String convertedFileName = filename.replace('\\', '/');  
        InputStream stream = ResourceRetriever.class.getClassLoader()  
                                   .getResourceAsStream(convertedFileName);  
        // If not found in jar, then load from disk  
        //���û�з������jar�ļ������Ӳ������  
        if (stream == null) {  
            return new FileInputStream(convertedFileName);  
        } else {  
            return stream;  
        }  
    }  
}  