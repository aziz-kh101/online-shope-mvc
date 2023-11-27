package com.tekup.onlinestore.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotNull
    @Pattern(regexp = "\\d+(?:\\.\\d+)?",message = "should be float")
    private String price;
    @NotNull
    @Pattern(regexp = "\\d+",message = "should be float")
    @Min(1)
    private String quantity;

    private String image;
}
