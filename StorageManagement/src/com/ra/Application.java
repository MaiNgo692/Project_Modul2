package com.ra;

import com.ra.entity.impl.Category;
import com.ra.entity.impl.Product;
import com.ra.font_color.ConsoleColors;
import com.ra.service.impl.CategoryService;
import com.ra.service.impl.ProductService;

import java.util.List;
import java.util.Scanner;
public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CategoryService categoryService = new CategoryService();
        CategoryService.categories = categoryService.readData();
        ProductService productService = new ProductService();
        ProductService.products = productService.readData();
        do {
            System.out.println("===== QUẢN LÝ KHO =====");
            System.out.println("1. Quản lý danh mục");
            System.out.println("2. Quản lý sản phẩm");
            System.out.println("3. Thoát");
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Nhập lựa chọn: " +ConsoleColors.RESET);
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        categoryManagement(scanner, categoryService);
                        break;
                    case 2:
                        productManagement(scanner, productService);
                        break;
                    case 3:
                        System.out.println("Bạn đã chọn thoát!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println(ConsoleColors.RED +"Hãy chọn từ 1 đến 3!"+ ConsoleColors.RESET);
                        break;
                }
            }catch (NumberFormatException ex){
                System.out.println(ConsoleColors.RED +"Hãy nhập vào 1 số!" +ConsoleColors.RESET);
            }
        }while (true);
    }

    static void categoryManagement(Scanner sc,CategoryService categoryService){
        boolean isExit = true;
        do{
            System.out.println("===== QUẢN LÝ DANH MỤC =====");
            System.out.println("1. Thêm mới danh mục");
            System.out.println("2. Cập nhật danh mục");
            System.out.println("3. Xóa danh mục");
            System.out.println("4. Tìm kiếm danh mục theo tên danh mục");
            System.out.println("5. Thống kê số lượng sản phẩm đang có trong danh mục");
            System.out.println("6. Quay lại");
//            System.out.println("7. Hiển thị toàn bộ danh mục");
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Nhập lựa chọn: " +ConsoleColors.RESET);
            try{
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1:
                        System.out.println("1. Thêm mới danh mục");
                        categoryService.add(sc);
                        categoryService.writeData();
                        break;
                    case 2:
                        System.out.println("2. Cập nhật danh mục");
                        System.out.print(ConsoleColors.BLUE +"Nhập mã danh mục cần cập nhật: " +ConsoleColors.RESET);
                        int id = Integer.parseInt(sc.nextLine());
                        categoryService.update(id,sc);
                        categoryService.writeData();
                        break;
                    case 3:
                        System.out.println("3. Xóa danh mục");
                        categoryService.delete(sc);
                        categoryService.writeData();
                        break;
                    case 4:
                        System.out.println("4. Tìm kiếm danh mục theo tên danh mục");
                        System.out.print(ConsoleColors.BLUE +"Nhập tên danh mục cần tìm kiếm: " +ConsoleColors.RESET);
                        String name = sc.nextLine();
                        categoryService.printTitle();
                        for(Category c: categoryService.findByName(name)){
                            c.displayData();
                        }
                        categoryService.printFooter();
                        break;
                    case 5:
                        System.out.println("5. Thống kê số lượng sản phẩm đang có trong danh mục");
                        categoryService.statisticProductInCategory();
                        break;
                    case 6:
                        System.out.println("6. Quay lại");
                        isExit = false;
                        break;
//                    case 7:
//                        categoryService.printTitle();
//                        for(Category c: CategoryService.categories){
//                            c.displayData();
//                        }
//                        categoryService.printFooter();
//                        break;
                    default:
                        System.out.println(ConsoleColors.RED +"Hãy chọn từ 1 đến 6!" + ConsoleColors.RESET);
                        break;
                }
            }catch (NumberFormatException ex){
                System.out.println(ConsoleColors.RED +"Hãy nhập vào 1 số!"  +ConsoleColors.RESET);
                isExit = true;
            }

        }while (isExit);
    }

    static void productManagement(Scanner sc,ProductService productService){
        boolean isExit = true;
        do{
            System.out.println("===== QUẢN LÝ SẢN PHẨM =====");
            System.out.println("1. Thêm mới sản phẩm");
            System.out.println("2. Cập nhật sản phẩm");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("4. Hiển thị sản phẩm theo tên A-Z");
            System.out.println("5. Hiển thị sản phẩm thoe lợi nhuận từ cao-thấp");
            System.out.println("6. Tìm kiếm sản phẩm");
            System.out.println("7. Quay lại");
//            System.out.println("8. Hiển thị toàn bộ sản phẩm");
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT +"Nhập lựa chọn: " +ConsoleColors.RESET);
            try{
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1:
                        System.out.println("1. Thêm mới sản phẩm");
                        productService.add(sc);
                        productService.writeData();
                        break;
                    case 2:
                        System.out.println("2. Cập nhật sản phẩm");
                        System.out.print(ConsoleColors.BLUE +"Nhập mã sản phẩm cần cập nhật: " +ConsoleColors.RESET);
                        String id = sc.nextLine();
                        productService.update(id,sc);
                        productService.writeData();
                        break;
                    case 3:
                        System.out.println("3. Xóa sản phẩm");
                        productService.delete(sc);
                        productService.writeData();
                        break;
                    case 4:
                        System.out.println("4. Hiển thị sản phẩm theo tên A-Z");
                        productService.printTitle();
                        List<Product> sortedList = productService.sortedByName();
                        sortedList.forEach(Product::displayData);
                        break;
                    case 5:
                        System.out.println("5. Hiển thị sản phẩm thoe lợi nhuận từ cao-thấp");
                        productService.printTitle();
                        List<Product> sortedProfitList = productService.sortedByProfit();
                        sortedProfitList.forEach(Product::displayData);
                        productService.printFooter();
                        break;
                    case 6:
                        System.out.println("6. Tìm kiếm sản phẩm");
                        System.out.println(ConsoleColors.BLUE +"Nhập từ khóa tìm kiếm: "+ConsoleColors.RESET);
                        String key = sc.nextLine();
                        List<Product> sortedByNameList = productService.findByName(key);
                        productService.printTitle();
                        sortedByNameList.forEach(Product::displayData);
                        productService.printFooter();
                        break;
                    case 7:
                        System.out.println("7. Quay lại");
                        isExit = false;
                        break;
//                    case 8:
//                        productService.printTitle();
//                        for(Product p : ProductService.products){
//                            p.displayData();
//                        }
//                        productService.printFooter();
//                        break;
                    default:
                        System.out.println(ConsoleColors.RED +"Hãy chọn từ 1 đến 7!" +ConsoleColors.RESET);
                        break;
                }
            }catch (NumberFormatException ex){
                System.out.println(ConsoleColors.RED +"Hãy nhập vào 1 số!" +ConsoleColors.RESET);
                isExit = true;
            }
        }while (isExit);
    }
}
