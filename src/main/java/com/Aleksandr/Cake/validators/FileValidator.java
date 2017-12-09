package com.Aleksandr.Cake.validators;

import com.Aleksandr.Cake.model.objects.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FileValidator implements Validator {

    private final Logger logger = LoggerFactory.getLogger(FileValidator.class);

    @Override
    public void validate(Object uploadedFile, Errors errors) {

        logger.info("Method validate of FileValidator class executed -- my logger");

        UploadedFile file = (UploadedFile) uploadedFile;

        if (file.getFile().getSize() == 0) {
            logger.info("File not loaded!");
            errors.rejectValue("file", "uploadedFileError.notFound", "Please select a file!");
        }

        if (file.getFile().getSize() > 3145728) {
            logger.info("File size = " + file.getFile().getSize() + ". File is to large!");
            errors.rejectValue("file", "uploadedFileError.maxSize", "Max size of selected file must be not more then 3MB!");
        }

        String contentType = file.getFile().getContentType();
        if (!(contentType.equalsIgnoreCase("image/jpg") ||
                contentType.equalsIgnoreCase("image/jpeg") ||
                contentType.equalsIgnoreCase("image/pjpeg") ||
                contentType.equalsIgnoreCase("image/png") ||
                contentType.equalsIgnoreCase("image/x-png") ||
                contentType.equalsIgnoreCase("image/bmp") ||
                contentType.equalsIgnoreCase("image/gif"))) {
            logger.info("File type mismatch!");
            errors.rejectValue("file", "uploadedFileError.invalidType", "Invalid type of uploaded file!");
        }

    }

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return false;
    }
}