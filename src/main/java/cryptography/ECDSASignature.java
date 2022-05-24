package cryptography;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class ECDSASignature {
    // This class only has static methods, so we don't need to instantiate it
    private ECDSASignature() { }

    /**
     * Generates key pair using ECDSA
     * @return Generated KeyPair object
     */
    public static KeyPair generateKeyPair() {
        KeyPair keyPair = null;

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            keyGen.initialize(256, random);
            keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyPair;
    }

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
