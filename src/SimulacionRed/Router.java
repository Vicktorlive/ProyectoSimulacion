package SimulacionRed;

/**
 * Clase que usamos para instanciar routers que hereda la propiedad de MAC de Hardware
 */
public class Router extends Hardware {
    private String ip;

    /**
     * @Constructor
     * @param mac String MAC de router
     * @param ip String IP de router
     */
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
