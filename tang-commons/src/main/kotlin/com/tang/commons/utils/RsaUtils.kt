package com.tang.commons.utils

import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.spec.MGF1ParameterSpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * RSA 加密解密工具类
 *
 * @author Tang
 */
object RsaUtils {

    private const val PRIVATE_KEY =
        "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC0dgj9afv1UTRGTgDIVy" +
        "dSgV04vfaIwcGpQ7jxvQXPdK2VjlbcRoIhza6cWjs/WbjlTISoI5Tl2vQdrEXdnMq4o3YK" +
        "QppJMWCtL8O8g5oi2FI5lPLVduQFoPR9Ist6eyTytQSfPm164JDPBZQVXSnwor0oKaGXUc" +
        "bvgYfhbOmzQjzdnamxRte/nBcbs+7qEKSw5NRlXT36PK9+O8bTaxd/Br3SXsMSmBH0i+Gg" +
        "0DjbXfshimZInFYiiYljp4nobYPo/cqxv6TpsktOYvuETlmVovuC83XBT3CrFWt+gazcDh" +
        "eGvQqevLqh6tIxm2zdPMmnjMQNw3Ybs+NYa9eY+E+1AgMBAAECggEAH0wRRxHodqU8say5" +
        "igVDdpWk+0BGz3T7B0YNfy9PIKmVqUhkXBOGYiJv1AH6IISJAouAvkBdhHeyuqqz+zlEFz" +
        "2SLTlb1LHRmHeeNMWGJ+DoccAZVVgnN6qwfDtamsVcpRMr2ApVpmfn9V98TTA5I9i1gY+m" +
        "zL2MCOGoFTp0VXguWuSa1nZTfoSg1uG2vk1+OrxWYxWR4BjX6fTg/qPZ2Hvvn6XHdooNwr" +
        "7m2CaDcX5oTgpSVfo/7HVebrOs+V1PKwuyVzpcQ9udNZabxPjsN2A8wwtq0RYW/9GL0DdN" +
        "BegLwp4mMMhaPhs0kuXRr6Xxa0u7pzCRb7K6XOK1uOFKiQKBgQDwmQgtQq//un2l3VjE2D" +
        "qY53kkuviMsrNLWYsxv5uDVvppRlsaQp0wUcsAGWGbS1Dt0z2TTDwRzN44Bn2kxfve7sHA" +
        "+KDA5T/Iqlfu3+zl6bRQwCUOqAibmFkMwagKoYB7p2+ao+Z2uGYRG1tuvTnwGGNiClxtUf" +
        "cfaqDrap4xewKBgQDAA3hNSge7Tmgyuto/06qb7k0TAaZNx2hqs71xugONNjKeEol3y2Vo" +
        "8BVlzqpPuNJQ0hepIXs1ALopxC7P+/e8e9v3o3uEmoufTvRFL2EFB8Wr5z61BJ/6SKT0QL" +
        "i4xRevLq07v/LkUBoomgwtB9RWchSj2cY/saI7RB92H4JEjwKBgCUBZKCRgUB0Dp9UTDL6" +
        "jwi1kYx0tYXudmVAgIhGUEUDO8C1cY24cTTdX7vEK58XFnt94hqlvxd9yzASz4BoczT2xB" +
        "ZKJ2+D0yuqB5xWFLnIGFPTOd/nOGu2IvwzTQVVkc1zE1dVnjzkX86Bxq5hrGehWKfbsBug" +
        "X8IVRGrxGyPBAoGAfa6HwwdO8kJLH9GAY8DXboNXvbYZtdVtOlJ2EQexpW+xSBhYFKp0sX" +
        "BcgSv5/H68YxxxUkpRDAtyzz3Tal3B9YSZIYnHoq9J7rfOWa6+cX153KBbQj9Ju5hrKFlo" +
        "z8BqVUdXKsHkaZ8o0CStDZiPWxoG+ozkH/LUfriDY10Sdm8CgYEAvY6tcfAKCWS+DDAvKa" +
        "99Gkdzzw8hOc1jrml/GNGlAlYJyB1ZOwHWySLpwMIIsYZ8mcG1cz4JWhbQEMAXQqMo1bL7" +
        "b9gBQIWIYgA64kIKyW8rUc15wWN/kTEgGJ9K6LrOgk4eWiom4iQWrP/9yrtdJVJcGtff2o" +
        "YCqdG2v1Isfu8="

    private const val PUBLIC_KEY =
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtHYI/Wn79VE0Rk4AyFcnUoFdOL" +
        "32iMHBqUO48b0Fz3StlY5W3EaCIc2unFo7P1m45UyEqCOU5dr0HaxF3ZzKuKN2CkKaSTFg" +
        "rS/DvIOaIthSOZTy1XbkBaD0fSLLensk8rUEnz5teuCQzwWUFV0p8KK9KCmhl1HG74GH4W" +
        "zps0I83Z2psUbXv5wXG7Pu6hCksOTUZV09+jyvfjvG02sXfwa90l7DEpgR9IvhoNA42137" +
        "IYpmSJxWIomJY6eJ6G2D6P3Ksb+k6bJLTmL7hE5ZlaL7gvN1wU9wqxVrfoGs3A4Xhr0Knr" +
        "y6oerSMZts3TzJp4zEDcN2G7PjWGvXmPhPtQIDAQAB"

    private const val ALGORITHM = "RSA"

    private const val TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"

    private val parameterSpec = OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT)

    @OptIn(ExperimentalEncodingApi::class)
    private fun encodeBase64(source: ByteArray): String {
        return Base64.encode(source)
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun decodeBase64(source: CharSequence): ByteArray {
        return Base64.decode(source)
    }

    /**
     * Generate RSA key pair with 2048 bits
     *
     * @return Pair of first element is private key, second element is public key
     */
    @JvmStatic
    fun generateKeyPair(): Pair<String, String> {
        val keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM)
        keyPairGenerator.initialize(2048)
        val keyPair = keyPairGenerator.genKeyPair()
        val privateKey = encodeBase64(keyPair.private.encoded)
        val publicKey = encodeBase64(keyPair.public.encoded)
        return Pair(privateKey, publicKey)
    }

    @JvmStatic
    fun encrypt(text: String, publicKey: String = PUBLIC_KEY): String {
        val keySpec = X509EncodedKeySpec(decodeBase64(publicKey))
        val keyFactory = KeyFactory.getInstance(ALGORITHM)
        val publicKey = keyFactory.generatePublic(keySpec)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey, parameterSpec)
        val encryptedBytes = cipher.doFinal(text.toByteArray())
        return encodeBase64(encryptedBytes)
    }

    @JvmStatic
    fun encrypt(text: String): String {
        return encrypt(text, PUBLIC_KEY)
    }

    @JvmStatic
    fun decrypt(text: String, privateKey: String = PRIVATE_KEY): String {
        val keySpec = PKCS8EncodedKeySpec(decodeBase64(privateKey))
        val keyFactory = KeyFactory.getInstance(ALGORITHM)
        val privateKey = keyFactory.generatePrivate(keySpec)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, privateKey, parameterSpec)
        val decryptedBytes = cipher.doFinal(decodeBase64(text))
        return String(decryptedBytes)
    }

    @JvmStatic
    fun decrypt(text: String): String {
        return decrypt(text, PRIVATE_KEY)
    }

}
