import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SIPanel extends JPanel{
    
    private int score;
    private BufferedImage base;
    private SI si;
    
    public SIPanel(SI si) {
        this.si = si;
        si.setSize(500, 450);
        score = 0;
        try {
            base = ImageIO.read(getClass().getResourceAsStream("/SIbase.gif"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(base, 100, 100, 100, 100, null);
    }
}
