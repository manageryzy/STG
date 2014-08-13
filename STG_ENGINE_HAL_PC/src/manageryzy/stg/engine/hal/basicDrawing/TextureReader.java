package manageryzy.stg.engine.hal.basicDrawing;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.jogamp.common.nio.Buffers;
//import 
  
/** 
 * Image loading class that converts BufferedImages into a data 
 * structure that can be easily passed to OpenGL. 
 * @author Pepijn Van Eeckhoudt 
 * ͼƬ�����ࣺ��λͼת��ΪOpenGL���׶�ȡ������ݸ�ʽ 
 */  
public class TextureReader {  
    /** 
     * ͨ���ļ����ȡ���� 
     * @param filename �ļ���·���� 
     * @return ������Ϣ 
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
     * ��ȡͼƬ�� BufferedImage 
     * @param resourceName 
     * @return 
     * @throws IOException 
     */  
    private static BufferedImage readImage(String resourceName) throws IOException {  
        return ImageIO.read(ResourceRetriever.getResourceAsStream(resourceName));  
    }  
  
    /** 
     * ��ȡ������Ϣ 
     * @param img Ҫ��ȡ��ͼƬ 
     * @param storeAlphaChannel �Ƿ���Alpha ͨ�� 
     * @return 
     */  
    private static Texture readPixels(BufferedImage img, boolean storeAlphaChannel) {  
        int[] packedPixels = new int[img.getWidth() * img.getHeight()];  
  
        /** 
         * ����һ�� PixelGrabber �����Դ�ָ�� Img ����ɵ�ͼ���н����ؾ��β��� (x, y, w, h) ץȡ��������飨packedPixels���С� 
         */  
        PixelGrabber pixelgrabber = new PixelGrabber(img, 0, 0, img.getWidth(), img.getHeight(), packedPixels, 0, img.getWidth());  
        try {  
            //���� Image �� ImageProducer ��ʼ�������أ����ȴ������ؾ����е���������  
            pixelgrabber.grabPixels();  
        } catch (InterruptedException e) {  
            throw new RuntimeException();  
        }  
  
        //���Ϊ4ͨ�� ��˵������RGB ���� alpha ֵ ����ֻ�� RGBֵ  
        int bytesPerPixel = storeAlphaChannel ? 4 : 3;  
        //����һ���µĽ������ػ�����  
        ByteBuffer unpackedPixels = Buffers.newDirectByteBuffer(packedPixels.length * bytesPerPixel);  
  
        //��img�е�RGB������ֵ ת��Ϊ Byte���͵� ��ݻ���  
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
  
        //���������е���ݷ�ת  
        unpackedPixels.flip();  
  
  
        return new Texture(unpackedPixels, img.getWidth(), img.getHeight());  
    }  
  
    /** 
     * �����洢������Ϣ���� 
     */  
    public static class Texture {  
        /** 
         * ���� 
         */  
        private ByteBuffer pixels;  
        /** 
         * �� 
         */  
        private int width;  
        /** 
         * �� 
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