package SimulacionRed;

public class Terminal extends Hardware {
    private String ip;

    public Terminal(String mac, String ip) {
        super(mac);
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String ARPrequest(Domain sourceDomain,String targetMac,String targetIp) {
        ARP arp = new ARP('1',"0800",'6','4','1',this.getMac(),getIp(),targetMac,targetIp);

        arp.arpProcess(arp,sourceDomain);


        return null;
    }



}