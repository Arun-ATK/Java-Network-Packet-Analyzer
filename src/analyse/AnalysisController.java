package analyse;

import packets.Packet;

import java.util.EnumMap;

public class AnalysisController {
    private static EnumMap<Packet.Protocol, Integer> packetCounts = new EnumMap<>(Packet.Protocol.class);

    public static void addPacket(Packet packet) {
        packetCounts.put(packet.getProtocol(),
                packetCounts.getOrDefault(packet.getProtocol(), 0) + 1);
    }

    public static EnumMap<Packet.Protocol, Integer> getCounts() {
        return new EnumMap<>(packetCounts);
    }

    public static void resetCount() {
        packetCounts = new EnumMap<>(Packet.Protocol.class);
    }
}
