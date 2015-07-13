package sapsan.common

import java.io._
import java.security.MessageDigest

import org.apache.commons.codec.binary.Hex
import org.apache.commons.io.{FilenameUtils, IOUtils}
import play.Logger
import play.api.libs.Files.TemporaryFile

object UploadUtils {
  def md5(data: Array[Byte]) = MessageDigest.getInstance("MD5").digest(data)

  def md5File(f: File) = md5(IOUtils.toByteArray(new FileInputStream(f)))


  def uploadAndSaveFile(tf: TemporaryFile, filename: String, storePath: File) = {
    val sep = File.separator
    val hashMd5 = md5File(tf.file).map("%02x" format _).mkString
    val pathMd5 = hashMd5.take(2) + sep + hashMd5.substring(2, 4) + sep + hashMd5.takeRight(28)
    val extension = FilenameUtils.getExtension(filename)
    val relativePath = pathMd5 + '.' + extension

    val newFile = new File(storePath + sep + relativePath)
    tf.moveTo(newFile, true)

    relativePath
  }

  def uploadAndSaveFile(bytes: Array[Byte], filename: String, storePath: File) = {
    val sep = File.separator
    val hexString = new String(Hex.encodeHex(bytes))

    val hashMd5 = md5(bytes).map("%02x" format _).mkString
    val pathMd5 = hashMd5.take(2) + sep + hashMd5.substring(2, 4) + sep + hashMd5.takeRight(28)
    val extension = FilenameUtils.getExtension(filename)
    val relativePath = pathMd5 + '.' + extension

    val outFileName = storePath + sep + relativePath
    val newFile = new File(storePath + sep + relativePath)
    newFile.getParentFile().mkdirs();
    val bos = new BufferedOutputStream(new FileOutputStream(newFile))
    Stream.continually(bos.write(bytes))
    bos.close()

     relativePath
  }

}

