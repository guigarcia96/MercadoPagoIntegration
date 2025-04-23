package com.example.mercadopago.controller;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @PostMapping("/mercadopago")
    public ResponseEntity<String> handleMercadoPagoWebhook(
            @RequestParam("type") String type,
            @RequestParam("data.id") Long paymentId) {
        
        if ("payment".equals(type)) {
            try {
                // Recupera os detalhes do pagamento
                PaymentClient client = new PaymentClient();
                Payment payment = client.get(paymentId);
                
                // Atualiza o status do pagamento em seu sistema
                updatePaymentStatus(paymentId, payment.getStatus());
                
                return ResponseEntity.ok("Notificação processada com sucesso");
            } catch (MPApiException | MPException e) {
                return ResponseEntity.internalServerError().body("Erro ao processar notificação: " + e.getMessage());
            }
        }
        
        return ResponseEntity.ok("Evento ignorado");
    }
    
    private void updatePaymentStatus(Long paymentId, String status) {
        // Implemente a lógica para atualizar o status do pagamento no seu sistema
        System.out.println("Pagamento " + paymentId + " atualizado para status: " + status);
    }
}
