package packets;

import java.util.Date;
import java.util.Map;

public class DefaultPacket extends Packet {
    public DefaultPacket(String data,
                         Date receiveDate,
                         int size,
                         int headerSize) {
        this.data = data;
        this.receiveDate = receiveDate;
        this.size = size;
        this.headerSize = headerSize;
        this.protocol = Protocol.Unknown;
    }
}
