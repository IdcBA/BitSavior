package com.bitsavior.game;

public class MoveMessage {
    // private members
    private Movement direction;
    private int inversion;

    // public methods
    public MoveMessage(Movement direction) {
        this.direction = direction;
        this.inversion = 1;
    }

    public Movement getDirection() {
        return direction;
    }

    public int getInversion() {
        return inversion;
    }

    public void setInversion(int inversion) {
        this.inversion = inversion;
    }
}
