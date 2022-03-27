package model;

import view.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Bomb extends GameFigure {
    Rectangle2D.Double collisionBox = new Rectangle2D.Double();
    private final int radius;
    private final Color color;
    private int dx = 3;
    private int dy = 3;
    public Bomb(float x, float y, int radius, Color color) {
        super(x, y);
        super.state = STATE_ALIVE;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        // Note: use drawOval() to draw outline only
        g.fillOval((int)(x - radius), (int)(y - radius), 
                radius * 2, radius * 2);
    }

    @Override
    public void update() {
        // ball bounces on the wall
        super.x += dx;
        super.y += dy;

        if (super.x + radius > GamePanel.width) {
            dx = -dx;
            super.x = GamePanel.width - radius;
        } else if (super.x - radius < 0) {
            dx = -dx;
            super.x = radius;
        }

        if (super.y + radius > GamePanel.height) {
            dy = -dy;
            super.y = GamePanel.height - radius;
        } else if (super.y - radius < 0) {
            dy = -dy;
            super.y = radius;
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        collisionBox.setRect(super.x, super.y, radius * 0.9, radius * 0.9);
        return collisionBox;
    }
}
