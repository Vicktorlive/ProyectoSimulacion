package SimulacionRed;

import java.sql.*;
import java.util.concurrent.TimeUnit;

public class DataCenter {

    public String dataCenterConnection (String res) throws SQLException, InterruptedException {

        test_data_ip destIP = new test_data_ip();
        String port,ip_adress,exitAdress;

    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/datacenters","root","");
    Statement stmt = conn.createStatement();

    System.out.println("Checking IP_Tables...");

    String strSelect = "Select port,ip_adress from iptables";

    ResultSet rset = stmt.executeQuery(strSelect);

    int rowCount =0;

    while(rset.next()){
        port = rset.getString("port");
        ip_adress = rset.getString("ip_adress");
        ++rowCount;

        System.out.println("Checking port: "+port +" with IP: "+ip_adress);
        TimeUnit.SECONDS.sleep(1);
        System.out.print("***");
        if(ip_adress.equals(destIP.getDestIP())){   //TO DO: Change to verify destination adress
            System.out.println("Found destination server");
            destIP.setExitIP(ip_adress);
            exitAdress = ip_adress;
            break;
        }
    }

        return "Destination IP_Found";  //Make Return exitAdress
    }

}
