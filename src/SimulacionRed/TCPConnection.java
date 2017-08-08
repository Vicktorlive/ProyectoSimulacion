package SimulacionRed;

public class TCPConnection {
    private int seqNumber;
    private int ackNumber;
    private int finNumber;
    private Terminal terminalOne;
    private Terminal terminalTwo;

    public TCPConnection(int seqNumber, int ackNumber, int finNumber, Terminal terminalOne, Terminal terminalTwo) {
        this.seqNumber = seqNumber;
        this.ackNumber = ackNumber;
        this.finNumber = finNumber;
        this.terminalOne = terminalOne;
        this.terminalTwo = terminalTwo;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    public int getAckNumber() {
        return ackNumber;
    }

    public void setAckNumber(int ackNumber) {
        this.ackNumber = ackNumber;
    }

    public int getFinNumber() {
        return finNumber;
    }

    public void setFinNumber(int finNumber) {
        this.finNumber = finNumber;
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
}
