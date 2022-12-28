package com.example.lostfoundserver.service

import com.example.lostfoundserver.dao.BaseDao
import com.example.lostfoundserver.dao.PostDao
import com.example.lostfoundserver.dao.UserDao
import com.example.lostfoundserver.entity.Post
import com.example.lostfoundserver.entity.PostType
import com.example.lostfoundserver.entity.PostView
import com.example.lostfoundserver.entity.User
import java.time.format.DateTimeFormatter

class PostService: BaseService() {
    private val dao = PostDao()
    private val userDao = UserDao()

    private fun Post.toPostView(): PostView {
        val owner = userDao.queryUserById(owner) ?: User(name = "用户不存在", number = "用户不存在")
        return PostView(
            id,
            owner.id,
            name,
            getTypeReal(),
            image,
            description,
            owner.name,
            owner.number,
            resolved,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(time)
        )
    }
    private fun List<Post>.toPostViews() = map { it.toPostView() }

    fun queryPosts(keyword: String = "", type: PostType? = PostType.TYPE_FOUND, resolved: Boolean = false, page: Int, count: Int): List<Post> {
        return dao.queryPosts(keyword, type, resolved, BaseDao.Limit(page, count))
    }

    fun queryPostCount(keyword: String = "", type: PostType? = PostType.TYPE_FOUND, resolved: Boolean = false): Int {
        return dao.queryPostCount(keyword, type, resolved)
    }

    fun queryUserPosts(userId: Int, page: Int, count: Int): List<Post> {
        return dao.queryUserPosts(userId, BaseDao.Limit(page, count))
    }

    fun queryUserPostCount(userId: Int): Int {
        return dao.queryUserPostCount(userId)
    }

    fun queryPostViews(keyword: String = "", type: PostType? = PostType.TYPE_FOUND, resolved: Boolean = false, page: Int, count: Int): Pair<List<PostView>, Int> {
        val posts = queryPosts(keyword, type, resolved, page, count)
        return posts.toPostViews() to queryPostCount(keyword, type, resolved)
    }

    fun queryUserPostViews(userId: Int, page: Int, count: Int): Pair<List<PostView>, Int> {
        val posts = queryUserPosts(userId, page, count)
        return posts.toPostViews() to queryUserPostCount(userId)
    }

    fun createPost(name: String, owner: Int, type: PostType, description: String, image: String? = null): Boolean {
        return dao.addPost(name, owner, type, description, image ?: "null")
    }

    fun updatePost(id: Int, userId: Int, name: String, type: PostType, description: String, image: String? = null): Boolean {
        val post = dao.queryPostById(id) ?: return false
        if (post.owner != userId) return false
        return dao.updatePost(id, name, type, description, image ?: "null")
    }

    fun setPostResolve(id: Int, userId: Int, resolved: Boolean): Boolean {
        val post = dao.queryPostById(id) ?: return false
        if (post.owner != userId) return false
        return dao.setPostResolve(id, resolved)
    }

    fun getPostById(id: Int): Post? {
        return dao.queryPostById(id)
    }

    fun getPostViewById(id: Int): PostView? {
        return getPostById(id)?.toPostView()
    }
}