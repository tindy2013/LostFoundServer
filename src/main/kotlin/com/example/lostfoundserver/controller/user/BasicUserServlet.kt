package com.example.lostfoundserver.controller.user

import com.example.lostfoundserver.controller.BaseServlet
import com.example.lostfoundserver.service.UserService

abstract class BasicUserServlet: BaseServlet() {
    protected val service = UserService()
}