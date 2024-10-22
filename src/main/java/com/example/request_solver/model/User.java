package com.example.request_solver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @NotBlank
    @Size(min = 5, max = 128)
    private String username;

    @NotBlank
    @Size(min = 5, max = 128)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role")})
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    @Column(name = "role")
    private List<Role> roles;
}