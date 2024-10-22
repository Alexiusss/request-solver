package com.example.request_solver.to;

import com.example.request_solver.HasId;
import com.example.request_solver.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestTo implements HasId {
    Integer id;
    LocalDate createdAt;
    String title;
    String description;
    Status status;
}