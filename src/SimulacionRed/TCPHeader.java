package SimulacionRed;

public class TCPHeader {
    private String sourcePort; // port from source terminal
    private String destinationPort; // port from destination terminal
    private int sequenceNumber; // number on which the sequence is at
    private int ackNumber; // number on which the acknowledgment is at
    private int dataOffset; // TODO: 28/07/17 Investigar los campos sin informacion para ver que defaults se pueden utilizar
    private String reserved;
    private boolean urg, ack, psh, rst, syn, fin; // response bits (ack = acknowledge, syn = sync, fin = end)
    private String window;
    private String checksum; // checksum for further validation
    private String urgentPointer;
    private String options;
    private String padding;
    private Data data; // data being sent

    /**
     * Constructor
     */

    // TODO: 28/07/17 Agregar defaults a como sea necesario
    public TCPHeader(String sourcePort, String destinationPort, int sequenceNumber, int ackNumber, int dataOffset, String reserved, boolean urg, boolean ack, boolean psh, boolean rst, boolean syn, boolean fin, String window, String checksum, String urgentPointer, String options, String padding, Data data) {
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.sequenceNumber = sequenceNumber;
        this.ackNumber = ackNumber;
        this.dataOffset = dataOffset;
        this.reserved = reserved;
        this.urg = urg;
        this.ack = ack;
        this.psh = psh;
        this.rst = rst;
        this.syn = syn;
        this.fin = fin;
        this.window = window;
        this.checksum = checksum;
        this.urgentPointer = urgentPointer;
        this.options = options;
        this.padding = padding;
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

    public boolean isUrg() {
        return urg;
    }

    public void setUrg(boolean urg) {
        this.urg = urg;
    }

    public boolean isAck() {
        return ack;
    }

    public void setAck(boolean ack) {
        this.ack = ack;
    }

    public boolean isPsh() {
        return psh;
    }

    public void setPsh(boolean psh) {
        this.psh = psh;
    }

    public boolean isRst() {
        return rst;
    }

    public void setRst(boolean rst) {
        this.rst = rst;
    }

    public boolean isSyn() {
        return syn;
    }

    public void setSyn(boolean syn) {
        this.syn = syn;
    }

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}