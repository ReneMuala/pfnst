package org.muala;

import org.muala.config.Configuration;
import org.muala.log.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Manager {
    Configuration configuration;

    public Manager(Configuration configuration){
        this.configuration = configuration;
    }

    public List<String> getFileNames(){
        List<String> fileNames = new ArrayList<>();
        var currentDir = new File("./");
        for (var file:
                Objects.requireNonNull(currentDir.listFiles())) {
            if(file.isFile())
                fileNames.add(file.getName());
        }
        return fileNames;
    }

    String getFileCategory(String filename){
        final String[] category = {new String()};
        if(!(filename.equals("pfnst.txt") || filename.equals(".pfnst.txt"))) {
            configuration.getFiles().forEach((String type, List<String> formats) -> {
                for (var format :
                        formats) {
                    if(filename.endsWith(format)) {
                        category[0] = type;
                    }
                }
            });
        }
        return category[0];
    }

    public Map<String, List<String>> getCategorizedFileNames(List<String> filenames){
        Map<String, List<String>> map = new HashMap<>();
        for (var filename:
             filenames) {
            var category = getFileCategory(filename);
            if(!category.isEmpty()){
                if(!map.containsKey(category)){
                    map.put(category, new ArrayList<>());
                }
                map.get(category).add(filename);
            }
        }
        return map;
    }

    public void organizeFiles(Map<String, List<String>> categorizedFiles){
        categorizedFiles.forEach((String category, List<String> files)->{
            var directory = new File(configuration.getDirectories().get(category));
            if(!directory.isDirectory() && configuration.getActions().get("makeDirectories")){
                directory.mkdir();
            }

            if(directory.isDirectory()){
                for (var filename:
                     files) {
                    var origin = new File(filename);
                    var destination = new File(directory.getPath()+"/"+origin.getName());
                    if(configuration.getActions().get("useMove")){
                        try {
                            Logger.message(String.format("(!) moving %s to %s", origin.getPath(), destination.getPath()));
                            Files.move(origin.toPath(),  destination.toPath());
                        } catch (IOException e) {
                            Logger.error(String.format("Failed to move, reason: %s", e.getMessage()));
                        }
                    } else {
                        try {
                            Logger.message(String.format("(!) copying %s to %s", origin.getPath(), destination.getPath()));
                            Files.copy(origin.toPath(),  destination.toPath());
                        } catch (IOException e) {
                            Logger.error(String.format("Failed to copy, reason: %s", e.getMessage()));
                        }
                    }
                }
            } else {
                Logger.message(String.format("Could not create or use directory: %s", directory.getPath()));
            }
        });
    }

    public void manage(){
        var filenames = getFileNames();
        var categorizedFileNames = getCategorizedFileNames(filenames);
        organizeFiles(categorizedFileNames);
    }
}
