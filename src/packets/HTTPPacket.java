package packets;

import java.util.Date;
import java.util.Map;

public class HTTPPacket extends Packet {

    public HTTPPacket(Map<String, String> ipHeader,
                      Map<String, String> tcpHeader,
                      Map<String, String> httpHeader,
                      String data,
                      Date recvTime,
                      int size,
                      int headerSize) {
        networkHeaders = ipHeader;
        transportHeaders = tcpHeader;
        applicationHeaders = httpHeader;
        this.data = data;

        protocol = Protocol.HTTP;
        protocolLayer = 5;
        this.recvTime = recvTime;
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
