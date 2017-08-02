package SimulacionRed;

public class Data {
    private String text;
    private String binary;
    private String hex;

    /**
     * Constructor
     */
    // TODO: 28/07/17 Metodos para encode / decode

    /**
     * Getters & Setters
     */

    // TODO: 28/07/17 Modificar a como sea necesario
    public String getText() {
        return text;
    }

    public void setText(String text) { this.text = text; }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }


    /**
     * Data encode
     */

    public void encodePlainText(String input) {
        encodeBinary(getText());
        encodeHex(input);
        System.out.println("Data encoded: ");
        System.out.println("Hexadecimal: " + getHex());
        System.out.println("Binary: " + getBinary());
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
        setBinary(binary.toString());
        return binary.toString();
    }

    private void encodeHex(String input) {
        String in = input;
        byte[] bytes = in.getBytes();
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02X ", b));
        }
        setHex(hex.toString());
    }

    /**
     * Data decode
     */

    public void decode(String type, String text) {
        if (type.toLowerCase() == "b") {
            setBinary(text);
            decodeBinary(text);
            encodeHex(text);
        } else if ( type.toLowerCase() == "h") {
            setHex(text);
            decodeHex(text);
            decodeBinary(text);
        }
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
        for (int i = 0; i < hex.length(); i+=2) {
            nextChar = (char)Integer.parseInt(hex.substring(i, i + 8), 2 );
            plainText += nextChar;
        }
        return plainText;
    }
}