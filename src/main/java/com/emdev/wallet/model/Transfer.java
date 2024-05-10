package com.emdev.wallet.model;

import com.emdev.wallet.types.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transferId;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;
    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 15)
    private String description;

    private Date date;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    public Transfer(Double amount, String description , TransactionType type) {
        this.amount = amount;
        this.description = description;
        this.date = new Date(System.currentTimeMillis());
        this.type = type;
    }
}
