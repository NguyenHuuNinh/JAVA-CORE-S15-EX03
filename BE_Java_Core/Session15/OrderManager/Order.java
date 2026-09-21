package vn.edu.rikkei.session15.ordermanager;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private List<Product> products;

    public Order(int orderId) {
        this.orderId = orderId;
        this.products = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double calculateTotal() {
        double total = 0;
        for (Product p : products) {
            total += p.getPrice();
        }
        return total;
    }

    public void displayOrderDetails() {
        System.out.println("--- CHI TIẾT ĐƠN HÀNG #" + orderId + " ---");
        if (products.isEmpty()) {
            System.out.println("Đơn hàng chưa có sản phẩm nào.");
        } else {
            for (Product p : products) {
                System.out.println("- " + p.toString());
            }
            System.out.printf("=> TỔNG TIỀN: %,.0f VNĐ\n", calculateTotal());
        }
    }
}
