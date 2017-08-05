package SimulacionRed;
// http://es.ccm.net/contents/281-protocolo-tcp
// http://www.freesoft.org/CIE/Course/Section4/8.htm
public class TCPHeader {
    private int sourcePort; // port from source terminal
    private int destinationPort; // port from destination terminal
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
    private int window; // Using a default for now
    //////////////////////////////////////////////////////////////////////
    private String checksum; // checksum for further validation
    private String urgentPointer;
    private String data; // data being sent

    /**
     * Constructor
     */
    public TCPHeader(int sourcePort, int destinationPort, String data) {
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
        this.window = 32;
        this.checksum = "";
        this.urgentPointer = "0000000000000000";
        this.data = data;
    }

    /**
     * Getters & Setters
     */
    public int getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(int destinationPort) {
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

    public int getWindow() {
        return window;
    }

    public void setWindow(int window) {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int calcDatagramSize() {
        Data data = new Data();
        String sp = Integer.toString(getSourcePort());
        String dp = Integer.toString(getDestinationPort());
        String sqN = Integer.toString(getSequenceNumber());
        String acN = Integer.toString(getAckNumber());
        String datOff = Integer.toString(getDataOffset());
        String res = "00000000";
        String urg = Integer.toString(getUrg());
        String ack = Integer.toString((getAck()));
        String psh = Integer.toString(getPsh());
        String rst = Integer.toString(getRst());
        String syn = Integer.toString(getSyn());
        String fin = Integer.toString(getFin());
        String win = Integer.toString(getWindow());
        String chk = getChecksum();
        String urgP = getUrgentPointer();
        String dat = getData();

        String all = sp + dp + sqN + acN + datOff + res + urg + ack + psh + rst + syn + fin + win + chk + urgP;
        all = data.encodePlainText(all,"b") + dat;

        return all.length();
    }
}