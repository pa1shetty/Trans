package com.example.trans.utillity

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.codec.binary.Hex
import javax.inject.Inject


class AesLibrary @Inject constructor() {
    private fun generateKey(): Key {
        return SecretKeySpec(encryptionKey.toByteArray(), ALGO)
    }


    @Throws(Exception::class)
    private fun getCipherTEXT(keyValue: Key, mode: Int): Cipher {
        val cipher = Cipher.getInstance(ENCRYP_ALGO_AES_PAD)
        val iv = ByteArray(cipher.blockSize)
        val ivParams = IvParameterSpec(iv)
        cipher.init(mode, keyValue, ivParams)
        return cipher
    }

    fun encryptData(data: String): String {
        return encrypt(prefix + data)
    }

    fun decryptData(data: String): String {
        return decrypt(data).trim().substring(prefix.length)
    }

    private fun encrypt(data: String): String {
        var encryptedData = ""
        try {
            val key = generateKey()
            val cp = getCipherTEXT(key, Cipher.ENCRYPT_MODE)
            encryptedData = getAesEncryptData(cp, data.toByteArray(StandardCharsets.UTF_8))
        } catch (e: Exception) {
            //Logger.error(e)
        }
        return encryptedData
    }

    /**
     * Description : Encrypt the byte array of required text
     *
     * @param cipher
     * @param dataToEncrpt
     * @return encrypted hexadecimal string format.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getAesEncryptData(cipher: Cipher, dataToEncrpt: ByteArray): String {
        val stringBuilder = StringBuilder()
        val newPaddeStr = padSpace(dataToEncrpt, cipher.blockSize)
        val bais = ByteArrayInputStream(newPaddeStr)
        //Loop for 16 bytes in string
        var i = 0
        while (i < newPaddeStr.size) {
            val tempData = ByteArray(cipher.blockSize)
            bais.read(tempData, 0, tempData.size)
            stringBuilder.append(java.lang.String.valueOf(Hex.encodeHex(cipher.doFinal(tempData))))
            i += 16
        }
        return stringBuilder.toString()
    }

    /**
     * Description : add zeros to the bytes which are empty.
     *
     * @param byteArrToPad
     * @param blocksize
     * @return byte array without spaces
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun padSpace(byteArrToPad: ByteArray, blocksize: Int): ByteArray {
        var byteArrToPadLength = byteArrToPad.size
        var nofPadRequied =
            if (byteArrToPadLength % blocksize == 0) 0 else blocksize - byteArrToPadLength % blocksize
        val newStr = ByteArray(byteArrToPadLength + nofPadRequied)
        System.arraycopy(byteArrToPad, 0, newStr, 0, byteArrToPad.size)
        if (nofPadRequied != 0) {
            do {
                newStr[byteArrToPadLength] = ' '.code.toByte()
                byteArrToPadLength++
                //decrement pad loop
                nofPadRequied--
            } while (nofPadRequied > 0)
        }
        return newStr
    }

    /**
     * Description : used for decrypting the tex.
     *
     * @param message
     * @return original data
     */
    private fun decrypt(message: String): String {
        val stringBuilder = StringBuilder()
        try {
            val encryptedMessage: ByteArray = Hex.decodeHex(message.toCharArray())
            val k = generateKey()
            val cipher = getCipherTEXT(k, Cipher.DECRYPT_MODE)
            val bais = ByteArrayInputStream(encryptedMessage)
            //Loop for 16 bytes in string
            var i = 0
            while (i < encryptedMessage.size) {
                val tempData = ByteArray(cipher.blockSize)
                bais.read(tempData, 0, tempData.size)
                stringBuilder.append(String(cipher.doFinal(tempData)))
                i += 16
            }
            //decrypted = decrypted.substring(0, decrypted.indexOf(0x00));
        } catch (e: Exception) {
            //Logger.error(e)
        }
        return stringBuilder.toString()
    }

    companion object {
        private const val ALGO = "AES"
        private const val ENCRYP_ALGO_AES_PAD = "AES/CBC/NoPadding"
        private const val encryptionKey = "Aipl@2020$#\$pref"
        private const val prefix = "AUM"
    }

}