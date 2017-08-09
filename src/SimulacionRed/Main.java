package SimulacionRed;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.util.zip.DeflaterOutputStream;

/**
 * Fake mac address generator - http://www.miniwebtool.com/mac-address-generator/
 */
public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Data data = new Data();

    public static Terminal terminalUno = new Terminal("85-3E-4A-6E-85-F2", "192.80.13.2");
    public static Terminal terminalDos = new Terminal("7C-E4-30-D7-45-CE", "192.80.13.3");
    public static Terminal terminalTres = new Terminal("1C-D3-CE-A2-B5-CD", "192.80.13.4");
    public static Router routerUno = new Router("C8-1E-79-A7-5D-02", "192.80.13.1");

    public static Terminal terminalCuatro = new Terminal("2E-EE-73-63-AD-AE", "172.15.32.2");
    public static Terminal terminalCinco = new Terminal("E8-F9-EB-5B-75-8A", "172.15.32.3");
    public static Terminal terminalSeis = new Terminal("23-41-76-A8-14-AF", "172.15.32.4");
    public static Router routerSeis = new Router("C9-E9-C8-27-F4-43", "172.15.32.1");

    public static int[] ipRange = {192, 80, 13, 1, 40};
    public static Domain domainUno = new Domain(routerUno, terminalUno, terminalDos, terminalTres, ipRange);

    public static int[] ipRangeTwo = {172, 15, 32, 1, 45};
    public static Domain domainTwo = new Domain(routerSeis, terminalCuatro, terminalCinco, terminalSeis, ipRangeTwo);


    public static void main(String[] args) throws InterruptedException {
        // TODO: 8/08/17 CLEAN UP 
        // TODO: 3/08/17 IP Tables, Routing, Forwarding
        Terminal[] terminales = menu();

        Terminal terminalA = terminales[0];
        Terminal terminalB = terminales[1];

        int portA = portSelection("A", terminalA);
        int portB = portSelection("B", terminalB);

        scanner.nextLine(); // Clear buffer

        Domain domainA = resolveDomains(terminalA);
        Domain domainB = resolveDomains(terminalB);

        Router routerA = resolveRouters(domainA);
        Router routerB = resolveRouters(domainB);

        System.out.println("[*] Informacion de conexion a simular:");
        System.out.println("\t==> Host A IP: " + terminalA.getIp());
        System.out.println("\t          MAC: " + terminalA.getMac());
        System.out.println("\t         Port: " + portA);
        System.out.println("\t==> Host B IP: " + terminalB.getIp());
        System.out.println("\t          MAC: " + terminalB.getMac());
        System.out.println("\t         Port: " + portB);

        System.out.println("\n");
        System.out.println("------------- Starting Simulation --------------");
        System.out.println("\n");

        TimeUnit.SECONDS.sleep(3);
        /*
        Use input to determine sender/reciever
         */
        System.out.println("[+] Establishing TCP Connection...");
        terminalA.ARPrequest(domainA, routerA.getMac(), routerA.getIp());

        IPPacket packet = terminalA.establishTCP(terminalB.getIp(), portA, portB, "000010");

        /*
        RESOLVE ROUTING & FORWARDING
         */

        terminalB.packetReciever(packet); // Recieve first tcp message

        packet = terminalB.establishTCP(packet.getIpHeader().getSourceAddress(), packet.getIpMessage().getDestinationPort(), packet.getIpMessage().getSourcePort(), "010010"); // Respond

        /*
        RESOLVE ROUTING & FORWARDING
         */

        terminalA.packetReciever(packet); // Recieves reply
        packet = terminalA.establishTCP(packet.getIpHeader().getSourceAddress(), packet.getIpMessage().getDestinationPort(), packet.getIpMessage().getSourcePort(), "010000"); // Respond

        terminalB.packetReciever(packet);

        terminalA.connectionSwitch(); // isTCPConnected = true
        terminalB.connectionSwitch(); // isTCPConnected = true

        TimeUnit.SECONDS.sleep(1);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("[+] CONNECTION ESTABLISHED");
        System.out.println("\t==> TCP/IP");
        System.out.println("\t==> " + terminalA.getIp() + ":" + packet.getIpMessage().getSourcePort() + " to " + terminalB.getIp() + ":" + packet.getIpMessage().getDestinationPort());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


        // De aqui en adelante preguntar por input para enviar de A a B o viceversa
        while (terminalA.isTcpConnection() && terminalB.isTcpConnection()) {
            boolean valid = false;
            do {
                System.out.println("Send data over TCP Connection? (S/N)");
                String answer = scanner.nextLine();
                if (answer.toLowerCase().equals("n")) {
                    // Close connection
                    packet = terminalA.createPacket("[FIN:1] [ACK:"+terminalA.getAckNumber()+"] [SEQ:"+terminalA.getSeqNumber()+"]",terminalB.getIp(),portA,portB,"010001");

                    terminalB.packetReciever(packet);
                    TimeUnit.SECONDS.sleep(1);
                    terminalB.decodeAndPrintData(packet.getIpMessage().getData());
                    TimeUnit.SECONDS.sleep(2);
                    packet = terminalB.createPacket("[FIN:1] [ACK:"+ terminalB.getAckNumber() + "] [SEQ:" + terminalB.getSeqNumber() + "]",terminalA.getIp(),portB,portA,"010001");

                    TimeUnit.SECONDS.sleep(1);
                    terminalA.packetReciever(packet);
                    terminalA.decodeAndPrintData(packet.getIpMessage().getData());
                    TimeUnit.SECONDS.sleep(2);
                    packet = terminalA.createPacket("[ACK:"+(terminalA.getAckNumber() + 1)+"]",terminalB.getIp(),portA,portB,"010000");

                    terminalB.packetReciever(packet);
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
                    System.out.println("[+] Encoding ...");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("[+] Data encoded");
                    System.out.println("[+] Assembling IP Packet");
                    TimeUnit.SECONDS.sleep(1);
                    packet = terminalA.createPacket(input, terminalB.getIp(), packet.getIpMessage().getSourcePort(), packet.getIpMessage().getDestinationPort(), "010000");
                    System.out.println("[+] Sending IP Packet...");
                    /*
                    ROUTING FORWARDING
                     */



                    /*
                    ENDS ROUTING FORWADING
                     */
                    terminalB.packetReciever(packet);
                    terminalB.setAckNumber((terminalA.getSeqNumber() + input.length()) / Byte.SIZE);
                    terminalB.setSeqNumber((terminalB.getSeqNumber() + input.length()) / Byte.SIZE);
                    terminalB.decodeAndPrintData(packet.getIpMessage().getData());

                    /*
                    REPLY WITH ACK AND SEQ
                     */
                    input = "ACK: " + Integer.toString(terminalB.getAckNumber()) + " SEQ: " + Integer.toString(terminalB.getSeqNumber());
                    System.out.println("[+] Preparing reply...");
                    packet = terminalB.createPacket(input,terminalA.getIp(),packet.getIpMessage().getDestinationPort(),packet.getIpMessage().getSourcePort(),"010000");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("[+] Sending reply");
                    TimeUnit.SECONDS.sleep(2);
                    /*
                    ROUTING FORWARDING
                     */



                    /*
                    ENDS ROUTING FORWADING
                     */

                    /*
                    Recieves reply
                     */
                    terminalA.packetReciever(packet);
                    terminalA.decodeAndPrintData(packet.getIpMessage().getData());

                    valid = true;
                } else {
                    System.out.println("Invalid Input");
                    valid = false;
                }
            } while (!valid);
        }
    }

    // TODO: 7/08/17 Metodos para enviar datos y cerrar conexion para limpiar main

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
                System.out.println("|  4) Terminal Uno         |");
                System.out.println("|  5) Terminal Dos         |");
                System.out.println("|  6) Terminal Tres        |");
                System.out.println("----------------------------");
                reciever = scanner.nextInt();
                if(reciever < 4 || reciever > 6) {
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

    private static Router resolveRouters(Domain domain) {
        Router domainRouter;
        if (domain.getIpRange()[0] == 192) {
            domainRouter = routerUno;
        } else {
            domainRouter = routerSeis;
        }
        return domainRouter;
    }
}

