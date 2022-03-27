package model;

import controller.Main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
// vecmath package is not from standard Java SE
// You must add this library to your NetBeans project
import javax.vecmath.Vector2f;

public class Missile extends GameFigure {

    // missile size
    private static final int SIZE = 5;
    private static final int MAX_EXPLOSION_SIZE = 50;

    // public properties for quick access
    public Color color;
    public Point2D.Float target;
    Rectangle2D.Double collisionBox = new Rectangle2D.Double();
            

    private static final int UNIT_TRAVEL_DISTANCE = 4; // per frame move

    private int size = SIZE;

    /**
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     * @param color color of the missile
     */
    public Missile(float sx, float sy, float tx, float ty, Color color) {
        super(sx, sy);
        super.state = STATE_ALIVE;
        target = new Point2D.Float(tx, ty);
        this.color = color;
    }
    
    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.drawOval((int) (super.x - size / 2),
                (int) (super.y - size / 2),
                size, size);
    }

    @Override
    public void update() {
        updateState();
        if (state == STATE_ALIVE) {
            updateLocation();
        } else if (state == STATE_DYING) {
            updateSize();
        }
    }

    // Vector arithmetic
    // A: current position
    // B: target position
    // d: distance to travel along the line from A to B
    //     A_moved = A + |B - A| * d where |  | represents 'norm'
    public void updateLocation() {
        Vector2f currentLoc = new Vector2f((float) super.x, (float) super.y);
        Vector2f update = new Vector2f(target.x, target.y);
        update.sub(currentLoc); // B - A
        update.normalize(); // |B - A|
        update.scale(UNIT_TRAVEL_DISTANCE); // |B - A| x dist
        currentLoc.add(update); // A + |B - A| x d
        super.x = currentLoc.x;
        super.y = currentLoc.y;
    }

    public void updateSize() {
        size += 2;
    }

    public void updateState() {
        if (state == STATE_ALIVE) {
            double distance = target.distance(super.x, super.y);
            boolean targetReached = distance <= 2.0;
            if (targetReached) {
                state = STATE_DYING;
            }
        } else if (state == STATE_DYING) {
            if (size >= MAX_EXPLOSION_SIZE) {
                state = STATE_DONE;
            }
        }
    }
    @Override
    public Rectangle2D getCollisionBox() {
        collisionBox.setRect(super.x, super.y, size * 0.9, size * 0.9);
        return collisionBox;
    }    
}
