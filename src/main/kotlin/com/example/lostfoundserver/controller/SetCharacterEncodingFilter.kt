package com.example.lostfoundserver.controller

import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter

@WebFilter("/*")
class SetCharacterEncodingFilter : BaseFilter() {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (request.characterEncoding == null)
            request.characterEncoding = "UTF-8"
        response.characterEncoding = "UTF-8"
        chain.doFilter(request, response)
    }
}