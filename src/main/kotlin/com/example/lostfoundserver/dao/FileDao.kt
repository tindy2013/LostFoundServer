package com.example.lostfoundserver.dao

import com.example.lostfoundserver.entity.FileEntry
import org.apache.commons.dbutils.handlers.BeanHandler
import org.apache.commons.dbutils.handlers.BeanListHandler
import org.apache.commons.dbutils.handlers.ScalarHandler
import org.junit.Test

class FileDao: BaseDao() {
    fun addFile(entry: FileEntry): Boolean {
        val sql = "INSERT INTO files(owner, filename, size, `key`) VALUES (?, ?, ?, ?)"
        return runner.update(sql, entry.owner, entry.filename, entry.size, entry.key) == 1
    }

    fun getFileByKey(key: String): FileEntry? {
        val handler = BeanHandler(FileEntry::class.java)
        val sql = "SELECT * FROM files WHERE `key` = ? LIMIT 1"
        return runner.query(sql, handler, key)
    }

    fun getFileById(id: Int): FileEntry? {
        val handler = BeanHandler(FileEntry::class.java)
        val sql = "SELECT * FROM files WHERE id = ? LIMIT 1"
        return runner.query(sql, handler, id)
    }

    fun deleteFile(owner: Int, key: String): Boolean {
        val sql = "DELETE FROM files WHERE `key` = ? AND owner = ?"
        return runner.update(sql, key, owner) == 1
    }

    fun fileExists(owner: Int, name: String): Boolean {
        val sql = "SELECT COUNT(*) FROM files WHERE owner = ? AND filename = ? LIMIT 1"
        return runner.query(sql, ScalarHandler<Long>(), owner, name) != 0L
    }
}