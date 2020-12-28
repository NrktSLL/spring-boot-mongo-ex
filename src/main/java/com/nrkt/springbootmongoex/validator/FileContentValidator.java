package com.nrkt.springbootmongoex.validator;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FileContentValidator implements ConstraintValidator<ValidFile, MultipartFile> {
    @Override
    public void initialize(ValidFile constraintAnnotation) {

    }

    @SneakyThrows
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null) throw new IllegalAccessException("field can not be null");
        return validateContentType(Objects.requireNonNull(file.getContentType()));
    }

    public Boolean validateContentType(String contentType) {
        return contentType.equals("application/pdf") ||
                contentType.equals("text/xml") ||
                contentType.equals("application/json") ||
                contentType.equals("image/jpeg");
    }
}
