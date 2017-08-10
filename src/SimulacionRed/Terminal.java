package SimulacionRed;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.concurrent.TimeUnit;

/**
 * Clase para instanciar las terminales que formaran parte de la red a simular, se extiende de Hardware para
 * tener una direccion MAC por terminal
 */
public class Terminal extends Hardware {
    private String ip;
    private int portOne;
    private int portTwo;
    private int portThree;
    private int seqNumber;
    private int ackNumber;
    private int fin;
    private boolean tcpConnection;

    /**
     *
     * @param mac
     * @param ip
     */
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
        //String checksum = data.checksum(tcpHeader);
        //tcpHeader.setChecksum(checksum);

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
     * Metodo para procesar y asignar los indicadores (URG, ACK, PSH, RST, SYN, FIN)que se usen al crear un paquete
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
     * Metodo por el cual la terminal recibira el paquete enviado desde otra terminal
     * @param ethernetFrame Objeto EthernetFrame
     */
    public void packetReciever(EthernetFrame ethernetFrame) throws InterruptedException{
        String delimiter = "+++++++++++++++++++++++++++++++++++++++++++++++++++";
           String border = "--------------- | " + getIp() + " | ---------------";
        System.out.println(delimiter + "\n" + border);
        System.out.println("[+] Incoming data ...");
        processFrame(ethernetFrame);

        processIPHeader(ethernetFrame.getPayload().getIpHeader());
        processIPMessage(ethernetFrame.getPayload().getIpMessage());
        // System.out.println(border);
    }

    /**
     * Metodo para convertir los datos recibidos en binario o hexadecimal a texto
     * @param data
     * @throws InterruptedException
     */
    public void decodeAndPrintData(String data) throws InterruptedException {
        String border = "-------------- | Decoding Data | ---------------";
        TimeUnit.SECONDS.sleep((long) 1.5);
        Data dataDecode = new Data();
        System.out.println(border);
        System.out.println("[*] Decoding Data...");
        System.out.println("> " + dataDecode.decode(data,"b"));
        System.out.println("------------------------------------------------");
    }

    /**
     * Metodo para desglozar y mostrar datos del ethernet frame
     * @param ethernetFrame Objeto Ethernet Frame
     * @throws InterruptedException Timeouts
     */
    private void processFrame(EthernetFrame ethernetFrame) throws InterruptedException{
        System.out.println("[+] Processing Ethernet Frame ...");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\t==> Preamble: " + ethernetFrame.getPreamble());
        System.out.println("\t==> Destination Address: " + ethernetFrame.getDestinationAddress());
        System.out.println("\t==> Source Address: " + ethernetFrame.getSourceAddress());
        System.out.println("\t==> Ether Type: " + ethernetFrame.getEtherType());
        String payload = ethernetFrame.getEtherType().equals("0806") ? "ARP" : "IP Packet";
        System.out.println("\t==> Payload: " + payload);
        System.out.println("\t==> Frame Check Sequence: " + ethernetFrame.getFrameCheckSeq());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("[+] Unpacking payload ...");
        TimeUnit.SECONDS.sleep(2);
    }

    /**
     * Metodo para mostrar los datos que van dentro del IP Header del paquete
     * @param ipHeader Objeto IPHeader
     * @throws InterruptedException Timeouts
     */
    private void processIPHeader(IPHeader ipHeader) throws InterruptedException{
        System.out.println("[+] Processing IP Header ...");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\t==> IP Version: " + ipHeader.getVersion());
        System.out.println("\t==> Header Length: " + ipHeader.getVersion());
        System.out.println("\t==> Type of Service: " + ipHeader.getTypeOfService());
        System.out.println("\t==> Size of Datagram: " + ipHeader.getSizeOfDatagram());
        System.out.println("\t==> ID: " + ipHeader.getIdentification());
        System.out.println("\t==> Flags: " + ipHeader.getFlags());
        System.out.println("\t==> Fragment Offset: " + ipHeader.getFragmentOffset());
        System.out.println("\t==> Time to live: " + ipHeader.getTimeToLive());
        System.out.println("\t==> Protocol: " + ipHeader.getProtocol());
        System.out.println("\t==> Checksum: " + ipHeader.getHeaderChecksum());
        System.out.println("\t==> Sender IP: " + ipHeader.getSourceAddress());
        System.out.println("\t==> Destination IP: " + ipHeader.getDestinationAddress());
    }

    /**
     * Metodo para mostrar los datos que contiene el Payload del paquete
     * @param tcpHeader Objeto TCPHeader
     * @throws InterruptedException Timeout
     */
    private void processIPMessage(TCPHeader tcpHeader) throws InterruptedException {
        System.out.println("[+] Processing payload ...");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\t==> Source Port: " + tcpHeader.getSourcePort());
        System.out.println("\t==> Destination port: " + tcpHeader.getDestinationPort());
        System.out.println("\t==> Sequence Number: " + tcpHeader.getSequenceNumber());
        System.out.println("\t==> Acknowledgement Number: " + tcpHeader.getAckNumber());
        System.out.println("\t==> Data Offset: " + tcpHeader.getDataOffset());
        System.out.println("\t==> Reserved");
        System.out.println("\t==> URG: " + tcpHeader.getUrg());
        System.out.println("\t==> ACK: " + tcpHeader.getAck());
        System.out.println("\t==> PSH: " + tcpHeader.getPsh());
        System.out.println("\t==> RST: " + tcpHeader.getRst());
        System.out.println("\t==> SYN: " + tcpHeader.getSyn());
        System.out.println("\t==> FIN: " + tcpHeader.getFin());
        System.out.println("\t==> Window: " + tcpHeader.getWindow());
        System.out.println("\t==> Checksum " + tcpHeader.getChecksum());
        System.out.println("\t==> Urgent Pointer: " + tcpHeader.getUrgentPointer());
        System.out.println("\t==> Data: " + tcpHeader.getData());
    }

    /**
     * Metodo para cambiar el valor de si la terminal se encuentra en una conexion (true/false)
     */
    public void connectionSwitch() {
        if(isTcpConnection()) {
            setTcpConnection(false);
        } else {
            setTcpConnection(true);
        }
    }

}