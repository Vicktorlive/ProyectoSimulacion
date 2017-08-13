package SimulacionRed;

import java.sql.*;
import java.util.concurrent.TimeUnit;

public class DataCenter {


    public void routingAndForwarding(IPPacket packet) throws SQLException, InterruptedException {
    resolveRoute(packet);
    forwardRoute(packet);

    }

    private void resolveRoute(IPPacket packet) throws SQLException {

        System.out.println("Resolving route for package...");

        System.out.println("Getting destination IP...");
        System.out.println("\t"+packet.getIpHeader().getDestinationAddress());

        /* GET IP TABLES */
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/datacenters","root","");
        Statement stmt = conn.createStatement();
        System.out.println("[+] Checking IP_Tables...");
        String strSelect = "Select ip_adress from iptables";

        ResultSet rset = stmt.executeQuery(strSelect);

        while(rset.next()) {
            System.out.println("\t ==>|" + rset.getString("ip_adress") + "|");
        }


        System.out.println("--------------------------");
        System.out.println("[-] Found destination address");
    }

    private void forwardRoute(IPPacket packet) throws InterruptedException, SQLException{

        System.out.println("[+] Forwarding package...");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\t Getting source IP"+packet.getIpHeader().getSourceAddress());
        System.out.println("\t Setting destination IP" + packet.getIpHeader().getDestinationAddress());

        System.out.println("\t Validating Routing...");
        TimeUnit.SECONDS.sleep(2);

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/datacenters","root","");
        Statement stmt = conn.createStatement();
        String strSelect = "Select ip_adress from iptables";

        ResultSet rset = stmt.executeQuery(strSelect);
        System.out.println("\t ╔"+packet.getIpHeader().getSourceAddress());
        while(rset.next()) {
            System.out.println("\t ╠" + rset.getString("ip_adress"));
        }
        System.out.println("\t ╚"+ packet.getIpHeader().getDestinationAddress());
        System.out.println("[-] Packet Forwarded");
    }

}
