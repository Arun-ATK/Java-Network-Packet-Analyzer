package packets;

import java.util.Map;

public class UDPPacket extends Packet {
    @Override
    public Protocol getProtocol() {
        return Protocol.UDP;
    }

    @Override
    public Map<String, String> getNetworkHeaders() {
        return null;
    }

    @Override
    public String getData() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int getHeaderSize() {
        return 0;
    }
}
