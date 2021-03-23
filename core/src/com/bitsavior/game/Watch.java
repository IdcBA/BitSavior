package com.bitsavior.game;

/**
 * a watch holds information and functionality for
 * implementing timers e.g for controlling time sensitive behaviours and
 * effect changes
 */
public class Watch
{
    /**
     * the current system time in milliseconds
     */
    public long startTime = 0L;
    /**
     * the elapsed time since the start of the watch in milliseconds
     */
    public long elapsedTime = 0L;
    /**
     * the desired time limit in milliseconds
     */
    public long timeLimit;
    /**
     * shows if the watch is active or not
     */
    boolean isActive = false;
    /**
     * constructor
     * @param timeLimit : time limit in seconds
     */
    public Watch(int timeLimit)
    {
        this.timeLimit = timeLimit * 1000L;

    }
    /**
     * starts the watch
     */
    public void startWatch()
    {
        startTime = System.currentTimeMillis();
        isActive = true;
    }
    /**
     * updates the times and calculates the elapsed time
     */
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
    /**
     * reset the current watch and set all values to zero
     * @param timeLimit : the new time limit in seconds
     */
    public void reset(int timeLimit)
    {
        isActive = false;
        startTime = 0L;
        elapsedTime = 0L;
        this.timeLimit = timeLimit * 1000L;
    }
    /**
     * gets the remaining seconds until watch is finished
     * @return : the remaining time of the watch in seconds
     */
    public int getRemainingSeconds() { return (int)((timeLimit - elapsedTime) / 1000); }
    /**
     * gets the remaining milliseconds until watch is finished
     * @return : the remaining time of the watch in milliseconds
     */
    public long getRemainingMilliSeconds() { return timeLimit - elapsedTime; }
    /**
     * gets the time limit of the watch
     * @return : the time limit of the watch in seconds
     */
    public int getTimeLimit(){ return (int)(timeLimit / 1000); }
    /**
     * gets if the watch is active or not
     * @return : true if the watch is active, false if not
     */
    public boolean isActive(){ return isActive; }
}
