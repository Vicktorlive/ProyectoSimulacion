package SimulacionRed;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.concurrent.TimeUnit;
import java.util.Scanner;
/**
 * Fake mac address generator - http://www.miniwebtool.com/mac-address-generator/
 */
public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Data data = new Data();

    public static void main(String[] args) throws InterruptedException {
        // TODO: 28/07/17 Revisar conforme se avanze que getters y setters si se van a ocupan y eliminar el resto
        // TODO: 3/08/17 IP Tables, Routing, Forwarding
        // TODO: 3/08/17 Logica para conectar todo D=

        /*
        Tres terminales y router por "domain"
         */
        Terminal terminalUno = new Terminal("85-3E-4A-6E-85-F2","192.80.13.2");
        Terminal terminalDos = new Terminal("7C-E4-30-D7-45-CE","192.80.13.3");
        Terminal terminalTres = new Terminal("1C-D3-CE-A2-B5-CD","192.80.13.4");
        Router routerUno = new Router("C8-1E-79-A7-5D-02","192.80.13.1");

        Terminal terminalCuatro = new Terminal("2E-EE-73-63-AD-AE","172.15.32.2");
        Terminal terminalCinco = new Terminal("E8-F9-EB-5B-75-8A","172.15.32.3");
        Terminal terminalSeis = new Terminal("23-41-76-A8-14-AF","172.15.32.4");
        Router routerSeis = new Router("C9-E9-C8-27-F4-43","172.15.32.1");

        /*
        Domain
         */
        int[] ipRange = {192,80,13,1,40};
        Domain domainUno = new Domain(routerUno,terminalUno,terminalDos,terminalTres,ipRange);

        int[] ipRangeTwo = {172,15,32,1,45};
        Domain domainTwo = new Domain(routerSeis,terminalCuatro,terminalCinco,terminalSeis,ipRangeTwo);

        /*
        Use input to determine sender/reciever
         */
        System.out.println("[+] Establishing TCP Connection...");
        terminalUno.ARPrequest(domainUno,routerUno.getMac(),"192.32.120.1");

        IPPacket packet = terminalUno.establishTCP("172.15.32.2",terminalUno.getPortOne(),4222,"000010");

        /*
        RESOLVE ROUTING & FORWARDING
         */

        terminalCuatro.packetReciever(packet); // Recieve first tcp message

        packet = terminalCuatro.establishTCP(packet.getIpHeader().getSourceAddress(),packet.getIpMessage().getDestinationPort(),packet.getIpMessage().getSourcePort(),"010010"); // Respond

        /*
        RESOLVE ROUTING & FORWARDING
         */

        terminalUno.packetReciever(packet); // Recieves reply
        packet = terminalUno.establishTCP(packet.getIpHeader().getSourceAddress(),packet.getIpMessage().getDestinationPort(),packet.getIpMessage().getSourcePort(),"010000"); // Respond

        terminalCinco.packetReciever(packet);

        terminalUno.connectionSwitch();
        terminalCuatro.connectionSwitch();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("[+] CONNECTION ESTABLISHED");
        System.out.println("\t==> TCP/IP");
        System.out.println("\t==> " + terminalUno.getIp() + ":" + packet.getIpMessage().getSourcePort() + " to " + terminalCinco.getIp() + ":" + packet.getIpMessage().getDestinationPort());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


        // De aqui en adelante preguntar por input para enviar de A a B o viceversa

        /*
        RESOLVE ROUTING & FORWARDING // WHILE sending data
         */

        // Al contestar que no se quiere mandar mas datos procesar un paquete con FIN bit = 1

        // ACK de parte de la otra parte

       /* TimeUnit.SECONDS.sleep(1);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("[+] CONNECTION CLOSED");
        System.out.println("\t==> TCP/IP");
        System.out.println("\t==> " + terminalUno.getIp() + ":" + packet.getIpMessage().getSourcePort() + " to " + terminalCinco.getIp() + ":" + packet.getIpMessage().getDestinationPort());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");*/
    }
}
