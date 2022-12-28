package com.example.lostfoundserver.service

import com.example.lostfoundserver.dao.FileDao
import com.example.lostfoundserver.entity.FileEntry
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.nio.file.Files
import java.security.MessageDigest

class FileService: BaseService() {
    private val dao = FileDao()

    private fun ensureUploadDirectoryExists(path: String): File? {
        val dir: File
        try {
            dir = File("$path/upload")
            if (!dir.exists())
                Files.createDirectory(dir.toPath())
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return dir
    }

    private fun acquireFile(dir: File, key: String): File {
        return File(dir.toPath().resolve(key).toString())
    }

    fun acquireFile(dir: String, key: String): File? {
        val path = ensureUploadDirectoryExists(dir) ?: return null
        return acquireFile(path, key)
    }

    private fun generateKey(data: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(data)
        return BigInteger(1, digest.digest()).toString(16)
    }

    private fun saveFile(dir: File, fileContent: InputStream): String? {
        val data = ByteArray(fileContent.available())
        if (fileContent.read(data) < 0) return null
        fileContent.close()
        val key = generateKey(data)
        val newFile = acquireFile(dir, key)
        newFile.createNewFile()
        val stream = FileOutputStream(newFile, false)
        stream.write(data)
        stream.close()
        return key
    }

    private fun removeFile(dir: File, name: String) = acquireFile(dir, name).delete()

    fun createFile(userId: Int, rootPath: String, fileName: String, fileContent: InputStream): FileEntry? {
        val root = ensureUploadDirectoryExists(rootPath) ?: return null
        val size = fileContent.available()
        val key = saveFile(root, fileContent) ?: return null
        val entry = FileEntry(owner = userId, filename = fileName, size = size, key = key)
        if (!dao.fileExists(userId, fileName)) dao.addFile(entry)
        return entry
    }

    fun findFileByKey(key: String): FileEntry? {
        return dao.getFileByKey(key)
    }

    fun deleteFile(userId: Int, rootFolder: String, key: String): Boolean {
        val root = ensureUploadDirectoryExists(rootFolder) ?: return false
        val entry = findFileByKey(key) ?: return false
        removeFile(root, entry.key)
        return dao.deleteFile(userId, key)
    }
}