package me.dedose.game.handlers;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    public LinkedList<GameObject> object = new LinkedList<>();
    public LinkedList<GameObject> removePush = new LinkedList<>();

    public void tick(){
        for (GameObject gameObject : object) {
            gameObject.tick();
        }
    }

    public void render(Graphics g){
        for (GameObject gameObject : object) {
            gameObject.render(g);
        }
    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject(GameObject object){
        this.removePush.add(object);
    }
}
