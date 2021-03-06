import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public abstract class SIship extends SIthing{
    
    private boolean wasHit;
    private AudioClip boom;
    private BufferedImage SIshipHit;
    private int score;
    private static ArrayList<Integer> values;
    
    public SIship(int x, int y, int width, int height) {
        super(x, y, width, height);
        wasHit = false;
        score = 0;
        values = new ArrayList<>();
        values.add(50);
        values.add(100);
        values.add(150);
        values.add(300);
    }
    
    public boolean wasHit(SImissile missile) {
        Rectangle rect1 = new Rectangle(missile.getPosX(), missile.getPosY(), missile.getWidth(), this.getHeight() / 2);
        Rectangle rect2 = new Rectangle(this.getPosX() - (this.getWidth() / 2) + 3, this.getPosY() - (this.getHeight() / 2), this.getWidth(), this.getHeight());
        if(rect1.intersects(rect2)) {
            wasHit = true;
            if(this instanceof SIbottom) {
                score = 10;
            }
            else if(this instanceof SImiddle) {
                score = 20;
            }
            else if(this instanceof SItop) {
                score = 30;
            }
            else if(this instanceof SImystery) {
                Random rand = new Random();
                int a = rand.nextInt(4);
                score = values.get(a);
            }
            updateScore();
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
    
    public int updateScore() {
        return score;
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
    
    public void paint3(Graphics g) {
        g.drawImage(SIshipHit, getPosX(), getPosY(), getWidth(), getHeight(), null);
    }

}
