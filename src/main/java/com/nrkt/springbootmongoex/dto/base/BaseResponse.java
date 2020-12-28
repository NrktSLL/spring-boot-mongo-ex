package com.nrkt.springbootmongoex.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL, valueFilter = RepresentationModel.class)
public class BaseResponse extends RepresentationModel<BaseResponse> implements Serializable {
    @ApiModelProperty(notes = "_id", required = true)
    @JsonProperty("_id")
    @EqualsAndHashCode.Include
    String id;
}
