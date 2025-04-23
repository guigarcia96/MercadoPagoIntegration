package com.example.mercadopago.controller;

import com.example.mercadopago.dto.PixPaymentRequest;
import com.example.mercadopago.dto.PixPaymentResponse;
import com.example.mercadopago.service.PixPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/payments")
public class PixController {

    private final PixPaymentService pixPaymentService;
    
    @Autowired
    public PixController(PixPaymentService pixPaymentService) {
        this.pixPaymentService = pixPaymentService;
    }
    
    @PostMapping("/pix")
    @ResponseBody
    public ResponseEntity<PixPaymentResponse> createPixPayment(@RequestBody PixPaymentRequest request) {
        PixPaymentResponse response = pixPaymentService.createPixPayment(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/pix/form")
    public String showPaymentForm() {
        return "payment-form";
    }
    
    @PostMapping("/pix/checkout")
    public String processPayment(@ModelAttribute PixPaymentRequest request, Model model) {
        PixPaymentResponse response = pixPaymentService.createPixPayment(request);
        model.addAttribute("payment", response);
        return "payment-qrcode";
    }
}
