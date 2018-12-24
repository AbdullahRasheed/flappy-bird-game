package me.dedose.game.objects;

import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.ID;
import me.dedose.game.main.Main;

import java.awt.*;

public class Floor extends GameObject {

    public Floor(int x, int y, ID id) {
        super(x, y, id);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(x, y, Main.WIDTH, 50);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, Main.WIDTH, 50);
    }
}
