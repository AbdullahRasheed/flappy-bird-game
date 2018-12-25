package me.dedose.game.controls;

import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.Handler;
import me.dedose.game.handlers.ID;
import me.dedose.game.main.Main;
import me.dedose.game.mechanics.Tick;
import me.dedose.game.objects.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private TickHandler tickHandler;

    public KeyInput(Handler handler, TickHandler tickHandler) {
        this.handler = handler;
        this.tickHandler = tickHandler;
    }

    public void keyPressed(KeyEvent e) {
        if(Main.currentStatus) return;

        int key = e.getKeyCode();
        if(key == KeyEvent.VK_SPACE){
            for (GameObject gameObject : handler.object) {
                if(gameObject.getId() == ID.Player){
                    Player.falling = false;
                    tickHandler.clear();
                    tickHandler.addTick(new Tick() {
                        int jumpStrength = 16;
                        int weight = 1;
                        @Override
                        public void tick() {
                            gameObject.setY(gameObject.getY() - jumpStrength);
                            jumpStrength -= weight;
                            if(jumpStrength < 0) Player.falling = true;
                        }
                    });
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

    }
}
