package me.dedose.game.objects;

import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.Handler;
import me.dedose.game.handlers.ID;
import me.dedose.game.main.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Random;

public class Pipe extends GameObject {

    private Handler handler;
    public int height;
    Image img;
    public Pipe(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        try {
            this.height = new Random().nextInt(700 + 1 - 100) + 200;
            this.handler = handler;
            this.img = ImageIO.read(getClass().getResource("pipe_up.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Pipe(int x, int y, ID id, int height, Handler handler) {
        super(x, y, id);
        try {
            this.height = height;
            this.handler = handler;
            this.img = ImageIO.read(getClass().getResource("pipe_down.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        if(!Main.currentStatus) x -= 5;
        if(x == -50) handler.removeObject(this);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(img, x, y, null);
       // g.setColor(Color.black);
        //g.fillRect(x, y, 50, Main.HEIGHT);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, Main.HEIGHT);
    }
}
