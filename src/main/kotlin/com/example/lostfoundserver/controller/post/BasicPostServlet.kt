package com.example.lostfoundserver.controller.post

import com.example.lostfoundserver.controller.BaseServlet
import com.example.lostfoundserver.service.PostService

abstract class BasicPostServlet: BaseServlet() {
    protected val service = PostService()
}