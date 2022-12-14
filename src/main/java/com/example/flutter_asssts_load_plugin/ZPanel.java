package com.example.flutter_asssts_load_plugin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ZPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    private Image image;
    private int imgWidth = 0;
    private int imgHeight = 0;

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public ZPanel() {
    }

    public void setImagePath(String imgPath) {
        // 该方法不推荐使用，该方法是懒加载，图像并不加载到内存，当拿图像的宽和高时会返回-1；  
        // image = Toolkit.getDefaultToolkit().getImage(imgPath);  
        try {
            // 该方法会将图像加载到内存，从而拿到图像的详细信息。  
            image = ImageIO.read(new FileInputStream(imgPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        setImgWidth(image.getWidth(this));
//        setImgHeight(image.getHeight(this));
    }

    @Override
    public void paintComponent(Graphics g1) {
        int x = 0;
        int y = 0;
        Graphics g = (Graphics) g1;
        if (null == image) {
            return;
        }

        g.drawImage(image, x, y, imgWidth == 0 ? image.getWidth(this) : getImgWidth(), imgHeight == 0 ? image.getHeight(this) : getImgHeight(),
                this);
        g = null;
    }
}  