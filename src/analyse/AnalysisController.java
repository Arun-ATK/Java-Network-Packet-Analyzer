package analyse;

import packets.Packet;

import java.util.Date;
import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

public class AnalysisController {
    private static int totalPackets = 0;
    private static float totalSize = 0;
    private static Date startTime = null;
    private static EnumMap<Packet.Protocol, Integer> packetCounts = new EnumMap<>(Packet.Protocol.class);

    public static void addPacket(Packet packet) {
        packetCounts.put(packet.getProtocol(),
                packetCounts.getOrDefault(packet.getProtocol(), 0) + 1);

        if (startTime == null) {
            startTime = new Date(System.currentTimeMillis());
        }
        ++totalPackets;
        totalSize += packet.getSize();
    }

    public static EnumMap<Packet.Protocol, Integer> getCounts() {
        return new EnumMap<>(packetCounts);
    }

    public static float getPacketRate() {
        long duration = getElapsedTime();
        long durationSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);

        return totalPackets / (float) durationSeconds;
    }

    public static int getTotalPackets() {
        return totalPackets;
    }

    public static float getTotalSize() {
        return totalSize;
    }

    public static float getTransferRate() {
        float size = getTotalSize() / (8 * 1024);
        long duration  = getElapsedTime();
        long durationSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);

        return size / (float) durationSeconds;
    }

    public static long getElapsedTime() {
        Date currentTime = new Date(System.currentTimeMillis());
        return currentTime.getTime() - startTime.getTime();
    }

    public static void resetAnalytics() {
        resetCount();
        resetStartTime();
    }
    private static void resetCount() {
        packetCounts = new EnumMap<>(Packet.Protocol.class);
        totalPackets = 0;
        totalSize = 0;
    }
    private static void resetStartTime() {
        startTime = null;
    }
}
