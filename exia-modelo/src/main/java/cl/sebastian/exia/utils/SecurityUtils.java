package cl.sebastian.exia.utils;

import java.io.Serializable;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina
 */
public class SecurityUtils implements Serializable {

    private static final String ALGORITMO = "Blowfish";
    private static final String MODO_OPERACION = "Blowfish/CBC/PKCS5Padding";
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    private SecurityUtils() {
        throw new AssertionError();
    }

    public static String hashSha256(String text) {
        String hash = StringUtils.EMPTY;
        try {
            hash = DigestUtils.sha256Hex(text);
        } catch (Exception e) {
            logger.error("Error al hashear en Sha256: {}", e.toString());
        }
        return hash;
    }

    public static String hashSha1(String text) {
        String hash = StringUtils.EMPTY;
        try {
            hash = DigestUtils.sha1Hex(text);
        } catch (Exception e) {
            logger.error("Error al hashear en Sha1: {}", e.toString());
        }
        return hash;
    }

    public static String cifrar(String llave, String mensaje) {
        String resultado = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(llave) && StringUtils.isNotEmpty(mensaje)) {
                // Mejorar la versión adjuntando el vector de inicialización
                byte[] biv = new byte[8];
                Arrays.fill(biv, (byte) 0);
                IvParameterSpec iv = new IvParameterSpec(biv);

                byte[] datos = llave.getBytes("UTF-8");
                SecretKeySpec ks = new SecretKeySpec(datos, ALGORITMO);
                Cipher cipher = Cipher.getInstance(MODO_OPERACION);
                cipher.init(Cipher.ENCRYPT_MODE, ks, iv);
                byte[] salida = cipher.doFinal(mensaje.getBytes("UTF-8"));
                resultado = Hex.encodeHexString(salida);
            }
        } catch (Exception e) {
            resultado = "";
            logger.error("Error al cifrar texto: {}", e.toString());
            logger.error("Error al cifrar texto", e);
        }
        return resultado;
    }

    public static String descifrar(String llave, String mensaje) {
        String resultado = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(llave) && StringUtils.isNotEmpty(mensaje)) {
                // Mejorar la versión adjuntando el vector de inicialización
                byte[] biv = new byte[8];
                Arrays.fill(biv, (byte) 0);
                IvParameterSpec iv = new IvParameterSpec(biv);

                byte[] datos = llave.getBytes("UTF-8");
                SecretKeySpec ks = new SecretKeySpec(datos, ALGORITMO);
                Cipher cipher = Cipher.getInstance(MODO_OPERACION);
                cipher.init(Cipher.DECRYPT_MODE, ks, iv);
                byte[] salida = cipher.doFinal(Hex.decodeHex(mensaje.toCharArray()));
                resultado = new String(salida);
            }
        } catch (Exception e) {
            resultado = "";
            logger.error("Error al descifrar texto: {}", e.toString());
            logger.error("Error al descifrar texto", e);
        }
        return resultado;
    }

    public static String cifrar(String publico, String privado, String mensaje) {
        String resultado = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(publico) && StringUtils.isNotBlank(privado) && StringUtils.isNotEmpty(mensaje)) {
                byte[] biv = Arrays.copyOf(publico.getBytes("UTF-8"), 8);
                IvParameterSpec iv = new IvParameterSpec(biv);

                byte[] datos = privado.getBytes("UTF-8");
                SecretKeySpec ks = new SecretKeySpec(datos, ALGORITMO);
                Cipher cipher = Cipher.getInstance(MODO_OPERACION);
                cipher.init(Cipher.ENCRYPT_MODE, ks, iv);
                byte[] salida = cipher.doFinal(mensaje.getBytes("UTF-8"));
                resultado = Hex.encodeHexString(salida);
            }
        } catch (Exception e) {
            resultado = "";
            logger.error("Error al cifrar texto: {}", e.toString());
            logger.error("Error al cifrar texto", e);
        }
        return resultado;
    }

    public static String descifrar(String publico, String privado, String mensaje) {
        String resultado = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(publico) && StringUtils.isNotBlank(privado) && StringUtils.isNotEmpty(mensaje)) {
                byte[] biv = Arrays.copyOf(publico.getBytes("UTF-8"), 8);
                IvParameterSpec iv = new IvParameterSpec(biv);

                byte[] datos = privado.getBytes("UTF-8");
                SecretKeySpec ks = new SecretKeySpec(datos, ALGORITMO);
                Cipher cipher = Cipher.getInstance(MODO_OPERACION);
                cipher.init(Cipher.DECRYPT_MODE, ks, iv);
                byte[] salida = cipher.doFinal(Hex.decodeHex(mensaje.toCharArray()));
                resultado = new String(salida);
            }
        } catch (Exception e) {
            resultado = "";
            logger.error("Error al descifrar texto: {}", e.toString());
            logger.error("Error al descifrar texto", e);
        }
        return resultado;
    }
}
