package com.bitsavior.game;

public class Watch
{
    public long startTime = 0L;
    public long elapsedTime = 0L;
    public long timeLimit;

    boolean isActive = false;

    /**
     *
     * @param timeLimit : time limit in seconds
     */
    public Watch(int timeLimit)
    {
        this.timeLimit = timeLimit * 1000L;

    }
    public void startWatch()
    {
        startTime = System.currentTimeMillis();
        isActive = true;
    }

    public void update()
    {
        if(isActive) {
            elapsedTime = System.currentTimeMillis() - startTime;

            if (elapsedTime >= timeLimit) {
                isActive = false;
                elapsedTime = timeLimit;
                startTime = 0L;
            }
        }
    }
    public void reset(int timeLimit)
    {
        isActive = false;
        startTime = 0L;
        elapsedTime = 0L;
        this.timeLimit = timeLimit * 1000L;
    }

    public int getRemainingSeconds() { return (int)((timeLimit - elapsedTime) / 1000); }

    public long getRemainingMilliSeconds() { return timeLimit - elapsedTime; }

    public int getTimeLimit(){ return (int)(timeLimit / 1000); }

    public boolean isActive(){ return isActive; }

}
