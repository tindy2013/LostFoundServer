package com.example.lostfoundserver.controller.file

import com.example.lostfoundserver.controller.BaseServlet
import com.example.lostfoundserver.service.FileService


abstract class BasicFileServlet: BaseServlet() {
    protected val service = FileService()
    protected lateinit var root: String

    override fun init() {
        root = servletContext.getRealPath("/WEB-INF")
    }
}