package com.example.lostfoundserver.controller.file

import org.json.JSONObject
import java.nio.file.Paths
import javax.servlet.annotation.MultipartConfig
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/file/upload")
@MultipartConfig
class UploadServlet : BasicFileServlet() {
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = req.getUser() ?: return resp.sendNotLoggedIn()
        val filePart = req.getPart("file") ?: return resp.makeArgMissingError("file")
        val fileName = Paths.get(filePart.submittedFileName).fileName.toString()
        val fileContent = filePart.inputStream
        val entry = service.createFile(user.id, root, fileName, fileContent) ?: return resp.makeResponse(1, error = "创建文件失败")
        resp.makeResponse(0, data = entry.key)
    }
}