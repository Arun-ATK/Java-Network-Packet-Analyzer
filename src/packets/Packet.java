package packets;

import java.util.Date;
import java.util.Map;

public abstract class Packet {
    public enum Protocol {
        HTTP,
        TCP,
        UDP,
        IP,
        Unknown
    }

    protected Protocol protocol = Protocol.Unknown;
    protected int protocolLayer;
    protected Date recvTime;

    protected Map<String, String> networkHeaders;
    protected Map<String, String> transportHeaders;
    protected Map<String, String> applicationHeaders;
    protected String data;

    public Protocol getProtocol() {
        return protocol;
    }

    public Map<String, String> getNetworkHeaders() {
        if (protocolLayer >= 3) {
            return networkHeaders;
        }
        else {
            return null;
        }
    }
    public Map<String, String> getTransportHeaders() {
        if (protocolLayer >= 4) {
            return transportHeaders;
        }
        else {
            return null;
        }
    }
    public Map<String, String> getApplicationHeaders() {
        if (protocolLayer == 5) {
            return applicationHeaders;
        }
        else {
            return null;
        }
    }

    public abstract String getData();

    public abstract int size();
    public abstract int headerSize();
}
