package fr.elercia.redcloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SaveConfig {

    public static String PATH_TO_FILES;

    @Value("${storage.path}")
    public void setPathToFiles(String pathToFiles) {
        PATH_TO_FILES = pathToFiles;
    }
}
