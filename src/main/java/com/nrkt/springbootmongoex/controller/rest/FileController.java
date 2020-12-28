package com.nrkt.springbootmongoex.controller.rest;

import com.nrkt.springbootmongoex.dto.response.FileResponse;
import com.nrkt.springbootmongoex.error.ApiError;
import com.nrkt.springbootmongoex.service.fileoperation.FileService;
import com.nrkt.springbootmongoex.validator.ValidFile;
import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.InputStreamResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "v1/files")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Api(tags = "File")
public class FileController {

    FileService fileService;

    @GetMapping("employeefiles/{id}")
    @ApiOperation("Get All Files Employee")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Done!"),
            @ApiResponse(code = 500, message = "Internal Server Error!", response = ApiError.class),
            @ApiResponse(code = 406, message = "Not Acceptable !", response = ApiError.class),
            @ApiResponse(code = 400, message = "Bad Request!", response = ApiError.class),
    })
    public CollectionModel<FileResponse> getEmployeeFileList(
            @ApiParam(value = "Employee Number")
            @PathVariable String id) {

            return CollectionModel.of(fileService.getFiles(id),
                    linkTo(methodOn(FileController.class).getEmployeeFileList(id))
                            .withSelfRel()
                            .withDeprecation("Get All Files Employee"));
    }

    @SneakyThrows
    @PutMapping("/upload/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("File Upload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Done!"),
            @ApiResponse(code = 500, message = "Internal Server Error!", response = ApiError.class),
            @ApiResponse(code = 406, message = "Not Acceptable !", response = ApiError.class),
            @ApiResponse(code = 400, message = "Bad Request!", response = ApiError.class)
    })
    public void fileUpload(
            @ApiParam(value = "Employee Number")
            @PathVariable String id,
            @Validated @ValidFile @RequestPart("file") MultipartFile file) {

        fileService.uploadFile(id, file);
    }

    @SneakyThrows
    @GetMapping("view/{id}")
    @ApiOperation("Get File")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error!", response = ApiError.class),
            @ApiResponse(code = 406, message = "Not Acceptable !", response = ApiError.class),
            @ApiResponse(code = 400, message = "Bad Request!", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found!", response = ApiError.class)
    })
    public ResponseEntity<InputStreamResource> getFile(
            @ApiParam(value = "File Number")
            @PathVariable String id) {

        var source = fileService.getFile(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(source.getContentType()))
                .contentLength(source.getContentLength())
                .header("Content-disposition", "attachment; filename=" + source.getName())
                .body(source.getResource());
    }

    @SneakyThrows
    @GetMapping("/download/{id}")
    @ApiOperation("Download File")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error!", response = ApiError.class),
            @ApiResponse(code = 406, message = "Not Acceptable !", response = ApiError.class),
            @ApiResponse(code = 400, message = "Bad Request!", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found!", response = ApiError.class)
    })
    public ResponseEntity<InputStreamResource> downloadFile(
            @ApiParam(value = "File Number")
            @PathVariable String id) {

        var source = fileService.getFile(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(source.getContentLength())
                .header("Content-disposition", "attachment; filename=" + source.getName())
                .body(source.getResource());
    }
}
