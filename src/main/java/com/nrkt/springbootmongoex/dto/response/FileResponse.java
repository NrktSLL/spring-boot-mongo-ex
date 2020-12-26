package com.nrkt.springbootmongoex.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@ApiModel(value = "FileResponse")
@Relation(collectionRelation = "fileResponses", itemRelation = "fileResponse")
public class FileResponse {
    @JsonProperty("_id")
    String id;

    String name;

    String employeeId;

    String contentType;

    Long contentLength;
    @JsonIgnore
    GridFsResource resource;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Europe/Istanbul")
    Date uploadDate;
}
