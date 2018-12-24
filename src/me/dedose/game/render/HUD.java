package me.dedose.game.render;

import me.dedose.game.handlers.Handler;
import me.dedose.game.handlers.ID;
import me.dedose.game.main.Main;
import me.dedose.game.objects.Pipe;

import java.awt.*;

public class HUD {

    public void render(Graphics g){
        if(Main.currentStatus){
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.BOLD, 35));
            g.drawString("You Lost!", Main.WIDTH/2, Main.HEIGHT/2);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 35));
        g.drawString("Score: " + Main.score, 50, 50);

        /*g.setFont(new Font("Arial", Font.BOLD, 120));
        g.setColor(new Color(0, 0, 0, 60));
        g.drawString("DEDOSE INC. COPYRIGHT", Main.WIDTH/2 - 700, Main.HEIGHT/2);*/
    }

    private static long counter = 1;
    public static void updatePipes(Handler handler){
        if(counter % 200 == 0){
            Pipe pipe = new Pipe(Main.WIDTH + 50, 100, ID.Pipe, handler);
            pipe.setY(Main.HEIGHT - pipe.height);
            handler.addObject(pipe);

            long gapSize = ((counter/50) + 20);
            long p2Height = (Main.HEIGHT - (pipe.height + (250 - gapSize)));
            Pipe pipe2 = new Pipe(Main.WIDTH + 50, -pipe.height, ID.Pipe, (int)p2Height, handler);
            pipe2.setY(pipe2.height - Main.HEIGHT);
            handler.addObject(pipe2);
        }
        counter++;
    }
}
