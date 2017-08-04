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

}