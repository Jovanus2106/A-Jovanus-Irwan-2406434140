package id.ac.ui.cs.advprog.eshops.service;

import id.ac.ui.cs.advprog.eshops.model.Order;
import id.ac.ui.cs.advprog.eshops.model.Payment;
import id.ac.ui.cs.advprog.eshops.model.Product;
import id.ac.ui.cs.advprog.eshops.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("order-1", products, 1708560000L, "Author", "WAITING_PAYMENT");

        paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "12345");
    }

    @Test
    void testAddPayment() {
        Payment mockPayment = new Payment("pay-1", "BANK_TRANSFER", paymentData, order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        assertNotNull(result);
        assertEquals("BANK_TRANSFER", result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccessChangesOrderStatus() {
        Payment payment = new Payment("pay-1", "BANK_TRANSFER", paymentData, order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment updatedPayment = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", updatedPayment.getStatus());
        assertEquals("SUCCESS", updatedPayment.getOrder().getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetStatusRejectedChangesOrderStatusToFailed() {
        Payment payment = new Payment("pay-1", "BANK_TRANSFER", paymentData, order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment updatedPayment = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", updatedPayment.getStatus());
        assertEquals("FAILED", updatedPayment.getOrder().getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment("pay-1", "BANK_TRANSFER", paymentData, order);
        when(paymentRepository.findById("pay-1")).thenReturn(payment);

        Payment result = paymentService.getPayment("pay-1");
        assertNotNull(result);
        assertEquals("pay-1", result.getId());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("pay-1", "BANK_TRANSFER", paymentData, order));
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();
        assertEquals(1, result.size());
    }
}