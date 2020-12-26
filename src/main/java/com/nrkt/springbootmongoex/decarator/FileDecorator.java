package com.nrkt.springbootmongoex.decarator;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.nrkt.springbootmongoex.dto.response.FileResponse;
import com.nrkt.springbootmongoex.exception.GridFsException;
import com.nrkt.springbootmongoex.mapper.FileMapper;
import lombok.Setter;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;

import java.util.Objects;

public abstract class FileDecorator implements FileMapper {

    @Setter(onMethod = @__({@Autowired}))
    FileMapper fileMapper;

    @Setter(onMethod = @__({@Autowired}))
    GridFsOperations gridFsOperations;

    @Override
    public FileResponse gridFSFileResponse(GridFSFile file) {
        if (file == null) return null;
        Document fileMetadata = Objects.requireNonNull(file.getMetadata());

        String filename = fileMetadata.getString("filename");
        String contentType = fileMetadata.getString("contentType");
        String employeeId = fileMetadata.getString("employeeId");

        GridFsResource resource = gridFsOperations.getResource(file);
        try {
            return FileResponse.builder()
                    .contentType(contentType)
                    .contentLength(file.getLength())
                    .uploadDate(file.getUploadDate())
                    .employeeId(employeeId)
                    .resource(resource)
                    .name(filename)
                    .id(file.getObjectId().toHexString())
                    .build();
        } catch (Exception exception) {
            throw new GridFsException(exception.getMessage());
        }
    }
}
