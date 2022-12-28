package com.example.lostfoundserver.controller

import com.example.lostfoundserver.entity.User
import org.json.JSONArray
import org.json.JSONObject
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class BaseServlet : HttpServlet() {
    protected fun HttpServletResponse.makePlainResponse(code: Int = 200, data: String, dataType: String = "application/json; charset=UTF-8") {
        contentType = dataType
        status = code
        writer.write(data)
    }

    protected fun HttpServletResponse.makeResponse(
        code: Int = 0,
        data: Any? = null,
        error: Any? = null
    ) {
        val obj = JSONObject()
        obj.put("code", code)
        if (error != null) {
            obj.put("error", error)
        } else  {
            obj.put("data", data)
        }
        makePlainResponse(200, obj.toString())
    }

    protected fun HttpServletResponse.makeArgMissingError(name: String) {
        return makeResponse(1, error = "request failed: argument $name missing")
    }

    protected fun HttpServletRequest.getIntParameter(name: String) = getParameter(name)?.toIntOrNull()
    protected fun HttpServletRequest.getBooleanParameter(name: String) = getParameter(name)?.toBoolean()

    protected fun HttpServletRequest.requireParameter(name: String, resp: HttpServletResponse): String? {
        val value = getParameter(name)
        if (value.isNullOrBlank()) {
            resp.makeArgMissingError(name)
            return null
        }
        return value
    }
    protected fun HttpServletRequest.requireIntParameter(name: String, resp: HttpServletResponse) = requireParameter(name, resp)?.toIntOrNull()
    protected fun HttpServletRequest.requireBooleanParameter(name: String, resp: HttpServletResponse) = requireParameter(name, resp)?.toBoolean()

    protected fun HttpServletResponse.makeTableResponse(
        statusCode: Int,
        code: Int,
        msg: String = "",
        count: Int,
        data: Any? = null
    ) {
        val obj = JSONObject().apply {
            put("code", code)
            put("count", count)
            put("msg", msg)
            put("data", data)
        }
        makePlainResponse(statusCode, obj.toString())
    }

    protected fun HttpServletResponse.sendNotLoggedIn() = makeResponse(403, error = "未登录")
    private fun HttpServletResponse.sendInsufficientPrivilege() = makeResponse(403, error = "权限不足")
    protected fun HttpServletRequest.getUser() = session.getAttribute("user") as User?

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) = super.doGet(req, resp)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) = super.doPost(req, resp)
    override fun doHead(req: HttpServletRequest, resp: HttpServletResponse) = super.doHead(req, resp)
    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) = super.doPut(req, resp)
    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) = super.doDelete(req, resp)
    override fun doOptions(req: HttpServletRequest, resp: HttpServletResponse) = super.doOptions(req, resp)
    override fun doTrace(req: HttpServletRequest, resp: HttpServletResponse) = super.doTrace(req, resp)

    protected fun Int?.toBoolean() = this != null && this > 0
}