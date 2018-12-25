package me.dedose.game.objects;

import me.dedose.game.controls.TickHandler;
import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.Handler;
import me.dedose.game.handlers.ID;
import me.dedose.game.main.Main;
import me.dedose.game.mechanics.Tick;
import me.dedose.game.render.HUD;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends GameObject {

    private boolean checkpoint = true;

    private Handler handler;
    private TickHandler tickHandler;
    public static boolean falling = false;

    private static Image fallingImg;
    private static Image stillImg;
    public Player(int x, int y, ID id, Handler handler, TickHandler tickHandler) {
        super(x, y, id);
        this.handler = handler;
        this.tickHandler = tickHandler;

        try {
            //use "sprites/chungus_f.png" and "sprites/chungus.png" for a nice little meme ;)
            fallingImg = ImageIO.read(getClass().getResource("sprites/bird_tilted.png"));
            stillImg = ImageIO.read(getClass().getResource("sprites/bird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        if(collision() && !Main.currentStatus) {
            Main.currentStatus = true;
            tickHandler.clear();
            tickHandler.addTick(new Tick() {
                int gravity = 6;
                int weight = 7;
                @Override
                public void tick() {
                    y += gravity;
                    gravity += weight;
                    Player.falling = true;
                    if(y >= Main.HEIGHT - 70) {
                        y = Main.HEIGHT - 70;
                        handler.object.clear();
                        tickHandler.clear();
                        Main.currentStatus = false;
                        HUD.counter = 1;
                        Main.score = 0;
                        initObjects();
                    }
                }
            });
        }

        for (GameObject gameObject : handler.object) {
            if((gameObject.getX() + 50 == x) && gameObject.getId() == ID.Pipe){
                if(checkpoint) {
                    Main.score++;
                    checkpoint = false;
                }else checkpoint = true;
            }
        }
    }

    public boolean collision(){
        try{
            for (GameObject gameObject : handler.object) {
                if(getBounds().intersects(gameObject.getBounds()) && gameObject.getId() != ID.Player) return true;
            }
        }catch (NullPointerException e){
            //ignore
        }
        return false;
    }

    @Override
    public void render(Graphics g) {
        try {
            Image image;
            if(falling) image = fallingImg;
            else image = stillImg;
            g.drawImage(image, x, y, null);
        }catch (Exception e){

        }
        //g.setColor(Color.yellow);
        //g.fillRect(x, y, 16, 16);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

    public void initObjects(){
        Pipe pipe = new Pipe(Main.WIDTH + 50, 100, ID.Pipe, handler);
        pipe.setY(Main.HEIGHT - pipe.height);
        handler.addObject(pipe);

        int p2Height = Main.HEIGHT - (pipe.height + 250);
        Pipe pipe2 = new Pipe(Main.WIDTH + 50, 100, ID.Pipe, p2Height, handler);
        pipe2.setY(pipe2.height - Main.HEIGHT);
        handler.addObject(pipe2);

        handler.addObject(new Player(300, 100, ID.Player, handler, tickHandler));
        handler.addObject(new Floor(0, Main.HEIGHT - 20, ID.Floor));
    }
}
