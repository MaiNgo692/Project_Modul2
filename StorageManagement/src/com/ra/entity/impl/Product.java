package com.ra.entity.impl;

import com.ra.entity.IProduct;
import com.ra.font_color.ConsoleColors;
import com.ra.service.impl.CategoryService;
import com.ra.service.impl.ProductService;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Product implements IProduct, Serializable {
    private static final long serialVersionUID = -361135733781632762L;
    private String id;
    private String name;
    private double importPrice;
    private double exportPrice;
    private double profit;
    private String description;
    private boolean status;
    private int categoryId;

    public Product() {
    }

    public Product(String id, String name, double importPrice, double exportPrice, String description, boolean status, int categoryId) {
        this.id = id;
        this.name = name;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.description = description;
        this.status = status;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public double getProfit() {
        return profit;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setId(String id) throws Exception {
        // Kiểm tra mã nhập vào là null hoặc độ dài khác 4 hoặc không bắt đầu là B thì đẩy ra Exception
        if(id == null || id.length() != 4 || !id.startsWith("P")){
            throw new Exception("Mã xản phẩm có 4 kí tự, bắt đầu bằng P");
        }
        // Kiểm tra Mã đã tồn tại hay chưa
        for(Product p: ProductService.products){
            if(p.getId().equals(id)){
                throw new Exception("Mã xản phẩm đã tồn tại!");
            }
        }
        this.id = id;
    }

    public void setName(String name) throws Exception {
        // Kiểm tra tên nhập vào nếu là null hoặc có độ dài dưới 6  ký tự hoặc có độ dài hơn 30 ký tự thì đẩy ra Exception
        if(name == null || name.length()<6 || name.length()>30){
            throw new Exception("Tên có từ 6 đến 30 ký tự!");
        }
        // Kiểm tra Tên nhập vào có trùng với các tên đã có săn trong mảng hiện có
        for(Product p: ProductService.products){
            if(p.getName().equals(name)){
                throw new Exception("Tên xản phẩm đã tồn tại!");
            }
        }
        this.name = name;
    }

    public void setImportPrice(double importPrice) throws Exception {
        if( importPrice < 0){
            throw new Exception("Giá nhập phải lớn hơn 0");
        }
        this.importPrice = importPrice;
    }

    public void setExportPrice(double exportPrice) throws Exception {
        if(exportPrice < importPrice* (1+MIN_INTEREST_RATE)){
            throw new Exception("Giá bán phải lơn hơn giá nhập "+(1+MIN_INTEREST_RATE) + " lần!");
        }
        this.exportPrice = exportPrice;
    }


    public void setDescription(String description) throws Exception {
        if(description == null || description.replace(" ","").length() ==0){
            throw new Exception("Mô tả không thể bỏ trống!");
        }
        this.description = description;
    }

    public void setStatus(String status) throws Exception {
        if(status.equals("false")||status.equals("true")){
            this.status = Boolean.parseBoolean(status);
        }else {
            throw new Exception("Trạng thái danh mục chỉ nhâp true hoặc false!");
        }
    }

    public void setCategoryId(int categoryId) throws Exception {
        // Kiểm tra categoryId nhập vào đã có sẵn hay chưa
        for (Category c : CategoryService.categories) {
            if (c.getId() == categoryId && c.getStatus()) {
                if(c.getStatus()){
                    this.categoryId = categoryId;
                    return;
                }
                throw new Exception("Danh mục mã " + categoryId +" đang không hoạt động!");
            }
        }
        throw new Exception("Không tồn tại mã danh mục " + categoryId);
    }

    @Override
    public void inputData(Scanner scanner, String select) {
        System.out.println(ConsoleColors.BLUE +"Nhập thông tin sản phẩm: "+ConsoleColors.RESET);
        boolean invalid ;
        if(select.equals("new")){
            do{
                try {
                    System.out.println(ConsoleColors.BLUE +"Nhập mã sản phẩm(gồm 4 ký tự, bắt đầu bằng 'P'): "+ConsoleColors.RESET);
                    String id = scanner.nextLine();
                    setId(id);
                    invalid= false;
                }catch (Exception ex){
                    invalid= true;
                    System.out.println(ConsoleColors.RED + ex.getMessage() + ConsoleColors.RESET);
                }
            }while (invalid);
        }
        do{
            try {
                System.out.println(ConsoleColors.BLUE +"Nhập tên sản phẩm(gồm 6-30 ký tự): "+ConsoleColors.RESET);
                String name = scanner.nextLine();
                setName(name);
                invalid=false;
            }catch (Exception ex){
                invalid= true;
                System.out.println(ConsoleColors.RED + ex.getMessage() + ConsoleColors.RESET);
            }
        }while (invalid);
        do{
            try {
                System.out.println(ConsoleColors.BLUE +"Nhập giá nhập sản phẩm: "+ConsoleColors.RESET);
                double importPrice = Double.parseDouble(scanner.nextLine());
                setImportPrice(importPrice);
                invalid=false;
            }catch (Exception ex){
                invalid= true;
                System.out.println(ConsoleColors.RED + ex.getMessage() + ConsoleColors.RESET);
            }
        }while (invalid);
        do{
            try {
                System.out.println(ConsoleColors.BLUE +"Nhập giá bán sản phẩm: "+ConsoleColors.RESET);
                double exportPrice = Double.parseDouble(scanner.nextLine());
                setExportPrice(exportPrice);
                invalid= false;
            }catch (Exception ex){
                invalid= true;
                System.out.println(ConsoleColors.RED + ex.getMessage() + ConsoleColors.RESET);
            }
        }while (invalid);
        //tính lợi nhuận
        calProfit();
        do{
            try {
                System.out.println(ConsoleColors.BLUE +"Nhập mô tả sản phẩm: "+ConsoleColors.RESET);
                String description = scanner.nextLine();
                setDescription(description);
                invalid= false;
            }catch (Exception ex){
                invalid= true;
                System.out.println(ConsoleColors.RED + ex.getMessage() + ConsoleColors.RESET);
            }
        }while (invalid);
        do{
            try {
                System.out.println(ConsoleColors.BLUE +"Nhập trạng thái sản phẩm: "+ConsoleColors.RESET);
                String status = scanner.nextLine();
                setStatus(status);
                invalid= false;
            }catch (Exception ex){
                invalid= true;
                System.out.println(ConsoleColors.RED + ex.getMessage() + ConsoleColors.RESET);
            }
        }while (invalid);
        do{
            try {
                System.out.println(ConsoleColors.BLUE +"Nhập mã danh mục sản phẩm: " +ConsoleColors.RESET);
                int categoryId = Integer.parseInt(scanner.nextLine());
                setCategoryId(categoryId);
                invalid= false;
            }catch (Exception ex){
                invalid= true;
                System.out.println(ConsoleColors.RED + ex.getMessage() + ConsoleColors.RESET);
            }
        }while (invalid);
    }

    @Override
    public void displayData() {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        System.out.printf("| %s | %s | %sđ | %sđ | %sđ | %s | %s |\n",
                centerString(13,this.id), centerString(20,this.name), centerString(12,vn.format(this.importPrice)) ,
                centerString(12,vn.format(this.exportPrice)), centerString(12,vn.format(this.profit)), centerString(30,this.description.length()>30? this.description.substring(0,27)+"...":this.description),
                centerString(15,this.status?"Hoạt động":"Không hoạt động"));
    }

    @Override
    public void calProfit() {
        this.profit = this.exportPrice - this.importPrice;
    }

    public static String centerString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }
}
