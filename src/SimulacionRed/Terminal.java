package SimulacionRed;

public class Terminal extends Hardware {
    private String ip;
    private int portOne;
    private int portTwo;
    private int portThree;

    public Terminal(String mac, String ip) {
        super(mac);
        this.ip = ip;
        this.portOne = 80;
        this.portTwo = 5228;
        this.portThree = 4222;
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

    public void ARPrequest(Domain sourceDomain, String targetMac, String targetIp) {
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
    public void createPacket(String inputData, String destinationAddress, int sourcePort, int destinationPort, String indicators) {
        // Creating new Data object
        Data data = new Data();
        // Converting input with Data to binary
        String input = data.encodePlainText(inputData,"b");

        //TCP header with encoded data to be sent
        TCPHeader tcpHeader = new TCPHeader(sourcePort,destinationPort,input);
        // Process indicator bits and change tcp attribute values as needed
        processIndicators(tcpHeader,indicators);
        // Datagram size bits (mas o menos)
        int dataSize = tcpHeader.calcDatagramSize();

        //Creating the ip header
        IPHeader ipHeader = new IPHeader(this.getIp(),destinationAddress,dataSize);

        // Packet ready for whatever
        IPPacket packet = new IPPacket(ipHeader,tcpHeader);
    }

    /**
     * Process indicatos from createPacket so we can modify this terminals TCP data (sequenceNumber and ackNumber
     * in this implementation)
     * @param tcpHeader
     * @param indicators
     */
    private void processIndicators(TCPHeader tcpHeader, String indicators) {
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
        tcpHeader.setUrg(indicatorBits[0]);
        tcpHeader.setAck(indicatorBits[1]);
        tcpHeader.setPsh(indicatorBits[2]);
        tcpHeader.setRst(indicatorBits[3]);
        tcpHeader.setSyn(indicatorBits[4]);
        tcpHeader.setFin(indicatorBits[5]);
    }

    private void calcDatagramSize(TCPHeader tcpHeader) {


    }

}