package com.arjundev.blog.service.file

import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

interface IFileService {
    @Throws(IOException::class)
    suspend fun uploadImage(path: String, file: MultipartFile):String?
    @Throws(FileNotFoundException::class)
    suspend fun getResource(path:String,filename:String):InputStream

    suspend fun deleteExistingImage(path: String, filename: String):Boolean
}