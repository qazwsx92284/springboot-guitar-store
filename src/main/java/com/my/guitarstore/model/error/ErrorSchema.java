package com.my.guitarstore.model.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel(description = "Schema for describing a potential error when the API is hit")
@Data
@AllArgsConstructor
public class ErrorSchema {

    @ApiModelProperty(name = "message", value = "The error message that is returned", example = "Error encountered while processing the request")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    @ApiModelProperty(name = "path", value = "API URI being hit", example = "v1/api/create/id:432")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String path;

    @ApiModelProperty(name = "timestamp",
            value = "Timestamp of when the error occurred. The format of timestamp is yyyy-MM-dd'T'HH:mm:ss.SSS",
            example = "2022-03-07T08:22:22.888")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalDateTime timestamp;
}
