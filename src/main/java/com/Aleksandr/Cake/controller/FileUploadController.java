package com.Aleksandr.Cake.controller;

import com.Aleksandr.Cake.validators.FileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FileUploadController {

    @Autowired
    private FileValidator fileValidator;

    private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String singleFileUpload(@ModelAttribute("stringURL") String stringURL, @ModelAttribute("file") MultipartFile file, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        logger.info("Method singleFileUpload executed -- my logger");

//        to call method validate fileValidator class, MultipartFile should be change to UploadedFile
//        fileValidator.validate(file,bindingResult);

        if (file.getSize() == 0) {
            logger.info("File not loaded!");
            redirectAttributes.addFlashAttribute("fileError", "Please select a file!");
            return "redirect:/" + stringURL;
        }

        if (file.getSize() > 3145728) {
            logger.info("File size = " + file.getSize() + ". File is to large!");
            redirectAttributes.addFlashAttribute("fileError", "Max size of selected file must be not more then 3MB!");
            return "redirect:/" + stringURL;
        }

        String contentType = file.getContentType();
        if (!(contentType.equalsIgnoreCase("image/jpg") ||
                contentType.equalsIgnoreCase("image/jpeg") ||
                contentType.equalsIgnoreCase("image/pjpeg") ||
                contentType.equalsIgnoreCase("image/png") ||
                contentType.equalsIgnoreCase("image/x-png") ||
                contentType.equalsIgnoreCase("image/bmp") ||
                contentType.equalsIgnoreCase("image/gif"))) {
            logger.info("File type mismatch!");
            redirectAttributes.addFlashAttribute("fileError", "Invalid type of uploaded file!");
            return "redirect:/" + stringURL;
        }

        try {
            // Get the file and save it somewhere
            byte[] loadedFile = file.getBytes();

        } catch (IOException e) {
            throw new IOException();
        }

        return "redirect:/" + stringURL;
    }

}
