package packets;

import java.util.Date;
import java.util.Map;

public class ApplicationPacket extends Packet {

    public ApplicationPacket(Map<String, String> networkHeaders,
                             Map<String, String> transportHeaders,
                             Map<String, String> applicationHeaders,
                             String data,
                             Date recvTime,
                             int size,
                             int headerSize,
                             Protocol protocol) {
        this.networkHeaders = networkHeaders;
        this.transportHeaders = transportHeaders;
        this.applicationHeaders = applicationHeaders;
        this.data = data;

        this.protocol = protocol;
        protocolLayer = 5;
        this.receiveDate = recvTime;
        this.size = size;
        this.headerSize = headerSize;
    }
}
