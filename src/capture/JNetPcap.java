package capture;

public class JNetPcap extends PacketCapturer {


    @Override
    public String[] getNetworkInterfaces() {
        return new String[0];
    }

    @Override
    public void openPcapFile(String filename) {

    }

    @Override
    public void closePcapFile(String filename) {

    }

    @Override
    public void openInterface(String interfaceName) {

    }

    @Override
    public void getNextPacket() {

    }

    @Override
    public void parseRawPacket() {

    }
}
