package com.avishek.clouddrive.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20 , name = "role_name")
    private  AppRole roleName;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private Set<User> users = new HashSet<>();
    public Role(Integer id, AppRole roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Role(AppRole roleName) {
        this.roleName = roleName;
    }
}
