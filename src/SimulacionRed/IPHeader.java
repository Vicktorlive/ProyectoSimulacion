package SimulacionRed;

public class IPHeader {
    private char version; // ipv4 = 4
    private char internetHeaderLength; // 5
    private String typeOfService; // TODO: 28/07/17 Investigar los campos en los que no hay informacion para ver que defaults se usan para TCP
    private char totalLength;
    private String identification;
    private String flags; //
    private String fragmentOffset;
    private char timeToLive; // this-- for every time it jumps a router
    private char protocol; // protocol being used 1 = ICMP / 6 = TCP
    private String headerChecksum; // validation like sha
    private String sourceAddress; // ip of sender
    private String destinationAddress; // ip of receiver

    /**
     * Constructor
     */

    // TODO: 28/07/17 Agregar defaults a como sea necesario
    public IPHeader(char version, char internetHeaderLength, String typeOfService, char totalLength, String identification, String flags, String fragmentOffset, char timeToLive, char protocol, String headerChecksum, String sourceAddress, String destinationAddress) {
        this.version = version;
        this.internetHeaderLength = internetHeaderLength;
        this.typeOfService = typeOfService;
        this.totalLength = totalLength;
        this.identification = identification;
        this.flags = flags;
        this.fragmentOffset = fragmentOffset;
        this.timeToLive = timeToLive;
        this.protocol = protocol;
        this.headerChecksum = headerChecksum;
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
    }

    /**
     * Getters & Setters
     */

    public char getVersion() {
        return version;
    }

    public void setVersion(char version) {
        this.version = version;
    }

    public char getInternetHeaderLength() {
        return internetHeaderLength;
    }

    public void setInternetHeaderLength(char internetHeaderLength) {
        this.internetHeaderLength = internetHeaderLength;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public char getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(char totalLength) {
        this.totalLength = totalLength;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getFragmentOffset() {
        return fragmentOffset;
    }

    public void setFragmentOffset(String fragmentOffset) {
        this.fragmentOffset = fragmentOffset;
    }

    public char getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(char timeToLive) {
        this.timeToLive = timeToLive;
    }

    public char getProtocol() {
        return protocol;
    }

    public void setProtocol(char protocol) {
        this.protocol = protocol;
    }

    public String getHeaderChecksum() {
        return headerChecksum;
    }

    public void setHeaderChecksum(String headerChecksum) {
        this.headerChecksum = headerChecksum;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
}