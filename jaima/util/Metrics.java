package jaima.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Metrics provide a simple way for every agent to keep
 * performance statistics.  
 * An agent just has to import this package, 
 * then call Metrics.set("label") or 
 * Metrics.increment("label") to track any kind of measurement.
 * A viewer can call Metrics.KeySet() to get a list 
 * of all the measurements being tracked, and display them
 * by iterating through measurements 
 * and using get() to get their values.
 * 
 * @author William H. Hooper
 * @version 2006-12-22
 */
public class Metrics
{
    private static Map<String, String> map;

    public static void init() {
        if (map == null)
        {
            map = new HashMap<String, String>();
        }
    }
    
    public static void set(String name, int i) {
        init();
        map.put(name, Integer.toString(i));
    }

    public static int getInt(String name) {
        init();
        String value = map.get(name);
        return value == null 
            ? 0
            : Integer.valueOf(value);
    }
    
    public static void setMax(String name, int i) {
        init();
        
        String value = map.get(name);
        if(value == null) {
            map.put(name, Integer.toString(i));
            return;
        }
        
        int old = Integer.valueOf(value);
        if(i > old)
        {
            map.put(name, Integer.toString(i));
        }
    }
    
    public static void increment(String name, int i)
    {
        set(name, getInt(name) + i);
    }
    
    public static void increment(String name)
    {
        increment(name, 1);
    }
    
    public static void set(String name, double d) {
        init();
        map.put(name, Double.toString(d));
    }

    public static double getDouble(String name) {
        init();
        String value = get(name);
        return value == null 
            ? 0.0
            : Double.valueOf(value);
    }
    
    public static void increment(String name, double d)
    {
        set(name, getDouble(name) + d);
    }

    public static String get(String name) {
        init();
        return map.get(name);
    }
    
    public static String getAll()
    {
        String output = "Summary of Metrics:\n";
        int labelWidth = longestLabel().length();
        int valueWidth = longestValue().length();
        String format = "%-" + labelWidth + "s =  %" 
            + valueWidth + "s\n";
        for(String label : keySet())
        {
            String value = get(label);
            output += String.format(format, label, value);
        }
        return output;
    }
    
    public static String longestLabel()
    {
        String longest = "";
        for(String label : keySet())
        {
            if(label.length() > longest.length())
                longest = label;
        }
        return longest;
    }
    
    public static String longestValue()
    {
        String longest = "";
        for(String value : values())
        {
            if(value.length() > longest.length())
                longest = value;
        }
        return longest;
    }
    
    public static void clear(String name)
    {
        init();
        map.remove(name);
    }
    
    public static void clearAll()
    {
        map = new HashMap<String, String>();
    }

    public static Set<String> keySet() {
        init();
        return new TreeSet<String>(map.keySet());
    }

    public static Set<String> values() {
        init();
        return new TreeSet<String>(map.values());
    }
}
