package com.emdev.wallet.model;

import com.emdev.wallet.types.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_movements")
public class Movements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movementsId;


    private Double amount;
    private String description;
    private Date date;

    @Enumerated(EnumType.STRING)
    private TransactionType type;


    @OneToMany(targetEntity = Incomings.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Incomings> incomings = new ArrayList<>();


    @OneToMany(targetEntity = Expenses.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Expenses> expenses = new ArrayList<>();

    public Movements(Double amount, String description, Date date ,TransactionType type) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }


}
