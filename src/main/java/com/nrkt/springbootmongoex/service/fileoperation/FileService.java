package com.nrkt.springbootmongoex.service.fileoperation;

import com.nrkt.springbootmongoex.dto.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<FileResponse> getFiles(String employeeId);

    void uploadFile(String id, MultipartFile multipartFile) throws Exception;

    FileResponse getFile(String employeeId);

    void removeFile(String id);
}
