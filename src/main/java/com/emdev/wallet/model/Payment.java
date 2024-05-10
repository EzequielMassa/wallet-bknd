package com.emdev.wallet.model;

import com.emdev.wallet.types.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;
    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 15)
    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Date date;

    public Payment(Double amount, String description) {
        this.amount = amount;
        this.description = description;
        this.date = new Date(System.currentTimeMillis());
        this.type = TransactionType.PAYMENT;
    }
}
