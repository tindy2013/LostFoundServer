package com.example.lostfoundserver.controller.user

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/user/update")
class UpdateUserServlet: BasicUserServlet() {
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = req.getUser() ?: return resp.sendNotLoggedIn()
        val username = req.getParameter("username")
        val contactName = req.getParameter("contactName")
        val contactNumber = req.getParameter("contactNumber")
        val result = service.updateUser(user.id, username, contactName, contactNumber)
        return if (result)
            resp.makeResponse(0, data = "更新成功")
        else
            resp.makeResponse(1, error = "更新失败")
    }
}
