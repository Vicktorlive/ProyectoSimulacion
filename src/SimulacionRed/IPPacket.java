package SimulacionRed;

/**
 * Clase donde se argupa el Header con un payload TCP para crear el paquete TCP/IP
 */
public class IPPacket {
    private IPHeader ipHeader;
    private TCPHeader ipMessage;

    /**
     * @Constructor
     * @param ipHeader Objeto IPHeader
     * @param ipMessage Objeto TCPHeader
     */
    public IPPacket(IPHeader ipHeader, TCPHeader ipMessage) {
        this.ipHeader = ipHeader;
        this.ipMessage = ipMessage;
    }

    /**
     * Getters & Setters
     */
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
