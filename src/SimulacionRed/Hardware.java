package SimulacionRed;

/**
 * Clase base para lo que sea hardware
 */
public class Hardware {
    private String mac;

    public Hardware(String mac) {
        this.mac = mac;
    }

    public String getMac() {
        return mac;
    }

    /**
     * @Constructor
     * @param mac String MAC de hardware
     */
    public void setMac(String mac) {
        this.mac = mac;
    }
}
