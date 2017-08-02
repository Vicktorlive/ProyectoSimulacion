package SimulacionRed;

public class ARP {
    private char hwAddressType; // 1 = ethernet
    private String protocolAddressType; // 0800 = ip
    private char hwAddressLength; // mac = 6 bytes
    private char protocolAddressLength; // ip = 4 bytes
    private char opCode; // 1 = broadcast - 2 = reply
    private String senderHardwareAddress; // sender mac
    private String senderProtocolAddress; // sender ip
    private String targetHardwareAddress; // target mac
    private String targetProtocolAddress; // target ip

    /**
     * Constructor
     */

    // TODO: 28/07/17 Agregar defaults a como sea necesario
    public ARP(char hwAddressType, String protocolAddressType, char hwAddressLength, char protocolAddressLength, char opCode, String senderHardwareAddress, String senderProtocolAddress, String targetHardwareAddress, String targetProtocolAddress) {
        this.hwAddressType = hwAddressType;
        this.protocolAddressType = protocolAddressType;
        this.hwAddressLength = hwAddressLength;
        this.protocolAddressLength = protocolAddressLength;
        this.opCode = opCode;
        this.senderHardwareAddress = senderHardwareAddress;
        this.senderProtocolAddress = senderProtocolAddress;
        this.targetHardwareAddress = targetHardwareAddress;
        this.targetProtocolAddress = targetProtocolAddress;
    }

    /**
     * Getters & Setters
     */

    public char getHwAddressType() {
        return hwAddressType;
    }

    public void setHwAddressType(char hwAddressType) {
        this.hwAddressType = hwAddressType;
    }

    public String getProtocolAddressType() {
        return protocolAddressType;
    }

    public void setProtocolAddressType(String protocolAddressType) {
        this.protocolAddressType = protocolAddressType;
    }

    public char getHwAddressLength() {
        return hwAddressLength;
    }

    public void setHwAddressLength(char hwAddressLength) {
        this.hwAddressLength = hwAddressLength;
    }

    public char getProtocolAddressLength() {
        return protocolAddressLength;
    }

    public void setProtocolAddressLength(char protocolAddressLength) {
        this.protocolAddressLength = protocolAddressLength;
    }

    public char getOpCode() {
        return opCode;
    }

    public void setOpCode(char opCode) {
        this.opCode = opCode;
    }

    public String getSenderHardwareAddress() {
        return senderHardwareAddress;
    }

    public void setSenderHardwareAddress(String senderHardwareAddress) {
        this.senderHardwareAddress = senderHardwareAddress;
    }

    public String getSenderProtocolAddress() {
        return senderProtocolAddress;
    }

    public void setSenderProtocolAddress(String senderProtocolAddress) {
        this.senderProtocolAddress = senderProtocolAddress;
    }

    public String getTargetHardwareAddress() {
        return targetHardwareAddress;
    }

    public void setTargetHardwareAddress(String targetHardwareAddress) {
        this.targetHardwareAddress = targetHardwareAddress;
    }

    public String getTargetProtocolAddress() {
        return targetProtocolAddress;
    }

    public void setTargetProtocolAddress(String targetProtocolAddress) {
        this.targetProtocolAddress = targetProtocolAddress;
    }
}