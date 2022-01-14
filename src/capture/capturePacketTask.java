package capture;

import org.jnetpcap.Pcap;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.PcapPacket;
import sysutil.Logger;

import java.io.IOException;

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
                    Logger.getLogger().writeMessage("File Ended");
                    cont = false;
                }
                case Pcap.NEXT_EX_NOT_OK -> {
                    Logger.getLogger().writeMessage("Error occurred while receiving packet");
                    cont = false;
                }
            }

            if (Thread.currentThread().isInterrupted()) {
                Logger.getLogger().writeMessage("Capture Thread interrupted");
                break;
            }
        }
    }
}
