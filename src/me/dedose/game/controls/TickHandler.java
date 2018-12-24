package me.dedose.game.controls;

import me.dedose.game.mechanics.Tick;

import java.util.LinkedList;

public class TickHandler {

    public LinkedList<Tick> ticks = new LinkedList<>();

    public void addTick(Tick tick){
        ticks.add(tick);
    }

    public void removeTick(Tick tick){
        ticks.remove(tick);
    }

    public void clear(){
        ticks.clear();
    }

    public void tick(){
        for (Tick tick : ticks) {
            tick.tick();
        }
    }
}
