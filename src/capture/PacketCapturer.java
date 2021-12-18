package capture;

import java.util.ArrayList;

public abstract class PacketCapturer {
    public abstract ArrayList<NetworkInterface> getNetworkInterfaces();

    public abstract void openPcapFile(String filename);
    public abstract void closePcapFile(String filename);

    public abstract void openInterface(String interfaceName);

    public abstract void getNextPacket();

    public abstract void parseRawPacket();
}
