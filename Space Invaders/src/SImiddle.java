import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SImiddle extends SIinvader {
    
    private BufferedImage middle0;
    private BufferedImage middle1;
    
    public SImiddle(int x, int y, int width, int height) {
        super(x, y, width, height);
        
        try {
            middle0 = ImageIO.read(getClass().getResourceAsStream("/SImiddle0.gif"));
            middle1 = ImageIO.read(getClass().getResourceAsStream("/SImiddle1.gif"));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(middle0, getPosX(), getPosY(), getWidth(), getHeight(), null);
    }
    public void paint2(Graphics g) {
        g.drawImage(middle1, getPosX(), getPosY(), getWidth(), getHeight(), null);
    }

    
}
