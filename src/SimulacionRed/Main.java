package SimulacionRed;

public class Main {

    public static void main(String[] args) {
        // TODO: 28/07/17 modelar clases para hardware (terminales, routers, switch, etc), IPs, MACs, agrupar por rangos de IP, IP tables, Routing, Forwarding
        // TODO: 28/07/17 Revisar comforme se avanze que getters y setters si se ocupan y eleminar el resto, tratar de encapsular lo mas posible
        Data data = new Data();
        String hello = "Hello in binary!";
        data.setText(hello);
        data.encodePlainText(hello);

        System.out.println(hello);
        System.out.println("Binary:");
        System.out.println(data.getBinary());
        System.out.println("Hex:");
        System.out.println(data.getHex());

        System.out.println("Decode:");
    }
}
