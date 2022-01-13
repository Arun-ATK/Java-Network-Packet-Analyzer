package sysutil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
    // Singleton Object
    private static final Logger logger = new Logger();

    Thread shutDownThread = new Thread(new LoggerShutDown());

    BufferedWriter bufferedWriter;

    private Logger() {
        try {
            File logFile = new File("Packet_Sniffer_LOG.txt");
            logFile.createNewFile();

            bufferedWriter = new BufferedWriter(new FileWriter(logFile, false));
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
            bufferedWriter.write("Date: " + new Date(System.currentTimeMillis()) + "\n");
            bufferedWriter.write(message + "\n");
            bufferedWriter.write("\n-----------\n\n");

            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeLog() {
        try {
            System.out.println("Log closed");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class LoggerShutDown implements Runnable {
        @Override
        public void run() {
            Logger.getLogger().closeLog();
        }
    }
}
