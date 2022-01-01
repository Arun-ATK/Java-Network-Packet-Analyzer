package capture;

import org.jnetpcap.ByteBufferHandler;
import org.jnetpcap.Pcap;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.PeeringException;

import java.io.PrintStream;
import java.util.Date;

public class capturePacketTask implements Runnable {
    PacketCapturer packetCapturer;
    Pcap pcap;

    capturePacketTask(PacketCapturer packetCapturer, Pcap pcap) {
        this.packetCapturer = packetCapturer;
        this.pcap = pcap;
    }

    @Override
    public void run() {
        ByteBufferHandler<PrintStream> byteBufferHandler = (pcapHeader, byteBuffer, printStream) -> {
            try {
                PcapPacket packet = new PcapPacket(pcapHeader.caplen());
                packet.peerHeaderAndData(pcapHeader, byteBuffer);
                packetCapturer.parseRawPacket(packet);

            } catch (PeeringException e) {
                e.printStackTrace();
            }

            if (Thread.currentThread().isInterrupted()) {
                pcap.breakloop();
            }
        };

        int cnt = 10;
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
