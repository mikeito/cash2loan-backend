package com.cash2loan.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadDirectory = Paths.get("uploaded-images");

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadDirectory.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioException) {
            throw new IOException("Error saving upload image: "+ fileName, ioException);
        }
    }

    public static String getResource(String fileName) throws FileNotFoundException {
        Path path = Paths.get("uploaded-images");
        String fullPath = path+ File.separator+fileName;
        return fullPath;
//        InputStream fs = new FileInputStream(fullPath);
//        return fs;

    }
}
