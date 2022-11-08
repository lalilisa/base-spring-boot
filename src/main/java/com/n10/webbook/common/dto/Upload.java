package com.n10.webbook.common.dto;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Upload {
    @Schema(format = "binary",name = "file")
    private MultipartFile file;
}
