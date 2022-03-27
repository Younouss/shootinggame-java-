package model;

import controller.Main;
import view.GamePanel;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import view.MainWindow;

public class GameData {

    private final int RADIUS = 6;
    public final List<GameFigure> enemyFigures;
    public final List<GameFigure> friendFigures;
    public static Shooter shooter;
    public int UTotal = 0;    
    public int UDestroyed = 0;
    public int UAlive = 0;
    public int BTotal = 0;
    public int BDestroyed = 0;
    public int BAlive = 0;
    public GameData() {
        enemyFigures = Collections.synchronizedList(
                new ArrayList<GameFigure>());
        friendFigures = Collections.synchronizedList(
                new ArrayList<GameFigure>());

        // GamePanel.width, height are known when rendered. 
        // Thus, at this moment,
        // we cannot use GamePanel.width and height.
        shooter = new Shooter(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 130);

        friendFigures.add(shooter);
        
    }
    public void addUfo(int n) {
        enemyFigures.add(new FlyingSaucer((int) (Math.random() * GamePanel.width), (int) (Math.random() * GamePanel.height)));        
    }
    
    public void addBomb(int n) {
        synchronized (enemyFigures) {
            for (int i = 0; i < n; i++) {
                float red = (float) Math.random();
                float green = (float) Math.random();
                float blue = (float) Math.random();
                // adjust if too dark since the background is black
                if (red < 0.5) {
                    red += 0.2;
                }
                if (green < 0.5) {
                    green += 0.2;
                }
                if (blue < 0.5) {
                    blue += 0.2;
                }
                enemyFigures.add(new Bomb(
                        (int) (Math.random() * GamePanel.width),
                        (int) (Math.random() * GamePanel.height),
                        RADIUS,
                        new Color(red, green, blue)));
            }
        }
    }

    public void update() {
        
        // no enemy is removed in the program
        // since collision detection is not implemented yet.
        // However, if collision detected, simply set
        // f.state = GameFigure.STATE_DONE
        synchronized (enemyFigures) {
            ArrayList<GameFigure> remove = new ArrayList<>();
            GameFigure f;
            for (int i = 0; i < enemyFigures.size(); i++) {
                f = enemyFigures.get(i);
                if (f.state == GameFigure.STATE_DONE) {
                    remove.add(f);
                    if(f.getCollisionBox().getWidth() == 36){
                        UDestroyed += 1;
                        UAlive = UTotal - UDestroyed;
                        MainWindow.UStats.setText("UFO’s: total ="+UTotal+", destroyed = "+UDestroyed+", alive = "+UAlive);
                    }
                    else{
                    BDestroyed += 1;
                    BAlive = BTotal - BDestroyed;
                    MainWindow.BStats.setText("Bombs: total ="+BTotal+", destroyed = "+BDestroyed+", alive = "+BAlive);                    
                    }
                    }
            }
            enemyFigures.removeAll(remove);
            for (GameFigure g : enemyFigures) {
                g.update();
            }
        }
        
        // missiles are removed if explosion is done
        synchronized (friendFigures) {
            ArrayList<GameFigure> remove = new ArrayList<>();
            GameFigure f;
            for (int i = 0; i < friendFigures.size(); i++) {
                f = friendFigures.get(i);
                if (f.state == GameFigure.STATE_DONE) {
                    remove.add(f);
                }
            }
            friendFigures.removeAll(remove);
            for (GameFigure g : friendFigures) {
                g.update();
            }
        }
    }
}
