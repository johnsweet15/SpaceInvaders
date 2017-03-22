import java.awt.BorderLayout;

import javax.swing.JFrame;

public class SI extends JFrame{
    
    public SI() {
        super("Space Invaders");
        
        setLayout(new BorderLayout());
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

}
