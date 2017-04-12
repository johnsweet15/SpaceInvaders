import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SIPanel extends JPanel implements ActionListener, KeyListener {

    public int score;
    private int count, missileCount;
    private BufferedImage base;
    private SI si;
    private boolean left, right, isMovingRight, isMovingLeft, isMovingDown;
    private SIBase SIBase;
    private SImissile bMissile, iMissile, iMissile2, iMissile3;
    public Timer timer;
    private AudioClip laser;
    private ArrayList<SItop> topList;
    private ArrayList<SImiddle> midList, midList2;
    private ArrayList<SIbottom> bottomList, bottomList2;
    private ArrayList<SIinvader> bottomInvaders, things;
    private double pace;
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
//        add(scoreText);

        gameOver = new JLabel("");
        gameOver.setFont(gameOver.getFont().deriveFont(40f));
        gameOver.setOpaque(false);
        gameOver.setForeground(Color.green);
        add(gameOver);

        score = 0;
        missileCount = 0;
        SIBase = new SIBase(225, 370, 26, 20);
        base = SIBase.getAliveBase();
        repaint();

        bMissile = null;

        isMovingRight = true;
        isMovingLeft = false;
        isMovingDown = false;

        createRound();

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
                fireMissiles();
                if (getLowestRow() == 5) {
                    if (bottomList2.get(0).getPosY() >= 325) {
                        timer.stop();
                    }
                }
                else if (getLowestRow() == 4) {
                    if (bottomList.get(0).getPosY() >= 325) {
                        timer.stop();
                    }
                }
                else if (getLowestRow() == 3) {
                    if (midList2.get(0).getPosY() >= 325) {
                        timer.stop();
                    }
                }
                else if (getLowestRow() == 2) {
                    if (midList.get(0).getPosY() >= 325) {
                        timer.stop();
                    }
                }
                else if (getLowestRow() == 1) {
                    if (topList.get(0).getPosY() >= 325) {
                        timer.stop();
                    }
                }
                // if(count % 40 == 0) {
                // System.out.println(getLowestRow());
                // }
                if (bMissile != null) {
                    bMissile.moveYBy(-5);
                    didHit();
                }
                if (bMissile != null && bMissile.getPosY() <= -10) {
                    bMissile = null;
                }
                if (iMissile != null) {
                    if (count % 2 == 0) {
                        iMissile.moveYBy(5);
                    }
                    if (iMissile.getPosY() > 400) {
                        iMissile = null;
                        missileCount--;
                    }
                }
                if (iMissile2 != null) {
                    if (count % 2 == 0) {
                        iMissile2.moveYBy(5);
                    }
                    if (iMissile2.getPosY() > 400 && count % 20 == 0) {
                        iMissile2 = null;
                        missileCount--;
                    }
                }
                if (iMissile3 != null) {
                    if (count % 2 == 0) {
                        iMissile3.moveYBy(5);
                    }
                    if (iMissile3.getPosY() > 400 && count % 30 == 0) {
                        iMissile3 = null;
                        missileCount--;
                    }
                }

                moveRight();
                moveLeft();
                moveDown();
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
                            bMissile = new SImissile(SIBase.getPosX(),
                                    SIBase.getPosY(), 2, 10);
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
                    if (bottomInvaders.get(9).getPosX() >= 465) {
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
                    if (bottomInvaders.get(0).getPosX() <= 2) {
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
                if (pace >= 4) {
                    pace = (int) ((pace) * .8);
                }
                isMovingDown = true;
                // System.out.println(pace);
            }
            isMovingDown = false;
        }
    }

    public void createRound() {
        topList = new ArrayList<>();
        midList = new ArrayList<>();
        midList2 = new ArrayList<>();
        bottomList = new ArrayList<>();
        bottomList2 = new ArrayList<>();
        bottomInvaders = new ArrayList<>();
        things = new ArrayList<>();

        things.addAll(topList);
        things.addAll(midList);
        things.addAll(midList2);
        things.addAll(bottomList);
        things.addAll(bottomList2);

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
    }

    public void resetRound() {
        score = 0;
        count = 0;
        pace = 40;
        scoreText.setText("Score: " + score);
        isMovingRight = true;
        isMovingLeft = false;
        iMissile = null;
        iMissile2 = null;
        iMissile3 = null;
        SIBase = new SIBase(225, 370, 26, 20);
        createRound();
        timer.start();
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
            if (bottomInvaders.get(i).getWasHit() == false
                    && bottomInvaders.get(i).wasHit(bMissile)) {
                bMissile = null;
                score += bottomInvaders.get(i).updateScore();
                scoreText.setText("Score: " + score);
                bottomInvaders.set(i, aboveInvader(bottomInvaders.get(i), i));
                break;
            }
        }
    }

    public SIinvader aboveInvader(SIinvader invader, int i) {
        if (topList.contains(invader)) {
            return topList.get(i);
        }
        if (midList.contains(invader)) {
            return topList.get(i);
        }
        else if (midList2.contains(invader)) {
            return midList.get(i);
        }
        else if (bottomList.contains(invader)) {
            return midList2.get(i);
        }
        else if (bottomList2.contains(invader)) {
            return bottomList.get(i);
        }
        return null;
    }

    public void bottomShips() {
        for (int i = 0; i < 10; i++) {
            if (topList.get(i).getPosY() - 25 == midList.get(i).getPosY()) {
                bottomInvaders.add(topList.get(i));
            }
            else if (midList.get(i).getPosY() - 25 == midList2.get(i)
                    .getPosY()) {
                bottomInvaders.add(midList.get(i));
            }
            else if (midList2.get(i).getPosY() - 25 == bottomList.get(i)
                    .getPosY()) {
                bottomInvaders.add(midList2.get(i));
            }
            else if (bottomList.get(i).getPosY() - 25 == bottomList2.get(i)
                    .getPosY()) {
                bottomInvaders.add(bottomList.get(i));
            }
            else {
                bottomInvaders.add(bottomList2.get(i));
            }
        }
    }

    public int getLowestRow() {
        for (int i = 0; i < 10; i++) {
            if (!(bottomList2.get(i).getWasHit())) {
                return 5;
            }
        }
        for (int i = 0; i < 10; i++) {
            if (!(bottomList.get(i).getWasHit())) {
                return 4;
            }
        }
        for (int i = 0; i < 10; i++) {
            if (!(midList2.get(i).getWasHit())) {
                return 3;
            }
        }
        for (int i = 0; i < 10; i++) {
            if (!(midList.get(i).getWasHit())) {
                return 2;
            }
        }
        for (int i = 0; i < 10; i++) {
            if (!(topList.get(i).getWasHit())) {
                return 1;
            }
        }
        return 0;
    }

    public void getFarthestRight() {

    }

    public void getFarthestLeft() {

    }

    public void fireMissiles() {
        Random rand = new Random();
        int a = rand.nextInt(10);
        int b = rand.nextInt(10);
        int c = rand.nextInt(10);
        int chance = rand.nextInt(5);
        int chance2 = rand.nextInt(5);
        int chance3 = rand.nextInt(5);
        for (int i = 0; i < bottomInvaders.size(); i++) {
            if (missileCount <= 3) {
                if (iMissile == null && chance != 2
                        && !(bottomInvaders.get(a).getWasHit())) {
                    iMissile = new SImissile(bottomInvaders.get(a).getPosX(),
                            bottomInvaders.get(a).getPosY(), 2, 10);
                    missileCount++;
                }
                if (iMissile2 == null && chance2 != 2
                        && !(bottomInvaders.get(b).getWasHit())) {
                    iMissile2 = new SImissile(bottomInvaders.get(b).getPosX(),
                            bottomInvaders.get(b).getPosY(), 2, 10);
                    missileCount++;
                }
                if (iMissile3 == null && chance3 != 2
                        && !(bottomInvaders.get(c).getWasHit())) {
                    iMissile3 = new SImissile(bottomInvaders.get(c).getPosX(),
                            bottomInvaders.get(c).getPosY(), 2, 10);
                    missileCount++;
                }
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        SIBase.paint(g);
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            
            Graphics2D g3 = (Graphics2D) g;
            g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setFont(new Font("Aerial", Font.PLAIN, 12));
            g2.setColor(Color.GREEN);
            g2.drawString("Score: " + score, 420, 20);
            
            
            
            if (pace == 0) {
                g3.setFont(new Font("Aerial", Font.PLAIN, 32));
                g3.setColor(Color.GREEN);
                g3.drawString("Game Over", 160, 200);
                //timer.stop();
            }
        }
        if (bMissile != null) {
            bMissile.paint(g);
        }
        if (iMissile != null) {
            iMissile.paint(g);
        }
        if (iMissile2 != null) {
            iMissile2.paint(g);
        }
        if (iMissile3 != null) {
            iMissile3.paint(g);
        }
        if (count < 40) {
            for (int i = 0; i < topList.size(); i++) {
                if (!(topList.get(i).getWasHit())) {
                    topList.get(i).paint(g);
                }
                if (!(midList.get(i).getWasHit())) {
                    midList.get(i).paint(g);
                }
                if (!(midList2.get(i).getWasHit())) {
                    midList2.get(i).paint(g);
                }
                if (!(bottomList.get(i).getWasHit())) {
                    bottomList.get(i).paint(g);
                }
                if (!(bottomList2.get(i).getWasHit())) {
                    bottomList2.get(i).paint(g);
                }
                // if(bottomList2.get(i).getWasHit()) {
                // ((SIship)bottomList2.get(i)).paint(g);
                // }
            }
        }
        else if (count < 80) {
            for (int i = 0; i < topList.size(); i++) {
                if (!(topList.get(i).getWasHit())) {
                    topList.get(i).paint2(g);
                }
                if (!(midList.get(i).getWasHit())) {
                    midList.get(i).paint2(g);
                }
                if (!(midList2.get(i).getWasHit())) {
                    midList2.get(i).paint2(g);
                }
                if (!(bottomList.get(i).getWasHit())) {
                    bottomList.get(i).paint2(g);
                }
                if (!(bottomList2.get(i).getWasHit())) {
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
