package lf.com.android.blackfishdemo.util;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class SignUtils {
    private static final String ALGORITHM = "RSA";
    private static final String SIGN_ALGORIGTHMS = "SHA1WithRSA";
    private static final String SIGN_SHA256RSA_ALGORIGHTMS = "SHA25WithRSA";
    private static final String DEFAULT_CHARSET = "UTF-8";

    private static String getAlgorithms(boolean rsa2) {
        return rsa2 ? SIGN_SHA256RSA_ALGORIGHTMS : SIGN_ALGORIGTHMS;
    }

    public static String sign(String content, String privateKey, boolean rsa2) {
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
        try {
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey privateKey1 = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(getAlgorithms(rsa2));
            signature.initSign(privateKey1);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] singed = signature.sign();

            return Base64.encode(singed);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
