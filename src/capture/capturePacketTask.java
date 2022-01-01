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
//        ByteBufferHandler<PrintStream> byteBufferHandler = (pcapHeader, byteBuffer, printStream) -> {
//            PcapPacket packet = new PcapPacket(pcapHeader, byteBuffer);
//            packetCapturer.parseRawPacket(packet);
//
//            if (Thread.currentThread().isInterrupted()) {
//                pcap.breakloop();
//            }
//        };
//
//
//        PcapPacketHandler<PrintStream> pcapPacketHandler = new PcapPacketHandler<PrintStream>() {
//            @Override
//            public void nextPacket(PcapPacket pcapPacket, PrintStream printStream) {
//                printStream.println("Hello");
//            }
//        };
//
//        int cnt = 2;
//        PrintStream out = System.out;
//        int stat = pcap.loop(cnt, byteBufferHandler, out);

        PcapPacket packet = new PcapPacket(JMemory.POINTER);
        int stat;
        boolean cont = true;
        while (cont) {
            stat = pcap.nextEx(packet);

            switch (stat) {
                case Pcap.NEXT_EX_OK -> System.out.println("OK");
                case Pcap.NEXT_EX_EOF -> {
                    System.out.println("EOF");
                    cont = false;
                }
                default -> {
                    System.out.println("Umm... Idk");
                    cont = false;
                }
            }

            if (Thread.currentThread().isInterrupted()) {
                System.out.println("INTERRUPT!");
                break;
            }
        }
    }
}
