package packets;

import java.util.Map;

public class DefaultPacket extends Packet {
    @Override
    public Protocol getProtocol() {
        return null;
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
    public int size() {
        return 0;
    }

    @Override
    public int headerSize() {
        return 0;
    }
}
