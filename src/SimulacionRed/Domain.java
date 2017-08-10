package SimulacionRed;

/**
 * Clase para agrupar los diferentes grupos de equipos bajo un rango de IPs
 */
public class Domain {

    private Router router;
    private Terminal terminalOne;
    private Terminal terminalTwo;
    private Terminal terminalThree;
    private int[] ipRange; // [192,20,30,range]

    public Domain(Router router, Terminal terminalOne, Terminal terminalTwo, Terminal terminalThree, int[] ipRange) {
        this.router = router;
        this.terminalOne = terminalOne;
        this.terminalTwo = terminalTwo;
        this.terminalThree = terminalThree;
        this.ipRange = ipRange;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Terminal getTerminalOne() {
        return terminalOne;
    }

    public void setTerminalOne(Terminal terminalOne) {
        this.terminalOne = terminalOne;
    }

    public Terminal getTerminalTwo() {
        return terminalTwo;
    }

    public void setTerminalTwo(Terminal terminalTwo) {
        this.terminalTwo = terminalTwo;
    }

    public Terminal getTerminalThree() {
        return terminalThree;
    }

    public void setTerminalThree(Terminal terminalThree) {
        this.terminalThree = terminalThree;
    }

    public int[] getIpRange() {
        return ipRange;
    }

    public void setIpRange(int[] ipRange) {
        this.ipRange = ipRange;
    }
}
