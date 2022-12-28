package com.example.lostfoundserver.controller.post

import com.example.lostfoundserver.entity.PostType
import org.json.JSONArray
import org.json.JSONObject
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/post/list")
class QueryPostServlet: BasicPostServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = req.getUser() ?: return resp.sendNotLoggedIn()
        val self = req.getBooleanParameter("self") ?: false
        val keyword = req.getParameter("keyword") ?: ""
        val typeInt = req.getIntParameter("type")
        val type = if (typeInt == null) null else PostType.values()[typeInt]
        val resolved = req.getIntParameter("resolved").toBoolean()
        val page = req.getIntParameter("page") ?: 1
        val count = req.getIntParameter("count") ?: 5
        val posts = if (!self)
            service.queryPostViews(keyword, type, resolved, page, count)
        else
            service.queryUserPostViews(user.id, page, count)
        resp.makeTableResponse(statusCode = 200, code = 0, data = JSONArray(posts.first), count = posts.second)
    }
}
