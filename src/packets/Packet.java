package packets;

import java.util.Date;

public abstract class Packet {
    public enum Protocol {
        HTTP,
        TCP,
        UDP,

        Unknown
    }

    protected Protocol protocol = Protocol.Unknown;
    protected Date recvTime;

    public abstract Protocol getProtocol();

    public abstract String getPacketHeaders();

    public abstract String getData();

    public abstract int size();

    public abstract int headerSize();
}
