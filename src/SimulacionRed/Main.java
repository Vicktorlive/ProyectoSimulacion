package SimulacionRed;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, InterruptedException {
        // TODO: 28/07/17 modelar clases para hardware (terminales, routers, switch, etc), IPs, MACs, agrupar por rangos de IP, IP tables, Routing, Forwarding
        // TODO: 28/07/17 Revisar conforme se avanze que getters y setters si se van a ocupan y eliminar el resto

        test_data_ip destIP = new test_data_ip();
       String datain;

        System.out.println("Insert destination IP");

        Scanner keyboard = new Scanner(System.in);

        datain = keyboard.nextLine();

        destIP.setDestIP(datain);

        //////////LLAMAR METODO DATA CENTER PARA HACER PRUEBAS /////

        System.out.println(destIP.getExitIP());

    }
}
