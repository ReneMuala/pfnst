package org.muala.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
    /**
     * <h1><pre>[files]</pre></h1>
     * Stores information about the file section.
     * <h2>Syntax:</h2>
     * <pre>formatName = fileFormat1,...,fileFormatN</pre>
     * <h2>Example:</h2>
     * <pre>photo=jpg,jpeg,png</pre>
     */
    final private Map<String, List<String>> files = new HashMap<>();
    public void addFileConfiguration(String formatName, List<String> formats){
        files.put(formatName, formats);
    }
    /**
     * Stores information about the directories associated to file format names.
     * Syntax:
     * formatName = folder
     * Example:
     * photo=./photosFolder
     */
    final private Map<String, String> directories = new HashMap<>();

    public void addDirectoriesConfiguration(String formatName, String directory){
        directories.put(formatName, directory);
    }

    /**
     * Stores information about possible actions.
     * Syntax:
     * actionName = booleanValue
     * Example:
     * makeDirectories=false
     */
    final private Map<String, Boolean> actions = new HashMap<>();
    public void addActionConfiguration(String action, Boolean value){
        actions.put(action, value);
    }

    public Map<String, Integer> getOptions() {
        return options;
    }

    /**
     * Stores information about options.
     * Syntax:
     * actionName = booleanValue
     * Example:
     * maxRenames=9999
     */
    final private Map<String, Integer> options = new HashMap<>();
    public void addOptionConfiguration(String action, Integer value){
        options.put(action, value);
    }
    public Map<String, List<String>> getFiles() {
        return files;
    }

    public Map<String, String> getDirectories() {
        return directories;
    }

    public Map<String, Boolean> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "files=" + files +
                ", directories=" + directories +
                ", actions=" + actions +
                ", options=" + options +
                '}';
    }

    public Configuration(){
        actions.put("makeDirectories", true);
        actions.put("useMove", true);
        actions.put("replaceExisting", false);
        options.put("maxRenames", 9999);
    }
}
