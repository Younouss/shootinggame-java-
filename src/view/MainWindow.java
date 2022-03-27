package view;

import controller.ButtonListener;
import controller.KeyController;
import controller.Main;
import controller.MouseController;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow extends JFrame {
    public static JTextField UStats;
    public static JTextField BStats;   
    public static JButton addBombButton;
    public static JButton addUfoButton;    
    public static JButton quitButton;


    
    public MainWindow() {

        Container c = getContentPane();

        c.add(Main.gamePanel, "Center");

        JPanel southPanel = new JPanel();
        addBombButton = new JButton("Add 10");
        southPanel.add(addBombButton);
        addUfoButton = new JButton("Add UFO");
        southPanel.add(addUfoButton);        
        quitButton = new JButton("Quit");
        southPanel.add(quitButton);
        c.add(southPanel, "South");
        UStats = new JTextField("UFO’s: total = xxx, destroyed = xx, alive = xx");   
        UStats.setEditable(false);       
        BStats = new JTextField("Bombs: total = xxx, destroyed = xx, alive = xx"); 
        BStats.setEditable(false); 
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 1));
        northPanel.add(UStats);
        northPanel.add(BStats);
        c.add(northPanel, "North");
        ButtonListener buttonListener = new ButtonListener();
        addBombButton.addActionListener(buttonListener);
        addUfoButton.addActionListener(buttonListener);        
        quitButton.addActionListener(buttonListener);
        MouseController mouseController = new MouseController();
        Main.gamePanel.addMouseListener(mouseController);
        KeyController keyListener = new KeyController();
        Main.gamePanel.addKeyListener(keyListener);
        Main.gamePanel.setFocusable(true);
        northPanel.setFocusable(false);
        addUfoButton.setFocusable(false); 
        addBombButton.setFocusable(false);       
        quitButton.setFocusable(false);
        UStats.setFocusable(false);         
        BStats.setFocusable(false);  
    }
}
