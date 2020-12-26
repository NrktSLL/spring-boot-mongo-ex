package com.nrkt.springbootmongoex.mapper;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.nrkt.springbootmongoex.decarator.FileDecorator;
import com.nrkt.springbootmongoex.dto.response.FileResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(FileDecorator.class)
public interface FileMapper {

    @Mapping(target = "employeeId", ignore = true)
    @Mapping(target = "resource", ignore = true)
    @Mapping(target = "name", source = "filename")
    @Mapping(target = "contentType", ignore = true)
    @Mapping(target = "contentLength", source = "length")
    @Mapping(target = "id", source = "id", ignore = true)
    FileResponse gridFSFileResponse(GridFSFile gridFSFile);
}
