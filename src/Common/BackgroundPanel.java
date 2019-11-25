package Common;

import java.awt.Color;
import java.awt.Graphics;      
import java.awt.Image;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;      
              
public class BackgroundPanel extends JPanel {      
              
    /**      
     *       
     */
    private static final long serialVersionUID = -898997756345637309L;      
    private Image image;      
              
    /**      
     * Constructor      
     */
    public BackgroundPanel() {
    	 super();      
         setOpaque(false);      
         setLayout(null); 
    }
    public void setImage(Image image) {      
        this.image = image;      
    }      
              
    @Override
    protected void paintComponent(Graphics g) {      
        if (image != null) {      
            int width = getWidth();// 获取组件大小      
            int height = getHeight();      
            g.drawImage(image, 0, 0, width, height, this);// 绘制图片与组件大小相同      
        }      
        super.paintComponent(g);// 执行超类方法      
    }      
                  
}