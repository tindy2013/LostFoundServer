package com.example.lostfoundserver.dao

import com.example.lostfoundserver.entity.User
import org.apache.commons.dbutils.handlers.BeanHandler
import org.apache.commons.dbutils.handlers.ScalarHandler

class UserDao : BaseDao() {
    fun queryUserByName(username: String): User? {
        val handler = BeanHandler(User::class.java)
        val sql = "SELECT * FROM users WHERE username = ? LIMIT 1"
        return runner.query(sql, handler, username)
    }

    fun queryUserById(id: Int): User? {
        val handler = BeanHandler(User::class.java)
        val sql = "SELECT * FROM users WHERE id = ? LIMIT 1"
        return runner.query(sql, handler, id)
    }

    fun queryUserCount(username: String): Long {
        val sql = "SELECT COUNT(*) FROM users WHERE username = ?"
        return runner.query(sql, ScalarHandler(), username)
    }

    fun addUser(username: String, password: String): Int {
        val sql = "INSERT INTO users(username, password) VALUES (?, ?)"
        return runner.execute(sql, username, password)
    }

    fun updatePassword(username: String, password: String): Boolean {
        val sql = "UPDATE users SET password = ? WHERE username = ?"
        return runner.execute(sql, password, username) == 1
    }

    fun updateUsername(id: Int, username: String): Boolean {
        val sql = "UPDATE users SET username = ? WHERE id = ?"
        return runner.update(sql, username, id) == 1
    }

    fun updateContactName(id: Int, contactName: String): Boolean {
        val sql = "UPDATE users SET name = ? WHERE id = ?"
        return runner.update(sql, contactName, id) == 1
    }

    fun updateContactNumber(id: Int, contactNumber: String): Boolean {
        val sql = "UPDATE users SET number = ? WHERE id = ?"
        return runner.update(sql, contactNumber, id) == 1
    }

}