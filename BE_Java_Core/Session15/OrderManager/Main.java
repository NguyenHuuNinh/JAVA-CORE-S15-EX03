package vn.edu.rikkei.session15.ordermanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static List<Product> productList = new ArrayList<>();
    private static Map<String, Order> orderMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n============== MENU ==============");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Xóa sản phẩm");
            System.out.println("3. Hiển thị sản phẩm");
            System.out.println("4. Tạo đơn hàng");
            System.out.println("5. Thêm sản phẩm vào đơn hàng");
            System.out.println("6. Hiển thị đơn hàng");
            System.out.println("0. Thoát");
            System.out.println("==================================");
            System.out.print("Lựa chọn của bạn: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("-> Lỗi: Vui lòng nhập một số hợp lệ!");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        addProduct(scanner);
                        break;
                    case 2:
                        deleteProduct(scanner);
                        break;
                    case 3:
                        displayProducts();
                        break;
                    case 4:
                        createOrder(scanner);
                        break;
                    case 5:
                        addProductToOrder(scanner);
                        break;
                    case 6:
                        displayOrder(scanner);
                        break;
                    case 0:
                        System.out.println("-> Đã thoát chương trình.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("-> Lựa chọn không tồn tại.");
                }
            } catch (BusinessException e) {
                System.out.println("-> [LỖI NGHIỆP VỤ] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("-> [LỖI HỆ THỐNG] " + e.getMessage());
            }
        }
    }

    private static void addProduct(Scanner scanner) throws BusinessException {
        System.out.print("Nhập ID sản phẩm: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhập tên sản phẩm: ");
        String name = scanner.nextLine();
        System.out.print("Nhập giá sản phẩm: ");
        double price = Double.parseDouble(scanner.nextLine());
        if (price <= 0) {
            throw new BusinessException("Giá sản phẩm phải lớn hơn 0!");
        }

        productList.add(new Product(id, name, price));
        System.out.println("-> Đã thêm sản phẩm thành công.");
    }

    private static void deleteProduct(Scanner scanner) throws BusinessException {
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        int id = Integer.parseInt(scanner.nextLine());

        Product target = null;
        for (Product p : productList) {
            if (p.getId() == id) {
                target = p;
                break;
            }
        }
        if (target == null) {
            throw new BusinessException("Không tìm thấy sản phẩm với ID = " + id);
        }

        productList.remove(target);
        System.out.println("-> Đã xóa sản phẩm thành công.");
    }

    private static void displayProducts() {
        if (productList.isEmpty()) {
            System.out.println("-> Danh sách sản phẩm trống.");
            return;
        }
        System.out.println("--- DANH SÁCH SẢN PHẨM ---");
        for (Product p : productList) {
            System.out.println(p.toString());
        }
    }

    private static void createOrder(Scanner scanner) {
        System.out.print("Nhập ID cho đơn hàng mới: ");
        int orderId = Integer.parseInt(scanner.nextLine());
        String key = String.valueOf(orderId);

        if (orderMap.containsKey(key)) {
            System.out.println("-> Lỗi: Đơn hàng này đã tồn tại!");
        } else {
            orderMap.put(key, new Order(orderId));
            System.out.println("-> Đã tạo đơn hàng #" + orderId + " thành công.");
        }
    }

    private static void addProductToOrder(Scanner scanner) throws BusinessException {
        System.out.print("Nhập ID đơn hàng: ");
        String orderKey = scanner.nextLine();
        if (!orderMap.containsKey(orderKey)) {
            throw new BusinessException("Đơn hàng #" + orderKey + " không tồn tại!");
        }

        System.out.print("Nhập ID sản phẩm muốn thêm: ");
        int productId = Integer.parseInt(scanner.nextLine());

        Product targetProduct = null;
        for (Product p : productList) {
            if (p.getId() == productId) {
                targetProduct = p;
                break;
            }
        }

        if (targetProduct == null) {
            throw new BusinessException("Sản phẩm với ID = " + productId + " không tồn tại!");
        }

        Order order = orderMap.get(orderKey);
        order.addProduct(targetProduct);
        System.out.println("-> Đã thêm sản phẩm '" + targetProduct.getName() + "' vào đơn hàng #" + orderKey);
    }

    private static void displayOrder(Scanner scanner) throws BusinessException {
        System.out.print("Nhập ID đơn hàng cần xem: ");
        String orderKey = scanner.nextLine();

        if (!orderMap.containsKey(orderKey)) {
            throw new BusinessException("Đơn hàng #" + orderKey + " không tồn tại!");
        }

        orderMap.get(orderKey).displayOrderDetails();
    }
}
