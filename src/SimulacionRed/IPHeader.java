package SimulacionRed;
// http://mars.netanya.ac.il/~unesco/cdrom/booklet/HTML/NETWORKING/node020.html

/**
 * Clase que permite crear el IP Header de un paquete de datos tipo IP
 */
public class IPHeader {
    private char version; // ipv4 = 4
    private char internetHeaderLength; // 5
    private String typeOfService;
    private int sizeOfDatagram; // In bytes, combined length of header and the data
    private int identification; //  Uniquely identifies the datagram. Usually incremented by 1 each time a datagram is sent. All fragments of a datagram contain the same identification value.
    private String flags; // Sequence of three flags that indicate to the router whether to fragment the packet
    private int fragmentOffset; // number of fragment
    private int timeToLive; // Number of hops/links which the packet may be routed over, decremented by most routers - used to prevent routing loops (this -- each router)
    private char protocol; // protocol being used 1 = ICMP / 6 = TCP
    private String headerChecksum; // validation like sha
    private String sourceAddress; // ip of sender
    private String destinationAddress; // ip of receiver

    /**
     * @Constructor
     * @param sourceAddress String IP de fuente
     * @param destinationAddress String IP de quien recibe
     * @param sizeOfDatagram int Tamano de datos
     */
    public IPHeader(String sourceAddress, String destinationAddress, int sizeOfDatagram) {
        this.version = '4';
        this.internetHeaderLength = '5';
        this.typeOfService = "0010";// = maximize reliability
        this.sizeOfDatagram = sizeOfDatagram;
        this.identification = 1;
        this.flags = "100";
        this.fragmentOffset = 1;
        this.timeToLive = 6;
        this.protocol = '6';
        this.headerChecksum = ""; // Process after all the package has been assembled
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

    public int getSizeOfDatagram() {
        return sizeOfDatagram;
    }

    public void setSizeOfDatagram(int sizeOfDatagram) {
        this.sizeOfDatagram = sizeOfDatagram;
    }

    public int getIdentification() {
        return identification;
    }

    public void setIdentification(int identification) {
        this.identification = identification;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public int getFragmentOffset() {
        return fragmentOffset;
    }

    public void setFragmentOffset(int fragmentOffset) {
        this.fragmentOffset = fragmentOffset;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
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