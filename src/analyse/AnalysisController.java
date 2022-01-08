package analyse;

import packets.Packet;

import java.util.EnumMap;

public class AnalysisController {
    private static final EnumMap<Packet.Protocol, Integer> packetCounts = new EnumMap<>(Packet.Protocol.class);

    public void addPacket(Packet packet) {
        packetCounts.put(packet.getProtocol(),
                packetCounts.getOrDefault(packet.getProtocol(), 0) + 1);
    }

    public EnumMap<Packet.Protocol, Integer> getCount() {
        return new EnumMap<>(packetCounts);
    }
}
