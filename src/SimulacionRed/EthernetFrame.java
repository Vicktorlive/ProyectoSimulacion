package SimulacionRed;

public class EthernetFrame {
    private String preamble; // 01010101010101011 56 bits of zeros and ones delimited by 10101011
    private String destinationAddress; // mac address of destination
    private String sourceAddress; // mac address of source
    private String etherType; // 0800 = IP / 0806 = ARP
    private IPPacket payload; // data
    private int frameCheckSeq; // Sort of counter to be able to assemble complete data in order

    /**
     * @Constructor
     * @param destinationAddress String MAC de destino
     * @param sourceAddress String MAC de fuente
     * @param etherType String 0800 = IP / 0806 ARP
     * @param payload String
     * @param frameCheckSeq String Permite identificar la integridad de datos y el orden
      */
    public EthernetFrame(String destinationAddress, String sourceAddress, String etherType, IPPacket payload, int frameCheckSeq) {
        this.preamble = "10101010 10101010 10101010 10101010 10101010 10101010 10101010 10101011";
        this.destinationAddress = destinationAddress;
        this.sourceAddress = sourceAddress;
        this.etherType = etherType;
        this.payload = payload;
        this.frameCheckSeq = frameCheckSeq;
    }

    /**
     * Getters & Setters
     */
    public String getPreamble() {
        return preamble;
    }

    public void setPreamble(String preamble) {
        this.preamble = preamble;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getEtherType() {
        return etherType;
    }

    public void setEtherType(String etherType) {
        this.etherType = etherType;
    }

    public IPPacket getPayload() {
        return payload;
    }

    public void setPayload(IPPacket payload) {
        this.payload = payload;
    }

    public int getFrameCheckSeq() {
        return frameCheckSeq;
    }

    public void setFrameCheckSeq(int frameCheckSeq) {
        this.frameCheckSeq = frameCheckSeq;
    }

    /**
     * Modificar todos las propiedades del frame para resuarlo sin volver a instanciar otro
     * @param destinationAddress String MAC destino
     * @param sourceAddress String MAC fuente
     * @param etherType 0800 = IP / 0806 = ARP
     * @param payload
     * @param frameCheckSeq
     */
    public void reFrame(String destinationAddress, String sourceAddress, String etherType, IPPacket payload, int frameCheckSeq) {
        setDestinationAddress(destinationAddress);
        setSourceAddress(sourceAddress);
        setEtherType(etherType);
        setPayload(payload);
        setFrameCheckSeq(frameCheckSeq);
    }
}