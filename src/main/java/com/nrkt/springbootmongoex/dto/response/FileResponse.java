package com.nrkt.springbootmongoex.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nrkt.springbootmongoex.dto.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "FileResponse")
@Relation(collectionRelation = "fileResponses", itemRelation = "fileResponse")
@Builder
public class FileResponse extends BaseResponse {

    String name;

    String employeeId;

    String contentType;

    Long contentLength;
    @JsonIgnore
    transient GridFsResource resource;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Istanbul")
    Date uploadDate;
}
