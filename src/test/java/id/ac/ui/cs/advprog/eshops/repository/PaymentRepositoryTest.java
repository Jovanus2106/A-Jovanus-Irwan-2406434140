package id.ac.ui.cs.advprog.eshops.repository;

import id.ac.ui.cs.advprog.eshops.model.Order;
import id.ac.ui.cs.advprog.eshops.model.Payment;
import id.ac.ui.cs.advprog.eshops.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private Payment payment;
    private Order dummyOrder;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductQuantity(1);
        products.add(product);

        dummyOrder = new Order("order-1", products, 1708560000L, "Author");
        payment = new Payment("pay-1", "BANK_TRANSFER", new HashMap<>(), dummyOrder);
    }

    @Test
    void testSaveNewPayment() {
        Payment savedPayment = paymentRepository.save(payment);
        assertEquals(payment.getId(), savedPayment.getId());

        Payment foundPayment = paymentRepository.findById("pay-1");
        assertNotNull(foundPayment);
        assertEquals("pay-1", foundPayment.getId());
    }

    @Test
    void testSaveUpdateExistingPayment() {
        paymentRepository.save(payment);

        payment.setStatus("SUCCESS");
        Payment updatedPayment = paymentRepository.save(payment);

        assertEquals("SUCCESS", updatedPayment.getStatus());
        assertEquals(1, paymentRepository.findAll().size());
    }

    @Test
    void testFindByIdNotFound() {
        Payment foundPayment = paymentRepository.findById("invalid-id");
        assertNull(foundPayment);
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment);
        Payment payment2 = new Payment("pay-2", "VOUCHER_CODE", new HashMap<>(), dummyOrder);
        paymentRepository.save(payment2);

        List<Payment> payments = paymentRepository.findAll();
        assertEquals(2, payments.size());
    }
}