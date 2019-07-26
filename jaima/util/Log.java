package jaima.util;

// import java.io.*;
import java.io.PrintStream;
// import java.util.*;
import java.util.Date;

/**
 * Logging provides a simple way for every agent to 
 * record significant events.
 * An agent just has to import this package, 
 * then call Log.record("description") 
 * to record an event.
 * The event will be timestamped, written to the log,
 * and added to a metric.
 * 
 * @author William H. Hooper
 * @version 2006-12-22
 */
public class Log
{
    private static PrintStream log;

    private static void init()
    {
        if (log == null)
        {
            set(System.err);
        }
    }
    
    public static void set(String filename)
    {
        PrintStream p;
        try {
            p = new PrintStream(filename);
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
        
        log = p;
        record("Log Opened");
    }
    
    public static void set(PrintStream p)
    {
        log = p;
        record("Log Opened");
    }
    
    public static void record (String description)
    {
        init();
        log.println(new Date() + ": " + description);
        log.flush();
        Metrics.increment("event: " + description);
    }
}
