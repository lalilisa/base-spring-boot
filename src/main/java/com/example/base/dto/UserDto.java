package com.example.base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
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
