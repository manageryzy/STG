package manageryzy.stg.engine.hal.basicDrawing;  

import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.net.URL;  
  
public class ResourceRetriever {  
    // 通过文件名加载相关的文件,返回 URL  
    public static URL getResource(final String filename) throws IOException {  
        // 尝试加载jar 资源  
        URL url = ResourceRetriever.class.getClassLoader().getResource(filename);  
        // If not found in jar, then load from disk  
        //如果没有发现这个jar文件，则从硬盘载入  
        if (url == null) {  
            return new URL("file", "localhost", filename);  
        } else {  
            return url;  
        }  
    }  
  
    // 通过文件名得到相关的输入流  
    public static InputStream getResourceAsStream(final String filename)   
                                    throws IOException {  
        // Try to load resource from jar  
         //尝试加载jar 资源 , 将filename中的'\\'由'/'替换  
        String convertedFileName = filename.replace('\\', '/');  
        InputStream stream = ResourceRetriever.class.getClassLoader()  
                                   .getResourceAsStream(convertedFileName);  
        // If not found in jar, then load from disk  
        //如果没有发现这个jar文件，则从硬盘载入  
        if (stream == null) {  
            return new FileInputStream(convertedFileName);  
        } else {  
            return stream;  
        }  
    }  
}  