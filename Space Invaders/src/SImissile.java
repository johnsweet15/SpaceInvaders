import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SImissile extends SIthing {
    
    public SImissile(int x, int y, int width, int height) {
        super(x, y, 2, 10);
        
    }
    
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(getPosX() + 13, getPosY(), getWidth(), getHeight());
    }
}
