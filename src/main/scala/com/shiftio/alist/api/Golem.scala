package com.shiftio.alist.api

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}

object Golem {

  private val key = "DSRqGmPgssSfx6r9"
  private val initVector = "QFMgtdtxp6QektB4"

  def encrypt(text: String): String = {

    val iv = new IvParameterSpec(initVector.getBytes("UTF-8"))
    val keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES")

    val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)

    val encrypted = cipher.doFinal(text.getBytes())
    return Base64.getEncoder.encodeToString(encrypted)
  }

  def decrypt(text: String): String = {
    val iv = new IvParameterSpec(initVector.getBytes("UTF-8"))
    val skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES")

    val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
    val original = cipher.doFinal(Base64.getDecoder.decode(text))

    new String(original)
  }

}
