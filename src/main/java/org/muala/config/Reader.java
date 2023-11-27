package org.muala.config;

import org.muala.log.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    Configuration configuration;
    FileInputStream fileInputStream;
    String configurationsString = "";

    String[] configurationSources = new String[] {
            "./pfnst.txt",
            "~/pfnst.txt",
            "./.pfnst.txt",
            "~/.pfnst.txt",
    };

    void readConfigurationsString(){
        try {
            configurationsString = new String(fileInputStream.readAllBytes());
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }

    void parseFileConfig(String line){
        if(line.matches("\\w+\\s*=\\s*(\\w,?)+")){
            var configPair = line.split("\\s*=\\s*");
            var fileFormatName = configPair[0].trim();
            List<String> formats = new ArrayList<>();
            for (var format:
                    configPair[1].split(",")) {
                formats.add(format.trim());
            }
            configuration.addFileConfiguration(fileFormatName, formats);
        } else {
            Logger.error(String.format("Invalid file configuration '%s'", line));
        }
    }


    void parseActionsConfig(String line){
        if(line.matches("(makeDirectories|useMove)\\s*=\\s*(true|false)")){
            var configPair = line.split("\\s*=\\s*");
            var actionName = configPair[0].trim();
            var actionValue = Boolean.parseBoolean(configPair[1].trim());
            configuration.addActionConfiguration(actionName, actionValue);
        } else {
            Logger.error(String.format("Invalid action configuration '%s'", line));
        }
    }

    void parseDirectoriesConfig(String line){
        if(line.matches("\\w+\\s*=\\s*[\\.\\w\\\\/]+")){
            var configPair = line.split("\\s*=\\s*");
            var fileFormatName = configPair[0].trim();
            var directoryName = configPair[1].trim();
            configuration.addDirectoriesConfiguration(fileFormatName, directoryName);
        } else {
            Logger.error(String.format("Invalid directory configuration '%s'", line));
        }
    }

    public void parseConfigurations(){
        String configurationMode = "[none]";
        for(var line:configurationsString.split("\n")) {
            line = line.trim();
            if(line.matches("#.*") || line.isEmpty())
                continue;
            switch (line){
                case "[files]", "[directories]", "[actions]":
                    configurationMode = line;
                    break;
                default:
                    switch (configurationMode){
                        case "[files]":
                            parseFileConfig(line);
                            break;
                        case "[directories]":
                            parseDirectoriesConfig(line);
                            break;
                        case "[actions]":
                            parseActionsConfig(line);
                            break;
                        default:
                            Logger.warn(String.format("No context provided for line: %s", line));
                    }
            }

        }
    }
    public void checkConfigurations(){
        for (var formatInDir:
                configuration.getDirectories().keySet()) {
            if(!configuration.getFiles().containsKey(formatInDir)){
                Logger.error(String.format("%s (from directories) is not a defined fileFormat Name", formatInDir));
            }
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Reader(){
        for (var source:
             configurationSources) {
            try {
                fileInputStream = new FileInputStream(source);
                break;
            } catch (FileNotFoundException e) {
                Logger.error(String.format("FileNotFound '%s'", source));
            }
        }
        if(fileInputStream == null){
            Logger.error("Could not read configuration file, exiting...");
            System.exit(-1);
        }
        configuration = new Configuration();
        readConfigurationsString();
    }
}
