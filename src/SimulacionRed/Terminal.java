package SimulacionRed;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.concurrent.TimeUnit;

public class Terminal extends Hardware {
    private String ip;
    private int portOne;
    private int portTwo;
    private int portThree;
    private int seqNumber;
    private int ackNumber;
    private int fin;
    private boolean tcpConnection;

    public Terminal(String mac, String ip) {
        super(mac);
        this.ip = ip;
        this.portOne = 80;
        this.portTwo = 5228;
        this.portThree = 4222;
        this.seqNumber = 0;
        this.ackNumber = 0;
        this.tcpConnection = false;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPortOne() {
        return portOne;
    }

    public void setPortOne(int portOne) {
        this.portOne = portOne;
    }

    public int getPortTwo() {
        return portTwo;
    }

    public void setPortTwo(int portTwo) {
        this.portTwo = portTwo;
    }

    public int getPortThree() {
        return portThree;
    }

    public void setPortThree(int portThree) {
        this.portThree = portThree;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    public int getAckNumber() {
        return ackNumber;
    }

    public void setAckNumber(int ackNumber) {
        this.ackNumber = ackNumber;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public boolean isTcpConnection() {
        return tcpConnection;
    }

    private void setTcpConnection(boolean tcpConnection) {
        this.tcpConnection = tcpConnection;
    }

    public void ARPrequest(Domain sourceDomain, String targetMac, String targetIp) throws InterruptedException {
        ARP arp = new ARP('1',"0800",'6','4','1',this.getMac(),getIp(),targetMac,targetIp);

        arp.arpProcess(arp,sourceDomain);
    }

    /**
     * Method to create IP Packet
     * @param inputData
     * @param destinationAddress
     * @param sourcePort
     * @param destinationPort
     * @param indicators
     */
    public IPPacket createPacket(String inputData, String destinationAddress, int sourcePort, int destinationPort, String indicators) {
        // Creating new Data object
        Data data = new Data();
        // Converting input with Data to binary
        String input = inputData.length() > 0 ? data.encodePlainText(inputData,"b") : "";

        //TCP header with encoded data to be sent
        TCPHeader tcpHeader = new TCPHeader(sourcePort,destinationPort,input);
        // Process indicator bits and change tcp attribute values as needed
        processIndicators(tcpHeader,indicators, true);
        // Datagram size bits (mas o menos)
        int dataSize = tcpHeader.calcDatagramSize();

        //Creating the ip header
        IPHeader ipHeader = new IPHeader(this.getIp(),destinationAddress,dataSize);

        // Packet ready for whatever
        IPPacket packet = new IPPacket(ipHeader,tcpHeader);

        return packet;
    }

    public IPPacket establishTCP(String destinationAddress, int sourcePort, int destinationPort, String indicators) {
        TCPHeader tcpHeader = new TCPHeader(sourcePort,destinationPort);
        processIndicators(tcpHeader,indicators, false);

        int dataSize = tcpHeader.calcDatagramSize();

        IPHeader ipHeader = new IPHeader(this.getIp(),destinationAddress,dataSize);

        IPPacket packet = new IPPacket(ipHeader,tcpHeader);

        return packet;
    }

    /**
     * Process indicatos from createPacket so we can modify this terminals TCP data (sequenceNumber and ackNumber
     * in this implementation)
     * @param tcpHeader
     * @param indicators
     */
    private void processIndicators(TCPHeader tcpHeader, String indicators, boolean isStreaming) {
        /**
         * example indicators = 010000
         * urg = 0
         * ack = 1
         * psh = 0
         * rst = 0
         * syn = 0
         * fin = 0
         */
        int[] indicatorBits = new int[6];
        for (int i = 0; i <indicators.length(); i++) {
            indicatorBits[i] = Integer.parseInt(Character.toString(indicators.charAt(i)));
        }

        if(isStreaming) {
            tcpHeader.setUrg(indicatorBits[0]);
            tcpHeader.setAck(indicatorBits[1]);
            tcpHeader.setPsh(indicatorBits[2]);
            tcpHeader.setRst(indicatorBits[3]);
            tcpHeader.setSyn(indicatorBits[4]);
            tcpHeader.setFin(indicatorBits[5]);
        } else {
            tcpHeader.setUrg(indicatorBits[0]);
            tcpHeader.setAck(indicatorBits[1]);
            tcpHeader.setPsh(indicatorBits[2]);
            tcpHeader.setRst(indicatorBits[3]);
            tcpHeader.setSyn(indicatorBits[4]);
            tcpHeader.setFin(indicatorBits[5]);
            setAckNumber(indicatorBits[1] + getAckNumber());
            setSeqNumber(indicatorBits[4] + getSeqNumber());
            setFin(indicatorBits[5] + getFin());
        }
    }

    /**
     * Method for Terminals to receive incoming packets for further breakdown and processing
     * @param ipPacket
     */
    public void packetReciever(IPPacket ipPacket) throws InterruptedException{
        String border = "--------------- | " + getIp() + " | ---------------";
        System.out.println(border);
        System.out.println("[+] Incoming data ...");
        processIPHeader(ipPacket);
        processIPMessage(ipPacket);
        System.out.println(border);
    }

    private void processIPHeader(IPPacket ipPacket) throws InterruptedException{
        System.out.println("[-] Processing IP Header ...");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\t==> IP Version: " + ipPacket.getIpHeader().getVersion());
        System.out.println("\t==> Header Length: " + ipPacket.getIpHeader().getVersion());
        System.out.println("\t==> Type of Service: " + ipPacket.getIpHeader().getTypeOfService());
        System.out.println("\t==> Size of Datagram: " + ipPacket.getIpHeader().getSizeOfDatagram());
        System.out.println("\t==> ID: " + ipPacket.getIpHeader().getIdentification());
        System.out.println("\t==> Flags: " + ipPacket.getIpHeader().getFlags());
        System.out.println("\t==> Fragment Offset: " + ipPacket.getIpHeader().getFragmentOffset());
        System.out.println("\t==> Time to live: " + ipPacket.getIpHeader().getTimeToLive());
        System.out.println("\t==> Protocol: " + ipPacket.getIpHeader().getProtocol());
        System.out.println("\t==> Checksum: " + ipPacket.getIpHeader().getHeaderChecksum());
        System.out.println("\t==> Sender IP: " + ipPacket.getIpHeader().getSourceAddress());
        System.out.println("\t==> Destination IP: " + ipPacket.getIpHeader().getDestinationAddress());
    }

    private void processIPMessage(IPPacket ipPacket) throws InterruptedException {
        System.out.println("[-] Processing payload ...");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\t==> Source Port: " + ipPacket.getIpMessage().getSourcePort());
        System.out.println("\t==> Destination port: " + ipPacket.getIpMessage().getDestinationPort());
        System.out.println("\t==> Sequence Number: " + ipPacket.getIpMessage().getSequenceNumber());
        System.out.println("\t==> Acknowledgement Number: " + ipPacket.getIpMessage().getAckNumber());
        System.out.println("\t==> Data Offset: " + ipPacket.getIpMessage().getDataOffset());
        System.out.println("\t==> Reserved");
        System.out.println("\t==> URG: " + ipPacket.getIpMessage().getUrg());
        System.out.println("\t==> ACK: " + ipPacket.getIpMessage().getAck());
        System.out.println("\t==> PSH: " + ipPacket.getIpMessage().getPsh());
        System.out.println("\t==> RST: " + ipPacket.getIpMessage().getRst());
        System.out.println("\t==> SYN: " + ipPacket.getIpMessage().getSyn());
        System.out.println("\t==> FIN: " + ipPacket.getIpMessage().getFin());
        System.out.println("\t==> Window: " + ipPacket.getIpMessage().getWindow());
        System.out.println("\t==> Checksum " + ipPacket.getIpMessage().getChecksum());
        System.out.println("\t==> Urgent Pointer: " + ipPacket.getIpMessage().getUrgentPointer());
        System.out.println("\t==> Data: " + ipPacket.getIpMessage().getData());
    }

    public void connectionSwitch() {
        if(isTcpConnection()) {
            setTcpConnection(false);
        } else {
            setTcpConnection(true);
        }
    }

}