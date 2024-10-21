package com.example.request_solver.model;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role")})
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    @Column(name = "role")
    private List<Role> roles;
}