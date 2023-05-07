package com.arjundev.blog.service.file

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class FileService : IFileService {
    override suspend fun uploadImage(path: String, file: MultipartFile): String? {
        val name = file.originalFilename ?: "unknown"

        //random name generated files
        val randomId = UUID.randomUUID().toString()

        val filename1 = "$randomId${name.substring(name.lastIndexOf('.'))}"

        //full path
        val filepath = path + File.separator + filename1
        //create folder if not created
        val f = File(path)
        if (!f.exists()) {
            f.mkdir()
        }
        // file copy
        withContext(Dispatchers.IO) {
            Files.copy(file.inputStream, Paths.get(filepath))
        }
        return filename1
    }

    override suspend fun getResource(path: String, filename: String): InputStream {
        val fullPath = path + File.separator + filename
        return withContext(Dispatchers.IO) {
            FileInputStream(fullPath)
        }
    }

    override suspend fun deleteExistingImage(path: String, filename: String): Boolean {
        val fullPath = path + File.separator + filename
        return withContext(Dispatchers.IO) {
            return@withContext Files.deleteIfExists(Path.of(fullPath))
        }
    }
}