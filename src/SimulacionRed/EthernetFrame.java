package SimulacionRed;

public class EthernetFrame {
    private String preamble; // 01010101010101011 56 bits of zeros and ones
    private String destinationAddress; // mac address of destination
    private String sourceAddress; // mac address of source
    private String etherType; // 0800 = IP / 0806 = ARP
    private String payload; // data
    private String frameCheckSeq; // like sha checksum for entire frame

    /**
     * Constructor
     */

    // TODO: 28/07/17 Agregar defaults a como sea necesario
    public EthernetFrame(String preamble, String destinationAddress, String sourceAddress, String etherType, String payload, String frameCheckSeq) {
        this.preamble = preamble;
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

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getFrameCheckSeq() {
        return frameCheckSeq;
    }

    public void setFrameCheckSeq(String frameCheckSeq) {
        this.frameCheckSeq = frameCheckSeq;
    }
}