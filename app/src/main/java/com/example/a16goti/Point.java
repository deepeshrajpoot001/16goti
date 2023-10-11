package com.example.a16goti;

public class Point {
    int curPointId;
    int goti; //0- nothing, 1- green(player1), 2- Red(Player2)
    int[] nextPointId;

    public int getGoti() {
        return goti;
    }

    public void setGoti(int goti) {
        this.goti = goti;
    }

    public int getCurPointId() {
        return curPointId;
    }

    public int[] getNextPointId() {
        return nextPointId;
    }

    public void setCurPointId(int curPointId) {
        this.curPointId = curPointId;
    }

    public void setNextPointId(int east,int southEast,int south,int southWest,int west,int northWest,int north, int northEast) {
        this.nextPointId= new int[]{east,southEast,south,southWest,west,northWest,north,northEast};
    }
}
