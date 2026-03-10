package id.ac.ui.cs.advprog.eshops.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Order dummyOrder;

    @BeforeEach
    void setUp() {
        // Buat Product dummy agar List tidak kosong
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Kecap");
        product.setProductQuantity(2);
        products.add(product);

        // Buat Order menggunakan constructor yang ada di Order.java
        dummyOrder = new Order("order-1", products, 1708560000L, "Safira Sudrajat", "WAITING_PAYMENT");
    }

    @Test
    void testCreatePaymentVoucherCodeSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("payment-1", "VOUCHER_CODE", paymentData, dummyOrder);

        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(dummyOrder, payment.getOrder());
    }

    @Test
    void testCreatePaymentVoucherCodeRejected_LengthNot16() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123");

        Payment payment = new Payment("payment-2", "VOUCHER_CODE", paymentData, dummyOrder);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejected_NotStartWithEshop() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "DISC1234ABC56789");

        Payment payment = new Payment("payment-3", "VOUCHER_CODE", paymentData, dummyOrder);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejected_Not8Digits() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCDEFGHXYZ");

        Payment payment = new Payment("payment-4", "VOUCHER_CODE", paymentData, dummyOrder);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF12345");

        Payment payment = new Payment("payment-5", "BANK_TRANSFER", paymentData, dummyOrder);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejected_EmptyBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF12345");

        Payment payment = new Payment("payment-6", "BANK_TRANSFER", paymentData, dummyOrder);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejected_NullReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");

        Payment payment = new Payment("payment-7", "BANK_TRANSFER", paymentData, dummyOrder);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentUnknownMethod() {
        Map<String, String> paymentData = new HashMap<>();
        Payment payment = new Payment("payment-8", "PAYPAL", paymentData, dummyOrder);
        assertEquals("REJECTED", payment.getStatus());
    }
}