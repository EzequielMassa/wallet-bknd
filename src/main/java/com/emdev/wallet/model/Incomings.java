package com.emdev.wallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_incomings")
public class Incomings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer incomingId;

    private Date date;
    private Double amount;
    private Integer accountId;


    public Incomings(Date date, Double amount, Integer accountId) {
        this.date = date;
        this.amount = amount;
        this.accountId = accountId;
    }

}
