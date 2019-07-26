package jaima.util;

/**
 * Write a description of class Timing here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VirtualTimer
extends Thread
{
    private static double scale = 1000.0;
    private static Long start;
    
    /**
     * @return the elapsed time in seconds,
     * regardless of scale
     */
    public static void startTimer() {
        start = System.nanoTime();
    }
    
    /**
     * @return the elapsed time in seconds,
     * regardless of scale, 
     * since startTimer was called
     */
    public static double elapsed() {
        return (System.nanoTime() - start) / 1E9;
    }
    
    /**
     * Set the time scale
     * @param s the scale, 1000=
     */
    public static void setScale(double s) {
        scale = s;
    }
    
    /**
     * @param u the number of time units to delay.
     * at the default scale of 1000, 1 unit = 1 second
     */
    public static void delay(double u) {
        double seconds = u * scale / 1000;
        long ms = (long) (seconds * 1000);
        int ns = ((int) (seconds * 1E9)) % 1000000;
        try { 
            Thread.sleep(ms, ns);
        } catch (Exception e) {
            Log.record(e.toString());
        }
    }
    
//     private double limit;
//     private boolean cancelled;
//     
//     public VirtualTimer()
//     {
//         limit = 1;
//     }
//     
//     public void set(double lim)
//     {
//         limit = lim;
//     }
//     
//     public void run()
//     throws Exception
//     {
//         cancelled = false;
//         delay(limit);
//         
//         if(!cancelled)
//             throw new Exception();
//     }
//     
//     public void cancel()
//     {
//         cancelled = true;
//     }
}
