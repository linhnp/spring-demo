package com.example.web.demo.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "user_ip", nullable = false)
    private String userIp;

    @Column(name = "user_account")
    private String userAccount;

    @Column(name = "is_active")
    private boolean isActive;

    // FetchType.EAGER is not the best solution for loading many-to-many
    // A person in stackoverflow suggests to add Session to Transaction manager instead
    // but I basically don't have time for that
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public User(String userIp) {
        this.userIp = userIp;
        this.isActive = true;
    }

    public User() {
    }

    public String getUserIp() {
        return userIp;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public boolean isActive() {
        return isActive;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}


