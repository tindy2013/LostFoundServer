package com.example.lostfoundserver.controller.post

import org.json.JSONObject
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/post/resolve")
class ResolvePostServlet: BasicPostServlet() {
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = req.getUser() ?: return resp.sendNotLoggedIn()
        val id = req.requireIntParameter("id", resp) ?: return
        val resolved = req.requireBooleanParameter("resolved", resp) ?: return

        val result = service.setPostResolve(id, user.id, resolved)
        return if (result)
            resp.makeResponse(0, data = JSONObject(service.getPostViewById(id)))
        else
            resp.makeResponse(1, error = "更新失败")
    }
}