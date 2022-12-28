package com.example.lostfoundserver.service

import cn.hutool.crypto.digest.DigestUtil
import com.example.lostfoundserver.dao.UserDao
import com.example.lostfoundserver.entity.User
import org.junit.Test

class UserService: BaseService() {
    private val dao = UserDao()

    private fun hashPassword(password: String): String {
        return DigestUtil.sha256Hex(password)
    }

    fun login(username: String, password: String): Pair<Boolean, User?> {
        val user = dao.queryUserByName(username)
        return (user != null && user.password == hashPassword(password)) to user.apply { this?.password = "" }
    }

    fun register(username: String, password: String): Boolean {
        val result = dao.queryUserCount(username)
        if (result != 0L) return false
        return dao.addUser(username, hashPassword(password)) == 1
    }

    fun setNewPassword(username: String, oldPassword: String, newPassword: String): Boolean {
        val user = dao.queryUserByName(username)
        if (user?.password != hashPassword(oldPassword))
            return false
        return dao.updatePassword(username, hashPassword(newPassword))
    }

    fun updateUser(id: Int, username: String? = null, contactName: String? = null, contactNumber: String? = null): Boolean {
        if (username != null)
            return dao.updateUsername(id, username)
        if (contactName != null)
            return dao.updateContactName(id, contactName)
        if (contactNumber != null)
            return dao.updateContactNumber(id, contactNumber)
        return false
    }

    @Test
    fun addAdmin() {
        register("admin", "admin")
    }
}