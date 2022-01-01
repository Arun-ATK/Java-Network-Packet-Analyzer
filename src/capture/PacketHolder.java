package capture;

import packets.Packet;

import java.util.ArrayList;

public class PacketHolder {
    private final ArrayList<Packet> packets = new ArrayList<>();

    public void addPacket(Packet p) {
        packets.add(p);
    }

    public Packet getLatestPacket() {
        return packets.get(packets.size() - 1);
    }

    public Packet getPacket(int index) {
        return packets.get(index);
    }

    public int size() {
        return packets.size();
    }

}
