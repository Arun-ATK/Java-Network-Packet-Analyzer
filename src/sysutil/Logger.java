package sysutil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    // Singleton Object
    private static final Logger logger = new Logger();

    BufferedWriter bufferedWriter;

    private Logger() {
        try {
            File logFile = new File("Packet_Sniffer_LOG.txt");
            logFile.createNewFile();

            bufferedWriter = new BufferedWriter(new FileWriter(logFile));
        } catch (IOException ex) {
            // TODO: Send message to error GUI
            ex.printStackTrace();
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public synchronized void writeMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.write("\n-----------\n");

            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
