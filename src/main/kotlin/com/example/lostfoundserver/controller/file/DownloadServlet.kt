package com.example.lostfoundserver.controller.file

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.file.Files
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/file/download/*")
class DownloadServlet : BasicFileServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val key = URLDecoder.decode(req.pathInfo.substring(1), "UTF-8")
        if (key.isEmpty()) return resp.makeArgMissingError("key")
        val fileEntry = service.findFileByKey(key) ?: return resp.makeResponse(1, error = "文件未找到")
        val realName = fileEntry.filename
        val file = service.acquireFile(root, key) ?: return resp.makeResponse(2, error = "文件未找到")
        resp.setHeader("Content-Type", servletContext.getMimeType(realName) ?: "application/octet-stream")
        resp.setHeader("Content-Length", file.length().toString())
        //resp.setHeader("Content-Disposition", "attachment; filename=" + realName + "; filename*=utf-8''" + URLEncoder.encode(realName, "UTF-8").replace("+", "%20"))
        Files.copy(file.toPath(), resp.outputStream)
    }
}