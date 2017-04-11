import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public abstract class SIship extends SIthing{
    
    private boolean wasHit;
    private AudioClip boom;
    private BufferedImage SIshipHit;
    
    public SIship(int x, int y, int width, int height) {
        super(x, y, width, height);
        wasHit = false;
    }
    
    public boolean wasHit(SImissile missile) {
        Rectangle rect1 = new Rectangle(missile.getPosX(), missile.getPosY(), missile.getWidth(), this.getHeight());
        Rectangle rect2 = new Rectangle(this.getPosX() - (this.getWidth() / 2), this.getPosY() - (this.getHeight() / 2), this.getWidth(), this.getHeight());
        if(rect1.intersects(rect2)) {
            wasHit = true;
            shipHit();
            try {
                SIshipHit = ImageIO.read(getClass().getResourceAsStream("/SIinvaderBlast.gif"));
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return wasHit;
    }
    
    public boolean getWasHit() {
        return wasHit;
    }
    
    public void playSound(String sound) {
        URL urlClick = this.getClass().getResource(sound);
        boom = Applet.newAudioClip(urlClick);
        boom.play();
    }
    
    public void shipHit() {
        playSound("SIshipHit.wav");
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(SIshipHit, getPosX(), getPosY(), getWidth(), getHeight(), null);
    }

}
