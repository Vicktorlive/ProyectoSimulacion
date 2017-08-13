package SimulacionRed;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.sun.xml.internal.bind.v2.TODO;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.util.zip.DeflaterOutputStream;
/**
 * Fake mac address generator - http://www.miniwebtool.com/mac-address-generator/
 */
public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Data data = new Data();
    /**
     * Se tienen tres terminales y un router por grupo o "domain"
     */
    public static Terminal terminalUno = new Terminal("85-3E-4A-6E-85-F2", "192.80.13.2");
    public static Terminal terminalDos = new Terminal("7C-E4-30-D7-45-CE", "192.80.13.3");
    public static Terminal terminalTres = new Terminal("1C-D3-CE-A2-B5-CD", "192.80.13.4");
    public static Router routerUno = new Router("C8-1E-79-A7-5D-02", "192.80.13.1");

    public static Terminal terminalCuatro = new Terminal("2E-EE-73-63-AD-AE", "172.15.32.2");
    public static Terminal terminalCinco = new Terminal("E8-F9-EB-5B-75-8A", "172.15.32.3");
    public static Terminal terminalSeis = new Terminal("23-41-76-A8-14-AF", "172.15.32.4");
    public static Router routerSeis = new Router("C9-E9-C8-27-F4-43", "172.15.32.1");
    /**
     * Existen dos domains con sus respectivos equipos y se les asigna su rango de IPs
     */
    public static int[] ipRange = {192, 80, 13, 1, 40};
    public static Domain domainUno = new Domain(routerUno, terminalUno, terminalDos, terminalTres, ipRange);

    public static int[] ipRangeTwo = {172, 15, 32, 1, 45};
    public static Domain domainTwo = new Domain(routerSeis, terminalCuatro, terminalCinco, terminalSeis, ipRangeTwo);

    /**
     *
     * @param args
     * @throws InterruptedException
     * @throws SQLException
     *
     * Existen cinco datacenters
     */

    public static  DataCenter sanFransico =new DataCenter();
    public static  DataCenter denver;
    public static  DataCenter wachington;
    public static  DataCenter massachusset;
    public static  DataCenter newYork;

    public static void main(String[] args) throws InterruptedException, SQLException {
        // TODO: 8/08/17 CLEAN UP 
        // TODO: 3/08/17 IP Tables, Routing, Forwarding
        Terminal[] terminales = menu(); // Main menu

        Terminal terminalA = terminales[0]; // Define selected terminals
        Terminal terminalB = terminales[1];

        int portA = portSelection("A", terminalA); // Define selected ports
        int portB = portSelection("B", terminalB);

        scanner.nextLine(); // Clear buffer

        Domain domainA = resolveDomains(terminalA); // Define Domains
        Domain domainB = resolveDomains(terminalB);

        Router routerA = resolveRouters(domainA); // Define routers
        Router routerB = resolveRouters(domainB);

        System.out.println("[*] Informacion de conexion a simular:"); // Display selected information
        System.out.println("\t==> Host A IP: " + terminalA.getIp());
        System.out.println("\t          MAC: " + terminalA.getMac());
        System.out.println("\t         Port: " + portA);
        System.out.println("\t==> Host B IP: " + terminalB.getIp());
        System.out.println("\t          MAC: " + terminalB.getMac());
        System.out.println("\t         Port: " + portB);

        System.out.println("\n");
        System.out.println("================================================");
        System.out.println("------------- Starting Simulation --------------");
        System.out.println("================================================");
        System.out.println("\n");

        TimeUnit.SECONDS.sleep(3);

        System.out.println("[+] Establishing TCP Connection...");

        terminalA.ARPrequest(domainA, "Resolving ...", routerA.getIp());
        IPPacket packet = terminalA.createPacket("[SYN:1]",terminalB.getIp(),portA,portB,"000010"); // First packet to establish TCP connection
        EthernetFrame ethernetFrame = new EthernetFrame("FF:FF:FF:FF:FF:FF",terminalA.getMac(),"0806",packet,1); // Packet goes inside an ethernet frame
        terminalA.setSeqNumber(1); // Set Sequence number
        packetAssemblyMsg();
        ethernetFrameMsg(ethernetFrame);
        /*
        RESOLVE ROUTING & FORWARDING
         */
        sanFransico.routingAndForwarding(packet);
        TimeUnit.SECONDS.sleep(3);


        /*
        ENDS ROUTING & FORWARDING
         */
        terminalB.packetReciever(ethernetFrame); // Receive first tcp message
        terminalB.decodeAndPrintData(packet.getIpMessage().getData()); // Decode binary data
        replyMsg();

        packet = terminalB.createPacket("[ACK:1] [SYN:1]",packet.getIpHeader().getSourceAddress(),packet.getIpMessage().getDestinationPort(),packet.getIpMessage().getSourcePort(),"010010"); // Respond
        ethernetFrame.reFrame(terminalA.getMac(),terminalB.getMac(),ethernetFrame.getEtherType(),packet,ethernetFrame.getFrameCheckSeq());
        terminalB.setAckNumber(1); // Set ACK number
        terminalB.setSeqNumber(1);// Set SEQ number
        packetAssemblyMsg();
        ethernetFrameMsg(ethernetFrame);

        /*
        RESOLVE ROUTING & FORWARDING
         */
        sanFransico.routingAndForwarding(packet);
        TimeUnit.SECONDS.sleep(3);
        /*
        ENDS ROUTING & FORWARDING
         */
        terminalA.packetReciever(ethernetFrame); // Receives reply
        terminalA.decodeAndPrintData(packet.getIpMessage().getData()); // Decode binary data
        replyMsg();

        packet = terminalA.createPacket("[ACK:1]",packet.getIpHeader().getSourceAddress(),packet.getIpMessage().getDestinationPort(),packet.getIpMessage().getSourcePort(),"010000"); // Respond
        ethernetFrame.reFrame(ethernetFrame.getSourceAddress(),ethernetFrame.getDestinationAddress(),ethernetFrame.getEtherType(),packet,ethernetFrame.getFrameCheckSeq());
        terminalA.setAckNumber(1); // Set ACK number
        packetAssemblyMsg();
        ethernetFrameMsg(ethernetFrame);
        /*
        RESOLVE ROUTING & FORWARDING
         */
        sanFransico.routingAndForwarding(packet);
        TimeUnit.SECONDS.sleep(3);
        /*
        ENDS ROUTING & FORWARDING
         */
        terminalB.packetReciever(ethernetFrame); // Receive
        terminalB.decodeAndPrintData(packet.getIpMessage().getData()); // Decode binary data
        String border = "--------------- | " + ethernetFrame.getPayload().getIpHeader().getSourceAddress() + " | ---------------";
        String delimiter = "+++++++++++++++++++++++++++++++++++++++++++++++++++";

        System.out.println(border + "\n" + delimiter);
        System.out.println("\n\n");

        terminalA.connectionSwitch(); // isTCPConnection = true
        terminalB.connectionSwitch(); // isTCPConnection = true

        TimeUnit.SECONDS.sleep(1);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("[+] CONNECTION ESTABLISHED");
        System.out.println("\t==> TCP/IP");
        System.out.println("\t==> " + terminalA.getIp() + ":" + packet.getIpMessage().getSourcePort() + " to " + terminalB.getIp() + ":" + packet.getIpMessage().getDestinationPort());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        /**
         * PROCESO PRINCIPAL DE TCP
         */
        while (terminalA.isTcpConnection() && terminalB.isTcpConnection()) {
            boolean valid = false;
            do {
                System.out.println("Send data over TCP Connection? (S/N)");
                String answer = scanner.nextLine();
                if (answer.toLowerCase().equals("n")) {
                    String input = "[ACK:" + Integer.toString(terminalA.getAckNumber()) + "] [SEQ: " + Integer.toString(terminalA.getSeqNumber()) + "]";
                    // Close connection
                    System.out.println("[+] Closing connection ...");
                    packet = terminalA.createPacket("[FIN:1] " + input,terminalB.getIp(),portA,portB,"010001"); // Send first FIN
                    ethernetFrame.reFrame(terminalB.getMac(),terminalA.getMac(),"0800",packet,1);
                    packetAssemblyMsg();
                    ethernetFrameMsg(ethernetFrame);
                    /*
                    ROUTING FORWARDING
                     */
                    sanFransico.routingAndForwarding(packet);
                    TimeUnit.SECONDS.sleep(3);
                    /*
                    ENDS ROUTING FORWARDING
                     */
                    terminalB.packetReciever(ethernetFrame);
                    TimeUnit.SECONDS.sleep(1);
                    terminalB.decodeAndPrintData(packet.getIpMessage().getData());
                    replyMsg();
                    TimeUnit.SECONDS.sleep(2);
                    input = "[ACK:" + Integer.toString(terminalB.getAckNumber()) + "] [SEQ: " + Integer.toString(terminalB.getSeqNumber()) + "]";
                    packet = terminalB.createPacket("[FIN:1] " + input,terminalA.getIp(),portB,portA,"010001"); // ACK and FIN
                    ethernetFrame.reFrame(ethernetFrame.getSourceAddress(),ethernetFrame.getDestinationAddress(),ethernetFrame.getEtherType(),packet,1);
                    packetAssemblyMsg();
                    ethernetFrameMsg(ethernetFrame);
                    /*
                    ROUTING FORWARDING
                     */
                    sanFransico.routingAndForwarding(packet);
                    TimeUnit.SECONDS.sleep(3);
                    /*
                    ENDS ROUTING FORWARDING
                     */
                    TimeUnit.SECONDS.sleep(1);
                    terminalA.packetReciever(ethernetFrame);
                    terminalA.decodeAndPrintData(packet.getIpMessage().getData());
                    replyMsg();
                    TimeUnit.SECONDS.sleep(2);
                    packetAssemblyMsg();
                    packet = terminalA.createPacket("[ACK:"+(terminalA.getAckNumber() + 1)+"]",terminalB.getIp(),portA,portB,"010000"); // Last ACK
                    ethernetFrame.reFrame(ethernetFrame.getSourceAddress(),ethernetFrame.getDestinationAddress(),ethernetFrame.getEtherType(),packet,1);
                    packetAssemblyMsg();
                    ethernetFrameMsg(ethernetFrame);
                    /*
                    ROUTING FORWARDING
                     */
                    sanFransico.routingAndForwarding(packet);
                    TimeUnit.SECONDS.sleep(3);
                    /*
                    ENDS ROUTING FORWARDING
                     */
                    terminalB.packetReciever(ethernetFrame);
                    TimeUnit.SECONDS.sleep(1);
                    terminalB.decodeAndPrintData(packet.getIpMessage().getData());
                    TimeUnit.SECONDS.sleep(2);
                    // Done with FIN bit sync

                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println("[+] CONNECTION CLOSED");
                    System.out.println("\t==> TCP/IP");
                    System.out.println("\t==> " + terminalA.getIp() + ":" + packet.getIpMessage().getSourcePort() + " to " + terminalB.getIp() + ":" + packet.getIpMessage().getDestinationPort());
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    terminalB.connectionSwitch(); // Connection closed
                    terminalA.connectionSwitch(); // Connection closed
                    valid = true;
                } else if (answer.toLowerCase().equals("s")) {
                    // Process input to send over tcp
                    System.out.println("Input data to send over TCP/IP");
                    String input = scanner.nextLine();
                    packet = terminalA.createPacket(input, terminalB.getIp(), packet.getIpMessage().getSourcePort(), packet.getIpMessage().getDestinationPort(), "010000");
                    ethernetFrame.reFrame(terminalB.getMac(),terminalA.getMac(),"0800",packet,1);
                    packetAssemblyMsg();
                    ethernetFrameMsg(ethernetFrame);
                    /*
                    ROUTING FORWARDING
                     */
                    sanFransico.routingAndForwarding(packet);
                    TimeUnit.SECONDS.sleep(3);
                    /*
                    ENDS ROUTING FORWARDING
                     */
                    terminalB.packetReciever(ethernetFrame);
                    terminalB.setAckNumber((terminalA.getSeqNumber() + input.length()) / Byte.SIZE);
                    terminalB.setSeqNumber((terminalB.getSeqNumber() + input.length()) / Byte.SIZE);
                    terminalB.decodeAndPrintData(packet.getIpMessage().getData());

                    input = "[ACK:" + Integer.toString(terminalB.getAckNumber()) + "] [SEQ:" + Integer.toString(terminalB.getSeqNumber()) + "]";
                    replyMsg();
                    packet = terminalB.createPacket(input,terminalA.getIp(),packet.getIpMessage().getDestinationPort(),packet.getIpMessage().getSourcePort(),"010000");
                    ethernetFrame.reFrame(ethernetFrame.getSourceAddress(),ethernetFrame.getDestinationAddress(),ethernetFrame.getEtherType(),packet,1);
                    packetAssemblyMsg();
                    ethernetFrameMsg(ethernetFrame);
                    /*
                    ROUTING FORWARDING
                     */

                    sanFransico.routingAndForwarding(packet);
                    TimeUnit.SECONDS.sleep(3);

                    /*
                    ENDS ROUTING FORWARDING
                     */
                    sanFransico.routingAndForwarding(packet);
                    TimeUnit.SECONDS.sleep(3);
                    /*
                    Receives reply
                     */
                    terminalA.packetReciever(ethernetFrame);
                    terminalA.decodeAndPrintData(packet.getIpMessage().getData());
                    valid = true;
                } else {
                    System.out.println("Invalid Input");
                    valid = false;
                }
            } while (!valid);
        }
    }

    /**
     * Menu princpial
     * @return Array con las terminales que eligio el usuario
     */
    private static Terminal[] menu() {
        int sender = 0;
        int reciever = 0;
        System.out.println("******************************************************");
        System.out.println("*                   Simulacion de Red                *");
        System.out.println("*            Envio de datos mediante TCP/IP          *");
        System.out.println("******************************************************\n");

        System.out.println("[*] Seleccione el equipo del cual desea enviar datos");
        System.out.println("-----------------------------------------------------");
        System.out.println("|        Grupo A          |         Grupo B         |");
        System.out.println("-----------------------------------------------------");
        System.out.println("|  1) Terminal Uno        |  4) Terminal Cuatro     |");
        System.out.println("|  2) Terminal Dos        |  5) Terminal Cinco      |");
        System.out.println("|  3) Terminal Tres       |  6) Terminal Seis       |");
        System.out.println("-----------------------------------------------------");

        sender = scanner.nextInt();

        if (sender >= 1 && sender <= 3) {
            boolean valid = false;
            do {
                System.out.println("[*] Seleccione el equipo al cual desea enviar datos");
                System.out.println("---------------------------");
                System.out.println("|         Grupo B         |");
                System.out.println("---------------------------");
                System.out.println("|  4) Terminal Cuatro     |");
                System.out.println("|  5) Terminal Cinco      |");
                System.out.println("|  6) Terminal Seis       |");
                System.out.println("---------------------------");
                reciever = scanner.nextInt();
                if(reciever < 4 || reciever > 6) {
                    System.out.println("[*] ERROR - OPCION NO VALIDA");
                } else {
                    valid = true;
                }
            } while (!valid);

        } else if (sender >= 4 && sender <= 6) {
            boolean valid = false;
            do {
                System.out.println("[*] Seleccione el equipo al cual desea enviar datos");
                System.out.println("---------------------------");
                System.out.println("|         Grupo A          |");
                System.out.println("---------------------------");
                System.out.println("|  1) Terminal Uno         |");
                System.out.println("|  2) Terminal Dos         |");
                System.out.println("|  3) Terminal Tres        |");
                System.out.println("----------------------------");
                reciever = scanner.nextInt();
                if(reciever < 1 || reciever > 3) {
                    System.out.println("[*] ERROR - OPCION NO VALIDA");
                } else {
                    valid = true;
                }
            } while (!valid);

        }

        Terminal[] terminales = new Terminal[2];
        switch (sender) {
            case 1:
                terminales[0] = terminalUno;
                break;
            case 2:
                terminales[0] = terminalDos;
                break;
            case 3:
                terminales[0] = terminalTres;
                break;
            case 4:
                terminales[0] = terminalCuatro;
                break;
            case 5:
                terminales[0] = terminalCinco;
                break;
            case 6:
                terminales[0] = terminalSeis;
                break;
        }

        switch (reciever) {
            case 1:
                terminales[1] = terminalUno;
                break;
            case 2:
                terminales[1] = terminalDos;
                break;
            case 3:
                terminales[1] = terminalTres;
                break;
            case 4:
                terminales[1] = terminalCuatro;
                break;
            case 5:
                terminales[1] = terminalCinco;
                break;
            case 6:
                terminales[1] = terminalSeis;
                break;
        }

        return terminales;
    }

    /**
     * Menu para la seleccion de puertos de ambos hosts
     * @param host A o B
     * @param terminal
     * @return El puerto que usara la terminal
     */
    private static int portSelection(String host, Terminal terminal) {
        System.out.println("[*] Seleccione el puerto de Host" + host.toUpperCase() +": ");
        System.out.println(" 1) 80");
        System.out.println(" 2) 5228");
        System.out.println(" 3) 4222");
        int answer = scanner.nextInt();

        switch (answer) {
            case 1:
                return terminal.getPortOne();
            case 2:
                return terminal.getPortTwo();
            case 3:
                return terminal.getPortThree();
        }
        return 0;
    }

    /**
     * Asigna los domains A y B  dependiendo de la seleccion de los usuarios
     * @param terminal
     * @return domain
     */
    private static Domain resolveDomains(Terminal terminal) {
        String ip = terminal.getIp();
        String[] slicedIP = ip.split("\\.");
        Domain domain;
        if(slicedIP[0].equals("192")) {
            domain = domainUno;
        } else {
            domain = domainTwo;
        }
        return domain;
    }

    /**
     * Asigna los routers A y B dependiendo de la seleccion de los usuarios
     * @param domain
     * @return Objeto Router
     */
    private static Router resolveRouters(Domain domain) {
        Router domainRouter;
        if (domain.getIpRange()[0] == 192) {
            domainRouter = routerUno;
        } else {
            domainRouter = routerSeis;
        }
        return domainRouter;
    }

    /**
     * Mensaje para cuando esta creando un paquete
     * @throws InterruptedException Timeout
     */
    private static void packetAssemblyMsg() throws InterruptedException{
        System.out.println("[+] Encoding ...");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("[+] Assembling IP Packet ...");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("[+] Passing packet to Ethernet Frame as payload ...");
    }

    /**
     * Mensaje para indicar que se respondera al paquete recivido
     * @throws InterruptedException Timeout
     */
    private static void replyMsg() throws InterruptedException {
        System.out.println("[+] Preparing reply...");
        TimeUnit.SECONDS.sleep(1);
    }

    /**
     * Mensaje para indicar la creacion de un Ethernet Frame
     * @param ethernetFrame Objeto EthernetFrame
     * @throws InterruptedException Timeouts
     */
    private static void ethernetFrameMsg(EthernetFrame ethernetFrame) throws InterruptedException{
        System.out.println("[+] Assembling Ethernet Frame ...");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\t==> Preamble: " + ethernetFrame.getPreamble());
        System.out.println("\t==> Destination Address: " + ethernetFrame.getDestinationAddress());
        System.out.println("\t==> Source Address: " + ethernetFrame.getSourceAddress());
        System.out.println("\t==> Ether Type: " + ethernetFrame.getEtherType());
        String payload = ethernetFrame.getEtherType().equals("0806") ? "ARP" : "IP Packet";
        System.out.println("\t==> Payload: " + payload);
        System.out.println("\t==> Frame Check Sequence: " + ethernetFrame.getFrameCheckSeq());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("[+] Sending Packet ...");
        String border = "--------------- | " + ethernetFrame.getPayload().getIpHeader().getSourceAddress() + " | ---------------";
        String delimiter = "+++++++++++++++++++++++++++++++++++++++++++++++++++";

        System.out.println(border + "\n" + delimiter);
        System.out.println("\n\n");
    }
}

