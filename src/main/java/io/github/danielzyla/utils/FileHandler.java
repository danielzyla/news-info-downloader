package io.github.danielzyla.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public interface FileHandler {

    Logger log = Logger.getLogger(FileHandler.class.getName());

    default void createFile(String filename) throws IOException {
        File dataFile = new File(filename);
        if (!dataFile.exists()) {
            boolean isNewFileCreated = dataFile.createNewFile();
            log.info("new " + filename + " creation status: " + isNewFileCreated);
        } else {
            log.info(filename + " already exists");
            new FileOutputStream(filename).close();
        }
    }
}
