package com.example.mercadopago.dto;

import lombok.Data;

@Data
public class PixPaymentRequest {
    private Float transactionAmount;
    private String description;
    private String payerEmail;
    private String payerFirstName;
    private String payerLastName;
    private String payerIdentificationType; // CPF ou CNPJ
    private String payerIdentificationNumber;
}
