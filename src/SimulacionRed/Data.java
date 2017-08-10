package SimulacionRed;

/**
 * Clase de utileria para la transformacion de cadenas de texto a binario o hexadecimal y viceversa.
 */
public class Data {
    /**
     * Metodo publico para convertir cadenas de texto a binario o hexadecimal
     * @param input String texto que se desea convertir
     * @param type Tipo de conversion a realizar b = binario, h = hexadecimal
     * @return String cadena convertida
     */
    public String encodePlainText(String input, String type) {
        if(type.toLowerCase().trim().equals("b")) {
            return encodeBinary(input);
        } else if (type.toLowerCase().trim().equals("h")) {
            return encodeHex(input);
        }
        return null;
    }

    /**
     * Metodo que convierte cadenas de texto a binario
     * @param input String texto
     * @return String binario
     */
    private String encodeBinary(String input) {
        byte[] bytes = input.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                // bit operators
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }

    /**
     * Metodo que convierte cadenas de texto a hexadecimal
     * @param input String texto
     * @return String hexadecimal
     */
    private String encodeHex(String input) {
        String in = input;
        byte[] bytes = in.getBytes();
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%04X ", b));
        }
        return hex.toString();
    }

    /**
     * Metodo publico para convertir cadenas binarias o hexadecimales a texto
     * @param input String binario o hexadecimal
     * @param type Indica el tipo de texto a convertir - String b = binario, h = hexadecimal
     * @return String texto
     */
    public String decode(String input, String type) {
        if (type.toLowerCase().trim().equals("b")) {
            return decodeBinary(input);
        } else if ( type.toLowerCase().trim().equals("h")) {
            return decodeHex(input);
        }
        return null;
    }

    /**
     * Metodo para convertir cadenas binarias a texto
     * @param binary String binario
     * @return String texto
     */
    private String decodeBinary(String binary) {
        String plainText = "";
        char nextChar;
        for(int i = 0; i <= binary.length()-8; i += 9) {
            nextChar = (char)Integer.parseInt(binary.substring(i, i+8), 2);
            plainText += nextChar;
        }
        return plainText;
    }

    /**
     * Metodo para convertir cadenas hexadecimales a texto
     * @param hex String hexadecimal
     * @return String texto
     */
    private String decodeHex(String hex) {
        String plainText = "";
        char nextChar;
        for (int i = 0; i < hex.length(); i+=3) {
            String str = hex.substring(i, i+2);
            nextChar = (char)Integer.parseInt(str, 16);
            plainText += nextChar;
        }
        return plainText;
    }

    /**
     * Obtener el checksum del TCP Header
     * @param tcpHeader Objeto TCPHeader
     * @return String Checksum
     */
   /* public String checksum(TCPHeader tcpHeader) {
        String hexData = Integer.toString(tcpHeader.getSourcePort()) + Integer.toString(tcpHeader.getDestinationPort()) + Integer.toString(tcpHeader.getSequenceNumber()) + Integer.toString(tcpHeader.getAckNumber()) + Integer.toString(tcpHeader.getDataOffset()) + Integer.toString(tcpHeader.getUrg()) + Integer.toString(tcpHeader.getAck()) + Integer.toString(tcpHeader.getPsh()) + Integer.toString(tcpHeader.getRst()) + Integer.toString(tcpHeader.getSyn()) + Integer.toString(tcpHeader.getFin()) + Integer.toString(tcpHeader.getWindow()) + tcpHeader.getChecksum() + tcpHeader.getUrgentPointer();

        hexData = encodeHex(hexData)  + encodeHex(decodeBinary(tcpHeader.getData()));

        String[] hexVals = hexData.split(" ");
        int acc = 0;
        for (int i = 0; i < hexVals.length - 1; i++) {
            int x = Integer.parseInt(hexVals[i],16);
            int sum = x + acc;
        }
        S

        //String[] grouped = formSixteenBits(hexData);

        String checksum = "";

        /*for (int i = 0; i < grouped.length; i++) {
            String group = grouped[i];
            String complement = "";
            for (int j = 0; j < group.length(); j++) {
                char x = group.charAt(j);
                if(Character.toString(x).equals("1")) {
                    complement = complement + "0";
                } else {
                    complement = complement + "1";
                }
            }
            checksum = checksum + encodeHex(decodeBinary(complement));
        }

        return hexData;
    }*/

    /**
     * Agrupar octetos en grupos de 16 bits
     * @param binary String binario
     * @return Array de grupos de 16 bits
     */
    private String[] formSixteenBits(String binary) {
        String[] splited = binary.split(" ");
        String[] grouped = new String[splited.length / 2];
        if (splited.length % 2 == 0) {
            for (int i = 0,j = 0; i < splited.length; i += 2, j++) {
                grouped[j] = splited[i] + splited[i + 1];
            }
        } else {
            for (int i = 0, j = 0; i < splited.length - 1; i += 2, j++) {
                if (i > splited.length) {
                    grouped[j] = splited[splited.length - 1];
                } else {
                    grouped[j] = splited[i] + splited[i + 1];
                }
            }
        }

        return grouped;
    }
}