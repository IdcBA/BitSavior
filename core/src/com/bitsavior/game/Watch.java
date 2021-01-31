package com.bitsavior.game;

public class Watch
{
    public long startTime = 0l;
    public long elapsedTime = 0l;
    public long timeLimit = 0l;

    boolean isActive = false;

    /**
     *
     * @param timeLimit : time limit in seconds
     */
    public Watch(int timeLimit)
    {
        this.timeLimit = timeLimit * 1000;

    }
    public void startWatch()
    {
        startTime = System.currentTimeMillis();
        isActive = true;
    }

    public boolean update()
    {
            elapsedTime = System.currentTimeMillis() - startTime;

            if(elapsedTime >= timeLimit) {
                isActive = false;
            }
            return isActive;
    }

    public int getRemainingSeconds() { return (int)((timeLimit - elapsedTime) / 1000); }

    public int getTimeLimit(){ return (int)(timeLimit / 1000); }

    public boolean isActive(){ return isActive; }

}
