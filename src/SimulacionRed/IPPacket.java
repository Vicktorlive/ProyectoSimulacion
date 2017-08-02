package SimulacionRed;

public class IPPacket {
    private IPHeader ipHeader;
    private TCPHeader ipMessage;

    /**
     * Constructor
     */

    public IPPacket(IPHeader ipHeader, TCPHeader ipMessage) {
        this.ipHeader = ipHeader;
        this.ipMessage = ipMessage;
    }

    /**
     * Getters & Setters
     */
    // TODO: 28/07/17 Modificar a como sea necesario
    public IPHeader getIpHeader() {
        return ipHeader;
    }

    public void setIpHeader(IPHeader ipHeader) {
        this.ipHeader = ipHeader;
    }

    public TCPHeader getIpMessage() {
        return ipMessage;
    }

    public void setIpMessage(TCPHeader ipMessage) {
        this.ipMessage = ipMessage;
    }
}
