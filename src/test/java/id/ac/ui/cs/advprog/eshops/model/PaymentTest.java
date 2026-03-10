package id.ac.ui.cs.advprog.eshops.model;

import id.ac.ui.cs.advprog.eshops.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshops.enums.PaymentStatus;
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
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Kecap");
        product.setProductQuantity(2);
        products.add(product);

        dummyOrder = new Order("order-1", products, 1708560000L, "Jovanus", "WAITING_PAYMENT");
    }

    @Test
    void testCreatePaymentVoucherCodeSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("payment-1", PaymentMethod.VOUCHER_CODE.getValue(), paymentData, dummyOrder);

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(dummyOrder, payment.getOrder());
    }

    @Test
    void testCreatePaymentVoucherCodeRejected_LengthNot16() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123");

        Payment payment = new Payment("payment-2", PaymentMethod.VOUCHER_CODE.getValue(), paymentData, dummyOrder);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejected_NotStartWithEshop() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "DISC1234ABC56789");

        Payment payment = new Payment("payment-3", PaymentMethod.VOUCHER_CODE.getValue(), paymentData, dummyOrder);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejected_Not8Digits() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCDEFGHXYZ");

        Payment payment = new Payment("payment-4", PaymentMethod.VOUCHER_CODE.getValue(), paymentData, dummyOrder);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF12345");

        Payment payment = new Payment("payment-5", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, dummyOrder);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejected_EmptyBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF12345");

        Payment payment = new Payment("payment-6", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, dummyOrder);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejected_NullReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");

        Payment payment = new Payment("payment-7", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, dummyOrder);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentUnknownMethod() {
        Map<String, String> paymentData = new HashMap<>();
        Payment payment = new Payment("payment-8", "PAYPAL", paymentData, dummyOrder);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}