package cryptography;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class ECDSASignature {
    // This class only has static methods, so we don't need to instantiate it
    private ECDSASignature() { }


    /**
     * Signs String data using ECDSA
     */
    public static byte[] sign(PrivateKey privateKey, String message) throws Exception {
        // Create Signature object and initialize it with the private key
        Signature dsa = Signature.getInstance("SHA1withECDSA");
        dsa.initSign(privateKey);

        // Sign the message
        byte[] byteMessage = message.getBytes(StandardCharsets.UTF_8);
        dsa.update(byteMessage);

        // Return the result
        return dsa.sign();
    }

    /**
     * Verify ECDSA signature
     */
    public static boolean verifySignature(byte[] sig, String message, PublicKey publicKey) throws Exception {
        // Create Signature Verification object and initialize it with the public key
        Signature dsa = Signature.getInstance("SHA1withECDSA");
        dsa.initVerify(publicKey);

        // Sign the message
        byte[] byteMessage = message.getBytes(StandardCharsets.UTF_8);
        dsa.update(byteMessage);

        return dsa.verify(sig);
    }
}
