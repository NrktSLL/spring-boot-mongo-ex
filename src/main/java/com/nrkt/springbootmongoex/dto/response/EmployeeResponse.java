package com.nrkt.springbootmongoex.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nrkt.springbootmongoex.dto.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "EmployeeResponse")
@Relation(collectionRelation = "employees", itemRelation = "employee")
@Builder
public class EmployeeResponse extends BaseResponse {

    @NotBlank
    @ApiModelProperty(notes = "Employee Firstname", example = "Ali", required = true)
    String firstName;

    @ApiModelProperty(notes = "Employee Second name", example = "Nrkt")
    String secondName;

    @NotBlank
    @ApiModelProperty(notes = "Employee Surname", example = "SLL", required = true)
    String lastName;

    @Email
    @ApiModelProperty(notes = "Employee Email", example = "aa@bb.com", required = true)
    String email;

    @ApiModelProperty(notes = "Hire Date", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Istanbul")
    @JsonProperty("date")
    Date hiredDate;
}
