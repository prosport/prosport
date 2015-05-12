package sapsan.common

import java.io.{File, FileInputStream}
import java.nio.file.Files
import java.security.MessageDigest

import org.apache.commons.io.{FilenameUtils, IOUtils}
import play.Logger

object UploadUtils {
  def md5(data: Array[Byte]) = MessageDigest.getInstance("MD5").digest(data)

  def md5File(f: File) = md5(IOUtils.toByteArray(new FileInputStream(f)))


  def uploadAndSaveFile(file: File, storePath: File) = {
    val start = System.currentTimeMillis
    val hashMd5 = md5File(file).map("%02x" format _).mkString
    val pathMd5 = hashMd5.take(2) + File.pathSeparatorChar + hashMd5.substring(2, 4) + File.pathSeparatorChar + hashMd5.takeRight(28)
    val extension = FilenameUtils.getExtension(file.getName)

    val newFileName = storePath + pathMd5 + '.' + extension
    println(newFileName)
//    storePath +
//    Logger.debug(file.getName + " = " + hashMd5)
//    Files.move(source, target, REPLACE_EXISTING);
//    file.moveTo(storePath, true)
//    println(System.currentTimeMillis - start)
  }

}

