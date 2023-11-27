package org.muala;

import org.muala.config.Reader;

public class Main {
    public static boolean verboseMode = true;
    public static void main(String[] args) {
        Reader reader = new Reader();
        reader.parseConfigurations();
        reader.checkConfigurations();
        Manager manager = new Manager(reader.getConfiguration());
        manager.manage();
    }
}