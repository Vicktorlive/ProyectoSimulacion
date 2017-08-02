package SimulacionRed;

public class Main {

    public static void main(String[] args) {
        // TODO: 28/07/17 modelar clases para hardware (terminales, routers, switch, etc), IPs, MACs, agrupar por rangos de IP, IP tables, Routing, Forwarding
        // TODO: 28/07/17 Revisar comforme se avanze que getters y setters si se ocupan y eleminar el resto, tratar de encapsular lo mas posible

         Data data = new Data();
         String nombre = "Fernandita la princesita";
         String binario = data.encodePlainText(nombre,"b");
         String hex = data.encodePlainText(nombre,"h");
         System.out.println(nombre + " en binario:\n" + binario);
         System.out.println(nombre + " en hexadecimal\n" + hex);
        System.out.println("Decoding binario = \n" + data.decode(binario,"b"));
        System.out.println("Decoding hex = \n" + data.decode(hex,"h"));
    }
}
