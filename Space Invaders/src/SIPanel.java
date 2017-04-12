import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SIPanel extends JPanel implements ActionListener, KeyListener {

    public int score;
    private int count;
    private BufferedImage base;
    private SI si;
    private boolean left, right, isMovingRight,
            isMovingLeft, isMovingDown;
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
    private double hypotenuse, min, pace;
    private SIthing closest;
    private JLabel scoreText, gameOver;

    public SIPanel(SI si) {
        this.si = si;
        si.setSize(500, 450);
        setBackground(Color.BLACK);
        pace = 40;
        
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));

        scoreText = new JLabel("Score: " + score);
        scoreText.setFont(scoreText.getFont().deriveFont(12f));
        scoreText.setOpaque(false);
        scoreText.setForeground(Color.green);
        add(scoreText);
        
        
//        gameOver = new JLabel("Game Over");
//        gameOver.setFont(gameOver.getFont().deriveFont(40f));
//        gameOver.setOpaque(false);
//        gameOver.setForeground(Color.green);
//        add(gameOver);


        score = 0;
        SIBase = new SIBase(225, 370, 26, 20);
        base = SIBase.getAliveBase();
        repaint();

        bMissile = null;

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

        left = right = false;
        setFocusable(true);
        timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (left) {
                    if (SIBase.getPosX() <= 2) {
                        SIBase.setPosX(2);
                    }
                    SIBase.moveXBy(-5);
                }
                if (right) {
                    if (SIBase.getPosX() >= 465) {
                        SIBase.setPosX(465);
                    }
                    SIBase.moveXBy(5);
                }
                bottomShips();
                if (bMissile != null) {
                    bMissile.moveYBy(-5);
                    didHit();
                }
                if(bMissile != null && bMissile.getPosY() <= -10) {
                    bMissile = null;
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
                        if (bMissile == null) {
                            bMissile = new SImissile(SIBase.getPosX(),  SIBase.getPosY(), 2, 10);
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
        if (isMovingRight && !isMovingDown) {
            if (count % pace == 0) {
                for (int i = 0; i < 10; i++) {
                    if (topList.get(9).getPosX() >= 465) {
                        isMovingLeft = true;
                        isMovingRight = false;
                        break;
                    }
                    moveInvadersX(i, 5);
                }
            }
        }
    }

    public void moveLeft() {
        if (isMovingLeft && !isMovingDown) {
            if (count % pace == 0) {
                for (int i = 10 - 1; i >= 0; i--) {
                    if (topList.get(0).getPosX() <= 2) {
                        isMovingRight = true;
                        isMovingLeft = false;
                        break;
                    }
                    moveInvadersX(i, -5);
                }
            }
        }
    }

    public void moveDown() {
        if ((isMovingLeft && topList.get(0).getPosX() <= 2)
                || isMovingRight && topList.get(9).getPosX() >= 465) {
            if (count % pace == 0) {
                for (int i = 0; i < 10; i++) {
                    moveInvadersY(i, 12);
                }
                isMovingDown = true;
                pace = (int)((pace) * .8);
            }
            isMovingDown = false;
        }
    }

    public void createRound() {
        SItop firstTop = new SItop(70, 80, 30, 24);
        topList.add(firstTop);
        for (int i = 1; i < 10; i++) {
            topList.add(
                    new SItop(topList.get(i - 1).getPosX() + 35, 80, 30, 24));
        }
        SImiddle firstMiddle = new SImiddle(70, 105, 30, 24);
        midList.add(firstMiddle);
        for (int i = 1; i < 10; i++) {
            midList.add(new SImiddle(midList.get(i - 1).getPosX() + 35, 105,
                    30, 24));
        }
        SImiddle firstMiddle2 = new SImiddle(70, 130, 30, 24);
        midList2.add(firstMiddle2);
        for (int i = 1; i < 10; i++) {
            midList2.add(new SImiddle(midList2.get(i - 1).getPosX() + 35, 130,
                    30, 24));
        }
        SIbottom firstBottom = new SIbottom(70, 155, 30, 24);
        bottomList.add(firstBottom);
        for (int i = 1; i < 10; i++) {
            bottomList.add(new SIbottom(bottomList.get(i - 1).getPosX() + 35,
                    155, 30, 24));
        }
        SIbottom firstBottom2 = new SIbottom(70, 180, 30, 24);
        bottomList2.add(firstBottom2);
        for (int i = 1; i < 10; i++) {
            bottomList2.add(new SIbottom(bottomList2.get(i - 1).getPosX() + 35,
                    180, 30, 24));
        }

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
        SIBase = new SIBase(225, 370, 26, 20);
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

    public void didHit() {
        for (int i = 0; i < bottomInvaders.size(); i++) {
//            if (((SIinvader)(things.get(i))).getWasHit() == false && ((SIinvader)(things.get(i))).wasHit(bMissile)) {
//                bMissile = null;
//                score += ((SIinvader)(things.get(i))).updateScore();
//                scoreText.setText("Score: " + score);
//                break;
//            }
            if (bottomInvaders.get(i).getWasHit() == false && bottomInvaders.get(i).wasHit(bMissile)) {
                bMissile = null;
                score += bottomInvaders.get(i).updateScore();
                scoreText.setText("Score: " + score);
                break;
            }
        }
    }
    
    public void bottomShips() {
        for(int i = 0; i < 10; i++) {
            if(bottomList.get(i).getPosY() - 25 == bottomList2.get(i).getPosY()) {
                bottomInvaders.add(bottomList.get(i));
            }
            else {
                bottomInvaders.add(bottomList2.get(i));
            }
        }
    }

    public SIthing findClosest(SIthing thing) {
        int x = thing.getPosX();
        int y = thing.getPosY();

        closest = things.get(0);

        min = Math.sqrt(Math.pow(Math.abs(things.get(0).getPosX() - x), 2)
                + Math.pow(Math.abs(things.get(0).getPosY() - y), 2));

        for (int i = 1; i < things.size(); i++) {
            int newX = things.get(i).getPosX();
            int newY = things.get(i).getPosY();
            hypotenuse = Math.sqrt(Math.pow(Math.abs(newX - x), 2)
                    + Math.pow(Math.abs(newY - y), 2));
            if (hypotenuse == Math.min(min, hypotenuse))
                ;
            closest = things.get(i);
            hypotenuse = min;
        }

        return closest;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        SIBase.paint(g);
        if (bMissile != null) {
            bMissile.paint(g);
        }
        if (count < 40) {
            for (int i = 0; i < topList.size(); i++) {
                if(!(topList.get(i).getWasHit())) {
                    topList.get(i).paint(g);
                }
                if(!(midList.get(i).getWasHit())) {
                    midList.get(i).paint(g);
                }
                if(!(midList2.get(i).getWasHit())) {
                    midList2.get(i).paint(g);
                }
                if(!(bottomList.get(i).getWasHit())) {
                    bottomList.get(i).paint(g);
                }
                if(!(bottomList2.get(i).getWasHit())) {
                    bottomList2.get(i).paint(g);
                }
            }
        }
        else if (count < 80) {
            for (int i = 0; i < topList.size(); i++) {
                if(!(topList.get(i).getWasHit())) {
                    topList.get(i).paint2(g);
                }
                if(!(midList.get(i).getWasHit())) {
                    midList.get(i).paint2(g);
                }
                if(!(midList2.get(i).getWasHit())) {
                    midList2.get(i).paint2(g);
                }
                if(!(bottomList.get(i).getWasHit())) {
                    bottomList.get(i).paint2(g);
                }
                if(!(bottomList2.get(i).getWasHit())) {
                    bottomList2.get(i).paint2(g);
                }
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
