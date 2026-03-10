package id.ac.ui.cs.advprog.eshops.controller;

import id.ac.ui.cs.advprog.eshops.model.Order;
import id.ac.ui.cs.advprog.eshops.model.Payment;
import id.ac.ui.cs.advprog.eshops.model.Product;
import id.ac.ui.cs.advprog.eshops.service.OrderService;
import id.ac.ui.cs.advprog.eshops.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create")
    public String showCreateOrderForm() {
        return "orderCreate";
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam String author) {
        List<Product> products = new ArrayList<>();
        Product dummy = new Product();
        dummy.setProductId("prod-1"); dummy.setProductQuantity(1);
        products.add(dummy);

        Order order = new Order(String.valueOf(System.currentTimeMillis()), products, System.currentTimeMillis(), author, "WAITING_PAYMENT");
        orderService.createOrder(order);
        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String showHistoryForm() {
        return "orderHistorySearch";
    }

    @PostMapping("/history")
    public String showAuthorHistory(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("author", author);
        model.addAttribute("orders", orders);
        return "orderHistoryList";
    }

    @GetMapping("/pay/{orderId}")
    public String showPaymentPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "orderPay";
    }

    @PostMapping("/pay/{orderId}")
    public String processPayment(@PathVariable String orderId,
                                 @RequestParam String method,
                                 @RequestParam Map<String, String> requestParams,
                                 Model model) {
        Order order = orderService.findById(orderId);

        Map<String, String> paymentData = new HashMap<>();
        if ("VOUCHER_CODE".equals(method)) {
            paymentData.put("voucherCode", requestParams.get("voucherCode"));
        } else if ("BANK_TRANSFER".equals(method)) {
            paymentData.put("bankName", requestParams.get("bankName"));
            paymentData.put("referenceCode", requestParams.get("referenceCode"));
        }

        Payment payment = paymentService.addPayment(order, method, paymentData);
        model.addAttribute("paymentId", payment.getId());
        return "orderPaySuccess";
    }
}