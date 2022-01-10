package packets;

import java.util.Date;
import java.util.Map;

public class TransportPacket extends Packet {
    public TransportPacket(Map<String, String> networkHeaders,
                           Map<String, String> transportHeaders,
                           String data,
                           Date receiveDate,
                           int size,
                           int headerSize,
                           Protocol protocol) {
        this.networkHeaders = networkHeaders;
        this.transportHeaders = transportHeaders;
        this.data = data;

        this.protocol = protocol;
        protocolLayer = 4;
        this.receiveDate = receiveDate;
        this.size = size;
        this.headerSize = headerSize;
    }
}
