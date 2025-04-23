package com.example.mercadopago.dto;

import lombok.Data;

@Data
public class PixPaymentResponse {
    private Long id;
    private String status;
    private String qrCodeBase64;
    private String qrCode;
    private String ticketUrl;
}
