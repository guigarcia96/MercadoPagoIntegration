# Integração de Pagamentos via QR Code do Mercado Pago

Este projeto é uma implementação de integração de pagamentos via QR Code do Mercado Pago utilizando Java com Spring Boot.

## Requisitos

- Java 11 ou superior
- Maven
- Conta no Mercado Pago com credenciais de acesso

## Configuração

1. Clone este repositório
2. Configure suas credenciais do Mercado Pago no arquivo `src/main/resources/application.properties`:
   ```properties
   mercadopago.access.token=SEU_ACCESS_TOKEN
   mercadopago.public.key=SUA_PUBLIC_KEY
   ```

## Executando o projeto

```bash
mvn spring-boot:run
```

O aplicativo estará disponível em `http://localhost:8080`.

## Endpoints

- `GET /api/payments/pix/form`: Formulário para criar um pagamento PIX
- `POST /api/payments/pix`: Endpoint REST para criar um pagamento PIX
- `POST /webhook/mercadopago`: Endpoint para receber notificações do Mercado Pago

## Fluxo do Aplicativo

1. Acesse o formulário de pagamento: `http://localhost:8080/api/payments/pix/form`
2. Preencha os dados do pagamento e clique em "Gerar QR Code PIX"
3. Escaneie o QR Code gerado com o aplicativo do seu banco ou carteira digital
4. O pagamento será processado e o status será atualizado

## Testes

Para testar em ambiente de sandbox:

1. Use as credenciais de teste fornecidas pelo Mercado Pago
2. Crie um usuário de teste (comprador e vendedor) no painel do Mercado Pago
3. Utilize as credenciais desses usuários para testar transações

## Webhook

Configure um webhook no painel do Mercado Pago para receber notificações de pagamento:
- URL: `https://seu-dominio.com/webhook/mercadopago`
- Eventos: Pagamentos
