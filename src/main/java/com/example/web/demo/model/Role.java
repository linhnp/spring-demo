package com.example.web.demo.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    protected Role() {
    }

    public Role (String roleName){
        this.roleName = roleName;
    }

    public String getRoleNameWithPrefix(){
        return "ROLE_" + roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}