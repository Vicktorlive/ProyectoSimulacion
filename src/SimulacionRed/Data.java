package SimulacionRed;

public class Data {
    /**
     * Data encode
     */
    public String encodePlainText(String input, String type) {
        if(type.toLowerCase().trim() == "b") {
            return encodeBinary(input);
        } else if (type.toLowerCase().trim() == "h") {
            return encodeHex(input);
        }
        return null;
    }

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

    private String encodeHex(String input) {
        String in = input;
        byte[] bytes = in.getBytes();
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02X ", b));
        }
        return hex.toString();
    }

    /**
     * Data decode
     */

    public String decode(String input, String type) {
        if (type.toLowerCase().trim() == "b") {
            return decodeBinary(input);
        } else if ( type.toLowerCase().trim() == "h") {
            return decodeHex(input);
        }
        return null;
    }

    private String decodeBinary(String binary) {
        String plainText = "";
        char nextChar;
        for(int i = 0; i <= binary.length()-8; i += 9) {
            nextChar = (char)Integer.parseInt(binary.substring(i, i+8), 2);
            plainText += nextChar;
        }
        return plainText;
    }

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
}