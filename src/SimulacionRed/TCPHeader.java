package SimulacionRed;
// http://es.ccm.net/contents/281-protocolo-tcp
// http://www.freesoft.org/CIE/Course/Section4/8.htm
public class TCPHeader {
    private String sourcePort; // port from source terminal
    private String destinationPort; // port from destination terminal
    /*
    The sequence number of the first data octet in this segment (except
    when SYN is present). If SYN is present the sequence number is the
    initial sequence number (ISN) and the first data octet is ISN+1.
     */
    private int sequenceNumber;
    //////////////////////////////////////////////////////////////////////

    /*
    If the ACK control bit is set this field contains the value of the
    next sequence number the sender of the segment is expecting to
    receive.  Once a connection is established this is always sent.
     */
    private int ackNumber;
    //////////////////////////////////////////////////////////////////////

    /*
    The number of 32 bit words in the TCP Header.  This indicates where
    the data begins.  The TCP header (even one including options) is an
    integral number of 32 bits long.
     */
    private int dataOffset;
    //////////////////////////////////////////////////////////////////////

    private String reserved; // Not being used atm

    private int urg; //    URG:  Urgent Pointer field significant
    private int ack; //    ACK:  Acknowledgment field significant
    private int psh; //    PSH:  Push Function
    private int rst; //    RST:  Reset the connection
    private int syn; //    SYN:  Synchronize sequence numbers
    private int fin; //    FIN:  No more data from sender

    /*
    The number of data octets beginning with the one indicated in the
    acknowledgment field which the sender of this segment is willing to
    accept.
     */
    private String window;
    //////////////////////////////////////////////////////////////////////
    private String checksum; // checksum for further validation
    private String urgentPointer;
    private int options;
    private int padding;
    private Data data; // data being sent

    /**
     * Constructor
     */

    // TODO: 28/07/17 Agregar defaults a como sea necesario
    public TCPHeader(String sourcePort, String destinationPort, String window, Data data) {
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.sequenceNumber = 0;
        this.ackNumber = 0;
        this.dataOffset = 4;
        this.reserved = "";
        this.urg = 0;
        this.ack = 0;
        this.psh = 0;
        this.rst = 0;
        this.syn = 0;
        this.fin = 0;
        this.window = window;
        this.checksum = "";
        this.urgentPointer = "0000000000000000";
        this.options = 0;
        this.padding = 32;
        this.data = data;
    }

    /**
     * Getters & Setters
     */
    public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getAckNumber() {
        return ackNumber;
    }

    public void setAckNumber(int ackNumber) {
        this.ackNumber = ackNumber;
    }

    public int getDataOffset() {
        return dataOffset;
    }

    public void setDataOffset(int dataOffset) {
        this.dataOffset = dataOffset;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public int getUrg() {
        return urg;
    }

    public void setUrg(int urg) {
        this.urg = urg;
    }

    public int getAck() {
        return ack;
    }

    public void setAck(int ack) {
        this.ack = ack;
    }

    public int getPsh() {
        return psh;
    }

    public void setPsh(int psh) {
        this.psh = psh;
    }

    public int getRst() {
        return rst;
    }

    public void setRst(int rst) {
        this.rst = rst;
    }

    public int getSyn() {
        return syn;
    }

    public void setSyn(int syn) {
        this.syn = syn;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getUrgentPointer() {
        return urgentPointer;
    }

    public void setUrgentPointer(String urgentPointer) {
        this.urgentPointer = urgentPointer;
    }

    public int getOptions() {
        return options;
    }

    public void setOptions(int options) {
        this.options = options;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}