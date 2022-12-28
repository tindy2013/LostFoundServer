package com.example.lostfoundserver.controller.post

import com.example.lostfoundserver.entity.PostType
import org.json.JSONObject
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/post/update")
class UpdatePostService: BasicPostServlet() {
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = req.getUser() ?: return resp.sendNotLoggedIn()
        val typeInt = req.requireIntParameter("type", resp) ?: return
        val type = PostType.values()[typeInt]
        val id = req.requireIntParameter("id", resp) ?: return
        val name = req.requireParameter("name", resp) ?: return
        val desc = req.requireParameter("desc", resp) ?: return
        val image = req.getParameter("image") ?: "null"

        val res = service.updatePost(id, user.id, name, type, desc, image)
        return if (res)
            resp.makeResponse(0, data = JSONObject(service.getPostViewById(id)))
        else
            resp.makeResponse(1, error = "更新失败")
    }
}