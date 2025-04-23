package com.example.mercadopago.service;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.example.mercadopago.dto.PixPaymentRequest;
import com.example.mercadopago.dto.PixPaymentResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class PixPaymentService {

    public PixPaymentResponse createPixPayment(PixPaymentRequest request) {
        try {
            // Cria o cliente de pagamento
            PaymentClient client = new PaymentClient();
            
            // Configura os dados de identificação do pagador
            IdentificationRequest identification = IdentificationRequest.builder()
                .type(request.getPayerIdentificationType())
                .number(request.getPayerIdentificationNumber())
                .build();
            
            // Configura os dados do pagador
            PaymentPayerRequest payer = PaymentPayerRequest.builder()
                .email(request.getPayerEmail())
                .firstName(request.getPayerFirstName())
                .lastName(request.getPayerLastName())
                .identification(identification)
                .build();
            
            // Cria o pedido de pagamento
            PaymentCreateRequest paymentRequest = PaymentCreateRequest.builder()
                .transactionAmount(BigDecimal.valueOf(request.getTransactionAmount()))
                .description(request.getDescription())
                .paymentMethodId("pix")
                .payer(payer)
                .dateOfExpiration(OffsetDateTime.now().plusHours(24).withOffsetSameInstant(ZoneOffset.of("-03:00")))
                .build();
            
            // Executa o pedido e obtém o pagamento
            Payment payment = client.create(paymentRequest);
            
            // Mapeia para o objeto de resposta
            return mapToResponse(payment);
            
        } catch (MPApiException | MPException e) {
            throw new RuntimeException("Erro ao criar pagamento PIX: " + e.getMessage(), e);
        }
    }
    
    private PixPaymentResponse mapToResponse(Payment payment) {
        PixPaymentResponse response = new PixPaymentResponse();
        response.setId(payment.getId());
        response.setStatus(payment.getStatus());
        
        // Extrai os dados do QR Code - adaptado para estrutura atual do SDK
        if (payment.getPointOfInteraction() != null && 
            payment.getPointOfInteraction().getTransactionData() != null) {
            
            response.setQrCodeBase64(payment.getPointOfInteraction().getTransactionData().getQrCodeBase64());
            response.setQrCode(payment.getPointOfInteraction().getTransactionData().getQrCode());
            response.setTicketUrl(payment.getPointOfInteraction().getTransactionData().getTicketUrl());
        }
        
        return response;
    }
}
