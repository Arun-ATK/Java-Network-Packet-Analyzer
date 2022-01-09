package capture;

import org.jnetpcap.Pcap;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.PcapPacket;

public class capturePacketTask implements Runnable {
    PacketCapturer packetCapturer;
    Pcap pcap;

    capturePacketTask(PacketCapturer packetCapturer, Pcap pcap) {
        this.packetCapturer = packetCapturer;
        this.pcap = pcap;
    }

    @Override
    public void run() {
        PcapPacket packet = new PcapPacket(JMemory.POINTER);
        boolean cont = true;
        while (cont) {
            int stat = pcap.nextEx(packet);

            switch (stat) {
                case Pcap.NEXT_EX_OK -> {
                    PcapPacket p = new PcapPacket(packet);
                    packetCapturer.addRawPacket(p);
                    packetCapturer.parseRawPacket(p);
                }
                case Pcap.NEXT_EX_EOF -> {
                    System.out.println("File ended");
                    cont = false;
                }
                case Pcap.NEXT_EX_NOT_OK -> {
                    System.out.println("Some error occurred");
                    cont = false;
                }
                case Pcap.NEXT_EX_TIMEDOUT -> System.out.println("Timed Out");
                default -> System.out.println("WHAT: " + stat);
            }

            if (Thread.currentThread().isInterrupted()) {
                System.out.println("INTERRUPT!");
                break;
            }
        }
    }
}
