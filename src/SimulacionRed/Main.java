package SimulacionRed;

import com.sun.xml.internal.bind.v2.TODO;

/**
 * Fake mac address generator - http://www.miniwebtool.com/mac-address-generator/
 */
public class Main {

    public static void main(String[] args) {
        // TODO: 28/07/17 Revisar conforme se avanze que getters y setters si se van a ocupan y eliminar el resto
        // TODO: 3/08/17 IP Tables, Routing, Forwarding
        // TODO: 3/08/17 Logica para conectar todo D=

        /*
        Tres terminales
         */
        Terminal terminalUno = new Terminal("85-3E-4A-6E-85-F2","192.80.13.2");
        Terminal terminalDos = new Terminal("7C-E4-30-D7-45-CE","192.80.13.3");
        Terminal terminalTres = new Terminal("1C-D3-CE-A2-B5-CD","192.80.13.4");
        Router routerUno = new Router("C8-1E-79-A7-5D-02","192.80.13.1");

        /*
        Domain
         */
        int[] ipRange = {192,80,13,40};
        Domain domainUno = new Domain(routerUno,terminalUno,terminalDos,terminalTres,ipRange);

        terminalUno.ARPrequest(domainUno,routerUno.getMac(),"192.32.120.1");
    }
}
