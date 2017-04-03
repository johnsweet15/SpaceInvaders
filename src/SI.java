
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class SI extends JFrame{
    
    private JMenuBar bar;
    private JMenu gameMenu;
    private JMenuItem newGame;
    private JMenuItem quit;
    private JMenuItem about;
    private JMenuItem pause;
    private JMenuItem resume;
    private JMenu help;
    
    public SI() {
        super("Space Invaders");
        
        SIPanel panel = new SIPanel(this);
        add(panel);
        
        
        bar = new JMenuBar();
        setJMenuBar(bar);

        gameMenu = new JMenu("Game");
        help = new JMenu("Help");
        
        bar.add(gameMenu);
        bar.add(Box.createHorizontalGlue());
        bar.add(help);
        
        newGameButton();
        pauseButton();
        resumeButton();
        quitButton();
        aboutButton();
        
        
        
        setSize(500, 450);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);      
        checkWindow();
        
        
    }
    
    private void newGameButton() {
        newGame = new JMenuItem("New Game");
        gameMenu.add(newGame);
        gameMenu.addSeparator();
        newGame.addActionListener(new ActionListener() {
            /**
             * Method called when about action occurs
             */
            public void actionPerformed(ActionEvent event) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Start a new game?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    
                }
            }
        });
    }
    
    private void pauseButton() {
        pause = new JMenuItem("Pause");
        gameMenu.add(pause);
        pause.setEnabled(false);
        pause.addActionListener(new ActionListener() {
            /**
             * Method called when about action occurs
             */
            public void actionPerformed(ActionEvent event) {
                
            }
        });
    }
    
    private void resumeButton() {
        resume = new JMenuItem("Resume");
        gameMenu.add(resume);
        resume.setEnabled(false);
        resume.addActionListener(new ActionListener() {
            /**
             * Method called when about action occurs
             */
            public void actionPerformed(ActionEvent event) {
                
            }
        });
    }
    
    private void quitButton() {
        quit = new JMenuItem("Quit");
        gameMenu.addSeparator();
        gameMenu.add(quit);
        quit.addActionListener(new ActionListener() {
            /**
             * Method called when about exit occurs
             */
            public void actionPerformed(ActionEvent event) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Dare to Quit?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }
    
    private void aboutButton() {
        about = new JMenuItem("About...");
        help.add(about);
        about.addActionListener(new ActionListener() {
            /**
             * Method called when about action occurs
             */
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null,
                        "SpaceInvaders\nby John Sweet", "About...",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    private void checkWindow() {
        addWindowListener(new WindowListener() {
            /**
             * Method called when window close action occurs
             */
            public void windowClosing(WindowEvent arg0) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Dare to Quit?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }

            /**
             * unused method that had to be implemented
             */
            public void windowActivated(WindowEvent arg0) {
            }

            /**
             * unused method that had to be implemented
             */
            public void windowClosed(WindowEvent arg0) {
            }

            /**
             * unused method that had to be implemented
             */
            public void windowDeactivated(WindowEvent arg0) {
            }

            /**
             * unused method that had to be implemented
             */
            public void windowDeiconified(WindowEvent arg0) {
            }

            /**
             * unused method that had to be implemented
             */
            public void windowIconified(WindowEvent arg0) {
            }

            /**
             * unused method that had to be implemented
             */
            public void windowOpened(WindowEvent arg0) {
            }
        });
    }
    
    public static void main(String[] args) {
        JFrame f = new SI();
        f.setVisible(true);
    }

}
