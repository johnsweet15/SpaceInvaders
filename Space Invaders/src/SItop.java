import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SItop extends SIinvader {
    
    private BufferedImage top0;
    private BufferedImage top1;
    
    public SItop(int x, int y, int width, int height) {
        super(x, y, width, height);
        
        try {
            top0 = ImageIO.read(getClass().getResourceAsStream("/SItop0.gif"));
            top1 = ImageIO.read(getClass().getResourceAsStream("/SItop1.gif"));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void paint(Graphics g) {
        g.drawImage(top0, getPosX(), getPosY(), getWidth(), getHeight(), null);
    }
    public void paint2(Graphics g) {
        g.drawImage(top1, getPosX(), getPosY(), getWidth(), getHeight(), null);
    }

    
}
