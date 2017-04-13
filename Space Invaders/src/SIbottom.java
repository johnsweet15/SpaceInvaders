import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SIbottom extends SIinvader {
    
    private BufferedImage bottom0;
    private BufferedImage bottom1;
    
    public SIbottom(int x, int y, int width, int height) {
        super(x, y, width, height);
        
        try {
            bottom0 = ImageIO.read(getClass().getResourceAsStream("/SIbottom0.gif"));
            bottom1 = ImageIO.read(getClass().getResourceAsStream("/SIbottom1.gif"));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(bottom0, getPosX(), getPosY(), getWidth(), getHeight(), null);
    }
    public void paint2(Graphics g) {
        g.drawImage(bottom1, getPosX(), getPosY(), getWidth(), getHeight(), null);
    }

    
}
