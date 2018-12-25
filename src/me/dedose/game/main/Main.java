package me.dedose.game.main;

import me.dedose.game.ai.Network;
import me.dedose.game.controls.KeyInput;
import me.dedose.game.controls.TickHandler;
import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.Handler;
import me.dedose.game.handlers.ID;
import me.dedose.game.objects.Floor;
import me.dedose.game.objects.Pipe;
import me.dedose.game.objects.Player;
import me.dedose.game.render.HUD;
import me.dedose.game.render.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Main extends Canvas implements Runnable {

    public static final int WIDTH = 1920, HEIGHT = 1080;
    public static boolean currentStatus = false;
    public static int score = 0;
    private boolean running = false;

    private Thread thread;
    private Handler handler;
    private TickHandler tickHandler;

    private HUD hud;
    private static List<Color> rainbowColors;

    private Network network;

    public Main(){
        this.handler = new Handler();
        this.tickHandler = new TickHandler();

        initObjects();

        this.addKeyListener(new KeyInput(handler, tickHandler));
        new Window(WIDTH, HEIGHT, "Flappy Bird", this);

        this.hud = new HUD();

        // --- RAINBOW COLOR LIST INIT
        rainbowColors = new ArrayList<>();
        for (int r=0; r<100; r++) rainbowColors.add(new Color(r*255/100, 255, 0));
        for (int g=100; g>0; g--) rainbowColors.add(new Color(255, g*255/100, 0));
        for (int b=0; b<100; b++) rainbowColors.add(new Color(255, 0, b*255/100));
        for (int r=100; r>0; r--) rainbowColors.add(new Color(r*255/100, 0, 255));
        for (int g=0; g<100; g++) rainbowColors.add(new Color(0, g*255/100, 255));
        for (int b=100; b>0; b--) rainbowColors.add(new Color(0, 255, b*255/100));
        rainbowColors.add(new Color(0, 255, 0));

        //network = new Network(4, 1, 3, 4);
    }

    public void initObjects(){
        Pipe pipe = new Pipe(WIDTH + 50, 100, ID.Pipe, handler);
        pipe.setY(HEIGHT - pipe.height);
        handler.addObject(pipe);
        int p2Height = HEIGHT - (pipe.height + 250);
        Pipe pipe2 = new Pipe(WIDTH + 50, 100, ID.Pipe, p2Height, handler);
        pipe2.setY(pipe2.height - HEIGHT);
        handler.addObject(pipe2);

        handler.addObject(new Player(300, 100, ID.Player, handler, tickHandler));
        handler.addObject(new Floor(0, HEIGHT - 20, ID.Floor));
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            if(running) render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private int currentColor = 0;
    private int counter = 0;
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // --- RAINBOW BACKGROUND SETTER
        if(counter == 50) {
            if(currentColor == 600) currentColor = 0;
            else currentColor++;
            counter = 0;
        }
        g.setColor(rainbowColors.get(currentColor));
        // ---

        //g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        counter++;

        handler.render(g);
        hud.render(g);

        g.dispose();
        bs.show();
    }

    private void tick(){
        handler.tick();
        for (GameObject removePush : handler.removePush) {
            handler.object.remove(removePush);
        }
        handler.removePush.clear();
        tickHandler.tick();
        HUD.updatePipes(handler);

    }

    public static void main(String[] args){
        new Main();
    }
}
