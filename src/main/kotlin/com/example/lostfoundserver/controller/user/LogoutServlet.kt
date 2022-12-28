package com.example.lostfoundserver.controller.user

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/user/logout")
class LogoutServlet : BasicUserServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.session.invalidate()
        return resp.makeResponse()
    }
}