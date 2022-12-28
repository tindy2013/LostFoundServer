package com.example.lostfoundserver.controller.post

import com.example.lostfoundserver.entity.PostType
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/post/create")
class CreatePostService: BasicPostServlet() {
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = req.getUser() ?: return resp.sendNotLoggedIn()
        val name = req.requireParameter("name", resp) ?: return
        val desc = req.requireParameter("desc", resp) ?: return
        val typeInt = req.requireIntParameter("type", resp) ?: return
        val type = PostType.values()[typeInt]
        val image = req.getParameter("image") ?: "null"

        val res = service.createPost(name, user.id, type, desc, image)
        return if (res)
            resp.makeResponse(0, data = "发布成功")
        else
            resp.makeResponse(2, error = "发布失败")
    }
}