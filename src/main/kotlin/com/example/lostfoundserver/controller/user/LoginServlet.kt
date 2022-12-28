package com.example.lostfoundserver.controller.user

import org.json.JSONObject
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/user/login")
class LoginServlet : BasicUserServlet() {
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val username = req.requireParameter("username", resp) ?: return
        val password = req.requireParameter("password", resp) ?: return

        val res = service.login(username, password)
        if (res.first)
            resp.makeResponse(0, data = JSONObject(res.second))
        else
            return resp.makeResponse(1, error = "用户名或密码错误")
        req.session.setAttribute("user", res.second)
    }
}