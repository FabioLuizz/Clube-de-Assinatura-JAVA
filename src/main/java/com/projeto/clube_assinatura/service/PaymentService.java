package com.projeto.clube_assinatura.service;

import com.projeto.clube_assinatura.dto.SubscriptionDTO;
import com.projeto.clube_assinatura.model.Plan;
import com.projeto.clube_assinatura.model.User;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    public String createCheckoutSession(User user, Plan plan, SubscriptionDTO subscriptionDTO) throws StripeException {

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("brl")
                .setUnitAmount(plan.getPrice().multiply(BigDecimal.valueOf(100)).longValue()) // em centavos
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(plan.getName())
                                .setDescription(plan.getDescription())
                                .build()
                )
                .build();

        SessionCreateParams.LineItem item = SessionCreateParams.LineItem.builder()
                .setPriceData(priceData) // <- aqui está a correção!
                .setQuantity(1L)
                .build();

        lineItems.add(item);

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/payment/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:8080/payment/cancel")
                .addAllLineItem(lineItems)
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }
}
