package com.nrkt.springbootmongoex.service.fileoperation;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;

import com.nrkt.springbootmongoex.exception.BadRequestException;
import com.nrkt.springbootmongoex.mapper.FileMapper;
import com.nrkt.springbootmongoex.repository.EmployeeRepository;
import com.nrkt.springbootmongoex.validator.ValidFile;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.web.multipart.MultipartFile;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.nrkt.springbootmongoex.dto.response.FileResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FileServiceImpl implements FileService {

    GridFsOperations gridFsOperations;
    FileMapper fileMapper;
    EmployeeRepository employeeRepository;

    @Override
    public List<FileResponse> getFiles(String employeeId) {
        var exitEmployee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() -> new BadRequestException("employee Not Found"));

        Query query = new Query(Criteria.where("metadata.employeeId").is(exitEmployee.getId()));
        GridFSFindIterable gridFSFiles = gridFsOperations.find(query);

        List<FileResponse> fileList = new ArrayList<>();
        for (GridFSFile gfs : gridFSFiles) {
            var file = gridFSFileOperation(gfs);
            fileList.add(file);
        }
        return fileList;
    }

    @Override
    public void uploadFile(String employeeId, @ValidFile() MultipartFile file) throws Exception {
        var exitEmployee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() -> new BadRequestException("employee Not Found"));

        Query query = new Query(Criteria.where("metadata.employeeId").is(exitEmployee.getId()));
        GridFSFile gridFSFile = gridFsOperations.findOne(query);
        var exitFile = gridFSFileOperation(gridFSFile);

        //Exit File Control
        if (exitFile != null && exitFile.getName().equals(file.getOriginalFilename()))
            throw new BadRequestException("File Already Exist");

        DBObject dbObject = new BasicDBObject();
        dbObject.put("filename", file.getOriginalFilename());
        dbObject.put("contentType", file.getContentType());
        dbObject.put("size", file.getSize());
        dbObject.put("employeeId", exitEmployee.getId());

        //if contentType is exist remove exit file and add new file (update)
        if (exitFile != null && Objects.requireNonNull(exitFile).getContentType().equals(file.getContentType())) {
            query = new Query(Criteria.where("_id").is(exitFile.getId()));
            gridFsDelete(query);
        }

        //putFile GridFS Bucket
        gridFsOperations.store(file.getInputStream(), file.getOriginalFilename(), dbObject);
    }

    @Override
    public FileResponse getFile(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        var gridFSFile = gridFsOperations.findOne(query);
        return gridFSFileOperation(gridFSFile);
    }

    @SneakyThrows
    @Override
    public void removeFile(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        gridFsDelete(query);
    }

    private FileResponse gridFSFileOperation(GridFSFile gridFSFile) {
        return fileMapper.gridFSFileResponse(gridFSFile);
    }

    private void gridFsDelete(Query query) throws Exception {
        try {
            gridFsOperations.delete(query);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }
}
