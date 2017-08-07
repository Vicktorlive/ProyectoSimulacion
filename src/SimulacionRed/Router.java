package SimulacionRed;

public class Router extends Hardware {
    private String ip;

    public Router(String mac, String ip) {
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
