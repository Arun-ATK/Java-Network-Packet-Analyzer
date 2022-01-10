package packets;

import java.util.Date;
import java.util.Map;

public class NetworkPacket extends Packet {
    public NetworkPacket(Map<String, String> networkHeaders,
                            String data,
                            Date receiveDate,
                            int size,
                            int headerSize,
                            Protocol protocol) {
        this.networkHeaders = networkHeaders;
        this.data = data;

        this.protocol = protocol;
        protocolLayer = 3;
        this.receiveDate = receiveDate;
        this.size = size;
        this.headerSize = headerSize;
    }
}
