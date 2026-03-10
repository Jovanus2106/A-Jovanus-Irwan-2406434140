package id.ac.ui.cs.advprog.eshops.service;

import id.ac.ui.cs.advprog.eshops.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshops.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshops.enums.PaymentStatus;
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

        order = new Order("order-1", products, 1708560000L, "Author", OrderStatus.WAITING_PAYMENT.getValue());

        paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "12345");
    }

    @Test
    void testAddPayment() {
        Payment mockPayment = new Payment("pay-1", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        Payment result = paymentService.addPayment(order, PaymentMethod.BANK_TRANSFER.getValue(), paymentData);

        assertNotNull(result);
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccessChangesOrderStatus() {
        Payment payment = new Payment("pay-1", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment updatedPayment = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), updatedPayment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), updatedPayment.getOrder().getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetStatusRejectedChangesOrderStatusToFailed() {
        Payment payment = new Payment("pay-1", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment updatedPayment = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), updatedPayment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), updatedPayment.getOrder().getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment("pay-1", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, order);
        when(paymentRepository.findById("pay-1")).thenReturn(payment);

        Payment result = paymentService.getPayment("pay-1");
        assertNotNull(result);
        assertEquals("pay-1", result.getId());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("pay-1", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, order));
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();
        assertEquals(1, result.size());
    }
}