package com.example.request_solver.to;

import com.example.request_solver.HasId;
import com.example.request_solver.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestTo implements HasId {

    Integer id;

    LocalDate createdAt;

    @NotBlank
    @Size(min = 5, max = 128)
    String title;

    @NotBlank
    @Size(min = 10, max = 128)
    String description;

    @NotNull
    Status status;

    public RequestTo(String title, String description) {
        this.title = title;
        this.description = description;
    }
}