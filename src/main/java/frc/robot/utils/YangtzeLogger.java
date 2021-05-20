package frc.robot.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import frc.robot.utils.Config.LoggerConfig;

public final class YangtzeLogger {
    private static YangtzeLogger Instance = new YangtzeLogger();

    public static YangtzeLogger getInstance() {
        return Instance;
    }

    private String logFileLocation = "/home/lvuser/logs/";

    private YangtzeLogger() {
        File usb1 = new File("/mnt");
        if (usb1.exists()) {
            logFileLocation = "/mnt/logs/";
        }

        try {
            createLogDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Logger logger = Logger.getGlobal();
        logger.setLevel(Level.ALL);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        
        try {
            String timeStamp = new SimpleDateFormat().format(new Date());
            String logfile = logFileLocation + "log_" + timeStamp + ".log";
            FileHandler fileHandler = new FileHandler(logfile);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(LoggerConfig.fileLogLevel);
            logger.addHandler(fileHandler);
            logger.info("log starting on file " + logfile);
        } catch (SecurityException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    private void createLogDirectory() throws IOException {
		File logDirectory = new File(logFileLocation);
		if (!logDirectory.exists()) {
			Files.createDirectory(Paths.get(logFileLocation));
		}
	}
}
