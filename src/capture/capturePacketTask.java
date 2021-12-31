package capture;

import org.jnetpcap.ByteBufferHandler;
import org.jnetpcap.Pcap;

import java.io.PrintStream;
import java.util.Date;

public class capturePacketTask implements Runnable {
    Pcap pcap;

    capturePacketTask(Pcap pcap) {
        this.pcap = pcap;
    }

    @Override
    public void run() {
        ByteBufferHandler<PrintStream> byteBufferHandler = (pcapHeader, byteBuffer, printStream) -> {
            // TODO: Replace with basic setup and method call for parsing the packet
            printStream.println("Packet captured on: " + new Date(pcapHeader.timestampInMillis()));
            printStream.println("Packet size: " + pcapHeader.caplen());
            printStream.println("-----");

            if (Thread.currentThread().isInterrupted()) {
                pcap.breakloop();
            }
        };

        int cnt = -1;
        PrintStream out = System.out;
        int stat = pcap.loop(cnt, byteBufferHandler, out);

        switch (stat) {
            case 0 -> System.out.println("Normal stop");
            case -1 -> System.out.println("Error");
            case -2 -> System.out.println("Interrupt!");
            default -> System.out.println("Umm...Idk " + stat);
        }
    }
}
