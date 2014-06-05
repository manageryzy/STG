package manageryzy.stg.engine.hal.basicDrawing;

import com.jogamp.opengl.util.*;
//import 
  
import com.jogamp.common.nio.Buffers; 
import javax.imageio.ImageIO;  
import java.awt.image.BufferedImage;  
import java.awt.image.PixelGrabber;  
import java.io.IOException;  
import java.nio.ByteBuffer;  
  
/** 
 * Image loading class that converts BufferedImages into a data 
 * structure that can be easily passed to OpenGL. 
 * @author Pepijn Van Eeckhoudt 
 * 图片导入类：将位图转化为OpenGL容易读取的是数据格式 
 */  
public class TextureReader {  
    /** 
     * 通过文件名读取纹理 
     * @param filename 文件的路径名 
     * @return 纹理信息 
     * @throws IOException 
     */  
    public static Texture readTexture(String filename) throws IOException {  
        return readTexture(filename, false);  
    }  
  
    public static Texture readTexture(String filename, boolean storeAlphaChannel) throws IOException {  
        BufferedImage bufferedImage;  
        if (filename.endsWith(".bmp")) {  
            bufferedImage = BitmapLoader.loadBitmap(filename);  
        } else {  
            bufferedImage = readImage(filename);  
        }  
        return readPixels(bufferedImage, storeAlphaChannel);  
    }  
  
    /** 
     * 读取图片到 BufferedImage 
     * @param resourceName 
     * @return 
     * @throws IOException 
     */  
    private static BufferedImage readImage(String resourceName) throws IOException {  
        return ImageIO.read(ResourceRetriever.getResourceAsStream(resourceName));  
    }  
  
    /** 
     * 读取像素信息 
     * @param img 要读取的图片 
     * @param storeAlphaChannel 是否有Alpha 通道 
     * @return 
     */  
    private static Texture readPixels(BufferedImage img, boolean storeAlphaChannel) {  
        int[] packedPixels = new int[img.getWidth() * img.getHeight()];  
  
        /** 
         * 创建一个 PixelGrabber 对象，以从指定 Img 所生成的图像中将像素矩形部分 (x, y, w, h) 抓取到给定的数组（packedPixels）中。 
         */  
        PixelGrabber pixelgrabber = new PixelGrabber(img, 0, 0, img.getWidth(), img.getHeight(), packedPixels, 0, img.getWidth());  
        try {  
            //请求 Image 或 ImageProducer 开始传递像素，并等待传递完相关矩形中的所有像素  
            pixelgrabber.grabPixels();  
        } catch (InterruptedException e) {  
            throw new RuntimeException();  
        }  
  
        //如果为4通道 则说明除了RGB 还有 alpha 值 否则只有 RGB值  
        int bytesPerPixel = storeAlphaChannel ? 4 : 3;  
        //建立一个新的解包的像素缓冲区  
        ByteBuffer unpackedPixels = Buffers.newDirectByteBuffer(packedPixels.length * bytesPerPixel);  
  
        //将img中的RGB的整形值 转换为 Byte类型的 数据缓冲  
        for (int row = img.getHeight() - 1; row >= 0; row--) {  
            for (int col = 0; col < img.getWidth(); col++) {  
                int packedPixel = packedPixels[row * img.getWidth() + col];  
                unpackedPixels.put((byte) ((packedPixel >> 16) & 0xFF));  
                unpackedPixels.put((byte) ((packedPixel >> 8) & 0xFF));  
                unpackedPixels.put((byte) ((packedPixel >> 0) & 0xFF));  
                if (storeAlphaChannel) {  
                    unpackedPixels.put((byte) ((packedPixel >> 24) & 0xFF));  
                }  
            }  
        }  
  
        //将缓冲区中的数据反转  
        unpackedPixels.flip();  
  
  
        return new Texture(unpackedPixels, img.getWidth(), img.getHeight());  
    }  
  
    /** 
     * 用来存储纹理信息的类 
     */  
    public static class Texture {  
        /** 
         * 像素 
         */  
        private ByteBuffer pixels;  
        /** 
         * 宽 
         */  
        private int width;  
        /** 
         * 高 
         */  
        private int height;  
  
        public Texture(ByteBuffer pixels, int width, int height) {  
            this.height = height;  
            this.pixels = pixels;  
            this.width = width;  
        }  
  
        public int getHeight() {  
            return height;  
        }  
  
        public ByteBuffer getPixels() {  
            return pixels;  
        }  
  
        public int getWidth() {  
            return width;  
        }  
    }  
}  