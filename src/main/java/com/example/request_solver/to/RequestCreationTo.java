package com.example.request_solver.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreationTo {
    @NotBlank
    @Size(min = 5, max = 128)
    String title;

    @NotBlank
    @Size(min = 10, max = 128)
    String description;
}
