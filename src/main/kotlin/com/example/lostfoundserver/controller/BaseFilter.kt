package com.example.lostfoundserver.controller

import com.example.lostfoundserver.entity.User
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest

abstract class BaseFilter: HttpFilter() {
    protected fun HttpServletRequest.getUser() = session.getAttribute("user") as User?
}