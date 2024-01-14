package com.ra.entity.impl;

import com.ra.entity.ICategory;
import com.ra.font_color.ConsoleColors;
import com.ra.service.impl.CategoryService;

import java.io.Serializable;
import java.util.Scanner;

public class Category implements ICategory, Serializable {
    private static final long serialVersionUID = -2742004532166689190L;
    private int id;
    private String name;
    private String description;
    private boolean status;

    public Category() {
    }

    public Category(int id, String name, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getStatus() {
        return status;
    }

    public void setId() {
        //id phải là số nguyên lớn hơn 0, duy nhất
        int maxId =0;
        for (Category c : CategoryService.categories) {
            if (c.getId() > maxId) {
                maxId = c.getId();
            }
        }
        this.id = maxId + 1;
    }

    public void setName(String name) throws Exception {
        // Kiểm tra tên nhập vào nếu là null hoặc có độ dài dưới 6  ký tự hoặc có độ dài hơn 30 ký tự thì đẩy ra Exception
        if(name == null || name.length()<6 || name.length()>30){
            throw new Exception("Tên có từ 6 đến 30 ký tự!");
        }
        // Kiểm tra Tên nhập vào có trùng với các tên đã có săn trong mảng hiện có
        for (Category c : CategoryService.categories) {
            if (c.getName().equals(name)) {
                throw new Exception("Tên danh mục đã tồn tại!");
            }
        }
        this.name = name;
    }

    public void setDescription(String description) throws Exception {
        if(description == null){
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
    @Override
    public void inputData(Scanner scanner) {
//        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT +"Nhập thông tin danh mục:"  +ConsoleColors.RESET);
        boolean invalid = true;
        do{
            try{
                System.out.print(ConsoleColors.BLUE + "Nhập tên danh mục: "  +ConsoleColors.RESET);
                String name = scanner.nextLine();
                setName(name);
                invalid = false;
            }catch (Exception ex){
                System.out.println(ConsoleColors.RED + ex.getMessage() + ConsoleColors.RESET);
            }
        }while (invalid);
        do{
            try{
                System.out.print(ConsoleColors.BLUE +"Nhập mô tả danh mục: " +ConsoleColors.RESET);
                String description = scanner.nextLine();
                setDescription(description);
                invalid = false;
            }catch (Exception ex){
                System.out.println(ConsoleColors.RED + ex.getMessage() + ConsoleColors.RESET);
            }
        }while (invalid);
        do{
            try{
                System.out.print(ConsoleColors.BLUE +"Nhập trạng thái danh mục: " +ConsoleColors.RESET);
                String status = scanner.nextLine();
                setStatus(status);
                invalid = false;
            }catch (Exception ex){
                System.out.println(ConsoleColors.RED + ex.getMessage() + ConsoleColors.RESET);
                invalid = true;
            }
        }while (invalid);
        if(this.id ==0){
            setId();
        }
    }

    @Override
    public void displayData() {
        System.out.printf("|%s|%s|%s|%s|\n" ,
                centerString(12,Integer.toString(this.id)),centerString(30,this.name),
                centerString(30,this.description.length()>30? this.description.substring(0,27)+"...":this.description),
                centerString(17,this.status?"Hoạt đông":"Không hoạt động"));
    }
    public static String centerString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }
}
