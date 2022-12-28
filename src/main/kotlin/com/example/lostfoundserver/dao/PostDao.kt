package com.example.lostfoundserver.dao

import com.example.lostfoundserver.entity.Post
import com.example.lostfoundserver.entity.PostType
import org.apache.commons.dbutils.handlers.BeanHandler
import org.apache.commons.dbutils.handlers.BeanListHandler
import org.apache.commons.dbutils.handlers.ScalarHandler
import org.junit.Test
import java.util.*

class PostDao: BaseDao() {
    fun queryPosts(keyword: String = "", type: PostType? = PostType.TYPE_FOUND, resolved: Boolean = false, limit: Limit): List<Post> {
        val sql = "SELECT * FROM posts WHERE (name LIKE ? OR description LIKE ?) AND type = ? AND resolved = ? ORDER BY time DESC LIMIT ?, ?"
        val sqlNoType = "SELECT * FROM posts WHERE (name LIKE ? OR description LIKE ?) AND resolved = ? ORDER BY time DESC LIMIT ?, ?"
        val realKeyword = "%$keyword%"
        val handler = BeanListHandler(Post::class.java)
        return if (type == null) {
            runner.query(sqlNoType, handler, realKeyword, realKeyword, resolved, limit.begin, limit.count)
        } else {
            runner.query(sql, handler, realKeyword, realKeyword, type.ordinal, resolved, limit.begin, limit.count)
        }
    }

    fun queryPostCount(keyword: String = "", type: PostType? = PostType.TYPE_FOUND, resolved: Boolean = false): Int {
        val sql = "SELECT COUNT(*) FROM posts WHERE (name LIKE ? OR description LIKE ?) AND type = ? AND resolved = ?"
        val sqlNoType = "SELECT COUNT(*) FROM posts WHERE (name LIKE ? OR description LIKE ?) AND resolved = ?"
        val realKeyword = "%$keyword%"
        return if (type == null) {
            runner.query(sqlNoType, ScalarHandler(), realKeyword, realKeyword, resolved)
        } else {
            runner.query(sql, ScalarHandler(), realKeyword, realKeyword, type.ordinal, resolved)
        }
    }

    fun addPost(name: String, owner: Int, type: PostType, description: String, image: String): Boolean {
        val sql = "INSERT INTO posts(name, owner, time, type, description, image, resolved) VALUES (?, ?, ?, ?, ?, ?, ?)"
        return runner.update(sql, name, owner, Date(), type.ordinal, description, image, false) == 1
    }

    fun updatePost(id: Int, name: String, type: PostType, description: String, image: String): Boolean {
        val sql = "UPDATE posts SET name = ?, type = ?, description = ?, image = ? WHERE id = ?"
        return runner.update(sql, name, type.ordinal, description, image, id) == 1
    }

    fun setPostResolve(id: Int, resolved: Boolean): Boolean {
        val sql = "UPDATE posts SET resolved = ? WHERE id = ?"
        return runner.update(sql, resolved, id) == 1
    }

    fun queryPostById(id: Int): Post? {
        val sql = "SELECT * FROM posts WHERE id = ? LIMIT 1"
        val handler = BeanHandler(Post::class.java)
        return runner.query(sql, handler, id)
    }

    fun queryUserPosts(userId: Int, limit: Limit): List<Post> {
        val sql = "SELECT * FROM posts WHERE owner = ? ORDER BY time DESC LIMIT ?, ?"
        val handler = BeanListHandler(Post::class.java)
        return runner.query(sql, handler, userId, limit.begin, limit.count)
    }

    fun queryUserPostCount(userId: Int): Int {
        val sql = "SELECT COUNT(*) FROM posts WHERE owner = ?"
        return runner.query(sql, ScalarHandler(), userId)
    }
}