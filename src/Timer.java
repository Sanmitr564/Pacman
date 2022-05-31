public class Timer {
    private int seconds;
    private int frames;
    private int totalFrames;
    private boolean isPaused;

    public Timer() {
        seconds = 0;
        frames = 0;
        totalFrames = 0;
        isPaused = true;
    }

    public int getSeconds() {
        return totalFrames / 60;
    }

    public int getFrames() {
        return totalFrames % 60;
    }

    public void iterate() {
        if(!isPaused) {
            totalFrames++;
        }
    }

    public void reset(){
        seconds = 0;
        frames = 0;
        totalFrames = 0;
        isPaused = true;
    }

    public void stop(){
        isPaused = true;
    }

    public void start(){
        isPaused = false;
    }

    public boolean startStop(){
        isPaused = !isPaused;
        return isPaused;
    }
}
