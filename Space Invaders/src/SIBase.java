import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SIBase extends SIship{
    
    private BufferedImage aliveBase;
    private BufferedImage deadBase;
    
    public SIBase(int x, int y, int height, int width) {
        super(x, y, height, width);
        try {
            aliveBase = ImageIO.read(getClass().getResourceAsStream("/SIbase.gif"));
            deadBase = ImageIO.read(getClass().getResourceAsStream("/SIbaseBlast.gif"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public BufferedImage getAliveBase() {
        return aliveBase;
    }
    
    public BufferedImage getDeadBase() {
        return deadBase;
    }
    
    public void paint(Graphics g) {
        g.drawImage(aliveBase, getPosX(), getPosY(), getWidth(), getHeight(), null);
    }
}
