package com.example.lostfoundserver.controller.user

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/user/register")
class RegisterServlet : BasicUserServlet() {
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val username = req.requireParameter("username", resp) ?: return
        val password = req.requireParameter("password", resp) ?: return
        val res = service.register(username, password)
        return if (res)
            resp.makeResponse(0, data = "注册成功")
        else
            resp.makeResponse(2, error = "用户已存在")
    }
}