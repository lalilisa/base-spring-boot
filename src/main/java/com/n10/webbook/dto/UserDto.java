package com.n10.webbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data

public class UserDto {

    @Schema(example = "trimai")
    @NotEmpty
    @NotNull
    String username;

    @Schema(example = "trimai")
    @NotEmpty
    @NotNull
    String password;
}
