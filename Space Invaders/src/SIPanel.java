import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SIPanel extends JPanel implements ActionListener, KeyListener{

    public int score;
    private int count;
    private BufferedImage base;
    private SI si;
    private boolean left, right, fired, missileGone, missileHit, isMovingRight, isMovingLeft, isMovingDown;
    private SIBase SIBase;
    private SImissile bMissile, iMissile;
    private SIship ship;
    public Timer timer;
    private AudioClip laser;
    private ArrayList<SItop> topList;
    private ArrayList<SImiddle> midList, midList2;
    private ArrayList<SIbottom> bottomList, bottomList2;
    private ArrayList<SIthing> things;
    private ArrayList<SIinvader> bottomInvaders;
    private double hypotenuse, min;
    private SIthing closest;
    
    public SIPanel(SI si) {
        this.si = si;
        si.setSize(500, 450);
        setBackground(Color.BLACK);
        
//        JTextArea begin = new JTextArea("Click to begin");
//        begin.setFont(begin.getFont().deriveFont(32f));
//        begin.setOpaque(false);
//        begin.setForeground(Color.green);
//        begin.setCaretPosition(0);
//        add(begin);

        score = 0;
        SIBase = new SIBase(225, 370, 26, 20);
        base = SIBase.getAliveBase();
        repaint();
        
        bMissile = new SImissile(0, -10, 2, 10);
        
        topList = new ArrayList<>();
        midList = new ArrayList<>();
        midList2 = new ArrayList<>();
        bottomList = new ArrayList<>();
        bottomList2 = new ArrayList<>();
        things = new ArrayList<>();
        bottomInvaders = new ArrayList<>();
        
        isMovingRight = true;
        isMovingLeft = false;
        isMovingDown = false;
        
        createRound();
        
        hypotenuse = 0;
        min = 0;
        
        left = right = fired = false;
        missileGone = true;
        missileHit = false;
        setFocusable(true);
        timer = new Timer(10, new ActionListener() {
           public void actionPerformed(ActionEvent event) {
               if(left) {
                   if(SIBase.getPosX() <= 2) {
                       SIBase.setPosX(2);
                   }
                   SIBase.moveXBy(-5);
               }
               if(right) {
                   if(SIBase.getPosX() >= 465) {
                       SIBase.setPosX(465);
                   }
                   SIBase.moveXBy(5);
               }
               if(fired) {
                   if(bMissile.getPosY() <= -10) {
                       fired = false;
                   }
                   else {
                       bMissile.moveYBy(-5);
                       if(didHit()) {
                           fired = false;
                           bMissile.setPosY(-10);
                       }
                       else{ 
                           fired = true;
                       }
                   }
               }
               
               moveDown();
               moveRight();
               moveLeft();
               repaint();
               count++;
           }
        });
        timer.start();
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        left = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        right = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        if(!fired) {
                            bMissile.setPosX(SIBase.getPosX());
                            bMissile.setPosY(SIBase.getPosY());
                            fired = true;
                            playSound("/SIbaseShoot.wav");
                        }
                        
                }
                repaint();
            }

            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        left = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        right = false;
                }
                repaint();
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }
        });
    }
    
    public void moveRight() {
        if(isMovingRight && !isMovingDown) {
            if(count % 40 == 0) {
                for(int i = 0; i < topList.size(); i++) {
                    if(topList.get(9).getPosX() >= 465) {
                        isMovingLeft = true;
                        isMovingRight = false;
                        //moveLeft();
                        //moveDown();
                        break;
                    }
                    moveInvadersX(i, 5);
                }
            }
        }
    }
    
    public void moveLeft() {
        if(isMovingLeft && !isMovingDown) {
            if(count % 40 == 0) {
                for(int i = topList.size() - 1; i >= 0; i--) {
                    if(topList.get(0).getPosX() <= 2) {
                        isMovingRight = true;
                        isMovingLeft = false;
                        //moveRight();
                        //moveDown();
                        break;
                    }
                    moveInvadersX(i, -5);
                }
            }
        }
    }
    
    public void moveDown() {
        if((isMovingLeft && topList.get(0).getPosX() <= 2) || isMovingRight && topList.get(9).getPosX() >= 465) {
            if(count % 40 == 0) {
                for(int i = 0; i < topList.size(); i++) {
                    moveInvadersY(i, 5);
                }
                isMovingDown = true;
            }
            isMovingDown = false;
        }
    }
    
    public void createRound() {
        SItop firstTop = new SItop(70, 80, 30, 24);
        topList.add(firstTop);
        for(int i = 1; i < 10; i++) {
            topList.add(new SItop(topList.get(i - 1).getPosX() + 35, 80, 30, 24));
        }
        SImiddle firstMiddle = new SImiddle(70, 105, 30, 24);
        midList.add(firstMiddle);
        for(int i = 1; i < 10; i++) {
            midList.add(new SImiddle(midList.get(i - 1).getPosX() + 35, 105, 30, 24));
        }
        SImiddle firstMiddle2 = new SImiddle(70, 130, 30, 24);
        midList2.add(firstMiddle2);
        for(int i = 1; i < 10; i++) {
            midList2.add(new SImiddle(midList2.get(i - 1).getPosX() + 35, 130, 30, 24));
        }
        SIbottom firstBottom = new SIbottom(70, 155, 30, 24);
        bottomList.add(firstBottom);
        for(int i = 1; i < 10; i++) {
           bottomList.add(new SIbottom(bottomList.get(i - 1).getPosX() + 35, 155, 30, 24));
        }
        SIbottom firstBottom2 = new SIbottom(70, 180, 30, 24);
        bottomList2.add(firstBottom2);
        for(int i = 1; i < 10; i++) {
           bottomList2.add(new SIbottom(bottomList2.get(i - 1).getPosX() + 35, 180, 30, 24));
        }
        
        bottomInvaders.addAll(bottomList2);
        
        things.addAll(topList);
        things.addAll(midList);
        things.addAll(midList2);
        things.addAll(bottomList);
        things.addAll(bottomList2);
    }
    
    public void resetRound() {
        score = 0;
        isMovingRight = true;
        isMovingLeft = false;
        topList = new ArrayList<>();
        midList = new ArrayList<>();
        midList2 = new ArrayList<>();
        bottomList = new ArrayList<>();
        bottomList2 = new ArrayList<>();
        createRound();
    }
    
    public void playSound(String sound) {
        URL urlClick = this.getClass().getResource(sound);
        laser = Applet.newAudioClip(urlClick);
        laser.play();
    }
    
    public void moveInvadersX(int i, int amount) {
        topList.get(i).moveXBy(amount);
        midList.get(i).moveXBy(amount);
        midList2.get(i).moveXBy(amount);
        bottomList.get(i).moveXBy(amount);
        bottomList2.get(i).moveXBy(amount);
    }

    public void moveInvadersY(int i, int amount) {
        topList.get(i).moveYBy(amount);
        midList.get(i).moveYBy(amount);
        midList2.get(i).moveYBy(amount);
        bottomList.get(i).moveYBy(amount);
        bottomList2.get(i).moveYBy(amount);
    }
    
    public boolean didHit() {
        for(int i = 0; i < bottomList2.size(); i++) {
            if(((SIship)bottomList2.get(i)).wasHit(bMissile)) {
                //bottomList2.remove(bottomList2.get(i));
                return true;
            }
        }
        return false;
    }
    
    public SIthing findClosest(SIthing thing) {
        int x = thing.getPosX();
        int y = thing.getPosY();
        
        closest = things.get(0);
        
        min = Math.sqrt(Math.pow(Math.abs(things.get(0).getPosX() - x), 2) + Math.pow(Math.abs(things.get(0).getPosY() - y), 2));
        
        for(int i = 1; i < things.size(); i++) {
            int newX = things.get(i).getPosX();
            int newY = things.get(i).getPosY();
            hypotenuse = Math.sqrt(Math.pow(Math.abs(newX - x), 2) + Math.pow(Math.abs(newY - y), 2));
            if(hypotenuse == Math.min(min, hypotenuse));
                closest = things.get(i);
                hypotenuse = min;
        }
        
        return closest;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        SIBase.paint(g);
        if(bMissile != null) {
            bMissile.paint(g);
        }
        if(count < 40) {
            for(int i = 0; i < bottomInvaders.size(); i++) {
                topList.get(i).paint(g);
                midList.get(i).paint(g);
                midList2.get(i).paint(g);
                bottomList.get(i).paint(g);
                bottomList2.get(i).paint(g);
            }
        }
        else if(count < 80) {
            for(int i = 0; i < bottomInvaders.size(); i++) {
                topList.get(i).paint2(g);
                midList.get(i).paint2(g);
                midList2.get(i).paint2(g);
                bottomList.get(i).paint2(g);
                bottomList2.get(i).paint2(g);
            }
        }
        else {
            count = 0;
        }
        repaint();
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
}
