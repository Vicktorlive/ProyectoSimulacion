package SimulacionRed;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

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
    
    public void arpProcess(ARP arp,Domain sourceDomain) throws InterruptedException {
        // TODO: 3/08/17 use timeouts here
        arpStart(arp);

        String border = "";
        String whoIs = "\t==>" + getSenderProtocolAddress() + " - Who is " + getTargetHardwareAddress();
        String iAm = "\t<== " + getTargetProtocolAddress() + " - I am " + getTargetHardwareAddress();

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        arpBroadcast(sourceDomain);
        System.out.println(whoIs);

        timeOut(2);
        System.out.println(iAm);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private void arpStart(ARP arp) throws InterruptedException {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("[+] Preparing ARP request...");

        timeOut(1);

        System.out.println("\t==> HW Address Type: " + arp.getHwAddressType());
        System.out.println("\t==> Protocol Address Type: " + arp.getProtocolAddressType());
        System.out.println("\t==> HW Address Length: " + arp.getHwAddressLength());
        System.out.println("\t==> Protocol Address Length: " + arp.getProtocolAddressLength());
        System.out.println("\t==> OP Code: " + arp.getOpCode());
        System.out.println("\t==> Sender Hardware Address: " + arp.getSenderHardwareAddress());
        System.out.println("\t==> Sender Protocol Address: " + arp.getSenderProtocolAddress());
        System.out.println("\t==> Target Hardware Address: " + arp.getTargetHardwareAddress());
        System.out.println("\t==> Target Protocol Address: " + arp.getTargetProtocolAddress());
    }
    
    private void arpBroadcast(Domain sourceDomain) throws InterruptedException {
        String ipRange = "";

        for (int i = 0  ; i < sourceDomain.getIpRange().length; i++) {
            if(i == 3) {
                ipRange += Integer.toString(sourceDomain.getIpRange()[i]) + "/";
            } else {
                ipRange += Integer.toString(sourceDomain.getIpRange()[i]) + ".";
            }

        }

        timeOut(2);
        System.out.println("[+] Broadcasting in " + ipRange);
    }

    private void timeOut(int timeout) throws InterruptedException{
        TimeUnit.SECONDS.sleep(timeout);
    }
}