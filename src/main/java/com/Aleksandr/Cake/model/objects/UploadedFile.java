package com.Aleksandr.Cake.model.objects;

import org.springframework.web.multipart.MultipartFile;

public class UploadedFile {

    private String fileName;
    private MultipartFile file;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}