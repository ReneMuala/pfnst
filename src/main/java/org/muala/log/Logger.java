package org.muala.log;
import org.muala.Main;
public class Logger {
    public static void warn(String message){
        if(Main.verboseMode) {
            System.out.println("Warning: " + message);
        }
    }
    public static void message(String message){
        if(Main.verboseMode) {
            System.out.println("Message: " + message);
        }
    }
    public static void error(String message){
        if(Main.verboseMode) {
            System.err.println("Error: " + message);
        }
    }
}
