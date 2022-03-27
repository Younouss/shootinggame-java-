package controller;

import model.GameFigure;

public class Animator implements Runnable {

    public boolean running = true;
    private final int FRAMES_PER_SECOND = 20;

    @Override
    public void run() {

        while (running) {
            long startTime = System.currentTimeMillis();
            
            processCollisions();

            Main.gameData.update();
            Main.gamePanel.gameRender();
            Main.gamePanel.printScreen();

            long endTime = System.currentTimeMillis();
            int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                    - (int) (endTime - startTime);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime); // ms
                } catch (InterruptedException e) {

                }
            }
        }
        System.exit(0);
    }
    
    private void processCollisions() {
        // detect collisions between friendFigure and enemyFigures
        // if detected, mark it as STATE_DONE, so that
        // they can be removed at update() method
        for(int i = 0; i < Main.gameData.friendFigures.size(); i++){
            for(int j = 0; j < Main.gameData.enemyFigures.size(); j++){
                if (Main.gameData.friendFigures.get(i).getCollisionBox().intersects(Main.gameData.enemyFigures.get(j).getCollisionBox())) {
                    Main.gameData.enemyFigures.get(j).state = GameFigure.STATE_DONE;
                }            
            }
        }
    }  
}
