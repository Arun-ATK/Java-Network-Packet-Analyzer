package capture;

import java.io.File;
import java.util.ArrayList;

public abstract class PacketCapturer {
    protected Thread captureThread = null;

    public abstract ArrayList<NetworkInterface> getNetworkInterfaces();

    public abstract boolean openPcapFile(File file);

    public abstract boolean openInterface(int interfaceNum);

    public abstract void startCapture();
    public abstract void stopCapture();

    public abstract void addRawPacket(Object p);
    public abstract void parseRawPacket(Object p);

    public abstract void saveFile(String fileName);
}
