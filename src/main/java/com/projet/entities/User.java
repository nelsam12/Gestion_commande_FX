package com.projet.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.projet.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.findByLoginAndPassword", query = "SELECT u FROM User u WHERE u.login LIKE :login AND u.password LIKE :password"),
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login LIKE :login"),
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(length = 60)
    private String name;
    @Column(length = 60)
    private String firstname;
    @Column(length = 60)
    private String login;
    @Column(length = 255)
    private String password;
    
    @Column()
    private boolean status = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Client client;

    @Enumerated(EnumType.ORDINAL)
    private Role role;
    

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", firstname=" + firstname + ", login=" + login + ", password="
                + password + ", role=" + role + "]";
    }
}
