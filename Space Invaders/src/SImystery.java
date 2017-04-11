import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SImystery extends SIinvader {
    
    private BufferedImage mystery;
    
    public SImystery(int x, int y, int width, int height) {
        super(x, y, width, height);
        
        try {
            mystery = ImageIO.read(getClass().getResourceAsStream("/SImystery.gif"));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        
    }
}
