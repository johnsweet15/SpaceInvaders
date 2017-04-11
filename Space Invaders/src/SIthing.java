import java.awt.Graphics;

public abstract class SIthing {
    
    private int posX;
    private int posY;
    private int width;
    private int height;
    
    public SIthing(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }
    
    public void setPosX(int posX) {
        this.posX = posX;
    }
    
    public int getPosX() {
        return posX;
    }
    
    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    public int getPosY() {
        return posY;
    }
    
    public void moveXBy(int x) {
        posX += x;
    }
    
    public void moveYBy(int y) {
        posY += y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public abstract void paint(Graphics g);

}
