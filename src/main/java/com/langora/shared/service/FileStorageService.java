package com.langora.shared.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    /**
     * Uploads an image to the storage provider.
     *
     * @param file   The multipart file to upload
     * @param folder The folder name to store the file
     * @return The public URL of the uploaded image
     */
    String uploadImage(MultipartFile file, String folder);
}
