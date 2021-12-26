package com.shabunya.carrent.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long user_id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private boolean isActive;

    @Column
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole userRole;

    @Column
    private BigDecimal balance;
}
