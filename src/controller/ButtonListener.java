package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.MainWindow;

public class ButtonListener implements ActionListener {
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == MainWindow.addBombButton) {
            Main.gameData.addBomb(10);
            Main.gameData.BTotal+=10;
            Main.gameData.BAlive+=10;
            MainWindow.BStats.setText("UFO’s: total ="+Main.gameData.BTotal+", destroyed = "+Main.gameData.BDestroyed+", alive = "+Main.gameData.BAlive);            
        }
        if (ae.getSource() == MainWindow.addUfoButton) {
            Main.gameData.addUfo(1);  
            Main.gameData.UTotal+=1;
            Main.gameData.UAlive+=1;
            MainWindow.UStats.setText("UFO’s: total ="+Main.gameData.UTotal+", destroyed = "+Main.gameData.UDestroyed+", alive = "+Main.gameData.UAlive);            
        } else if (ae.getSource() == MainWindow.quitButton) {
            if (Main.animator.running) {
                Main.animator.running = false;
            } else {
                System.exit(0);
            }
        }
    }

}
