package capture;

import java.util.ArrayList;

public abstract class PacketCapturer {
    protected Thread captureThread = null;

    public abstract ArrayList<NetworkInterface> getNetworkInterfaces();

    public abstract void openPcapFile(String filepath);
    public abstract void closePcapFile(String filename);

    public abstract void openInterface(int interfaceNum);

    public abstract void startCapture();
    public abstract void stopCapture();

    public abstract void getNextPacket();

    public abstract void parseRawPacket();
}
