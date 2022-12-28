package com.example.lostfoundserver.controller.user

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/user/reset-pass")
class ResetPasswordServlet: BasicUserServlet() {
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = req.getUser() ?: return resp.sendNotLoggedIn()
        val oldPassword = req.requireParameter("old_pass", resp) ?: return
        val newPassword = req.requireParameter("new_pass", resp) ?: return
        val result = service.setNewPassword(user.username, oldPassword, newPassword)
        return if (result)
            resp.makeResponse(data = "密码已更新")
        else
            resp.makeResponse(1, error = "旧密码不正确")
    }
}
