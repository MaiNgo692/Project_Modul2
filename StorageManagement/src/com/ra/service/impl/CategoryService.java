package com.ra.service.impl;

import com.ra.entity.impl.Category;
import com.ra.entity.impl.Product;
import com.ra.font_color.ConsoleColors;
import com.ra.service.ICategoryService;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CategoryService implements ICategoryService {
    public static List<Category> categories = new ArrayList<>();

    public Category findById(int id){
        return categories.stream().filter(c-> c.getId() == id).findFirst().orElse(null);
    }
    @Override
    public void statisticProductInCategory() {
        Map<Integer, Long>  map = ProductService.products.stream().collect(Collectors.groupingBy(Product::getCategoryId, Collectors.counting()));
        System.out.printf("|%s|%s|\n",centerString(30,"Tên danh mục"), centerString(20,"Số lượng"));
        categories.forEach(c -> {
            boolean isHasProduct = false;
            for(Map.Entry<Integer, Long> entry : map.entrySet()){
                if(c.getId()== entry.getKey()){
                    isHasProduct = true;
                    System.out.printf("|%s|%s|\n",centerString(30,c.getName()) ,centerString(20,Long.toString(entry.getValue())));
                }
            }
            if(!isHasProduct){
                System.out.printf("|%s|%s|\n",centerString(30,c.getName()) ,centerString(20,Long.toString(0)));
            }
        });
    }

    @Override
    public void add(Scanner scanner) {
        Category category = new Category();
        category.inputData(scanner);
        categories.add(category);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Thêm thành công!" +ConsoleColors.RESET);
    }


    public boolean update(int id, Scanner sc) {
        Category updateCategory = findById(id);
        if(updateCategory== null){
            System.out.println(ConsoleColors.RED + "Không tìm thấy danh mục mã " + id  +ConsoleColors.RESET);
            return false;
        }else {
            updateCategory.inputData(sc);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Cập nhật thành công!" +ConsoleColors.RESET);
            return true;
        }
    }

    @Override
    public boolean delete(Scanner sc) {
        System.out.print(ConsoleColors.BLUE +"Nhập mã danh mục cần xóa: "+ConsoleColors.RESET);
        int deleteId = Integer.parseInt(sc.nextLine());
        Category deleteCategory = findById(deleteId);
        boolean canDelete = true;
        if(deleteCategory == null){
            System.out.println(ConsoleColors.RED +"Không tồn tại mã danh mục " + deleteId +ConsoleColors.RESET);
            return false;
        }else {
            for(Product p: ProductService.products){
                if(p.getCategoryId() == deleteId){
                    canDelete = false;
                }
            }
            if(canDelete){
                categories.remove(deleteCategory);
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Xóa thành công!" +ConsoleColors.RESET);
                return true;
            }else {
                System.out.println(ConsoleColors.RED + "Danh mục đang có sản phẩm tham chiếu!" );
                System.out.println("Xóa không thành công!" +ConsoleColors.RESET);
                return false;
            }
        }
    }

    @Override
    public List<Category> findByName(String name) {
        List<Category> result = new ArrayList<>();
        categories.forEach(c -> {
            if(c.getName().contains(name))
                result.add(c);
        });
        return result;
    }


    @Override
    public List<Category> readData() {
        List<Category> readData = null;
        try{
            File fileCategory = new File(CATEGORY_FILE);
            FileInputStream fis = new FileInputStream(fileCategory);
            ObjectInputStream ois = new ObjectInputStream(fis);
            readData = (List<Category>)ois.readObject();
            ois.close();
            fis.close();
        }catch (Exception ex ){
            readData= new ArrayList<>();
        }
        return readData;
    }

    @Override
    public void writeData() {
        try{
            File fileCategory = new File(CATEGORY_FILE);
            FileOutputStream fos = new FileOutputStream(fileCategory);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(categories);
            oos.close();
            fos.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void printTitle(){
        System.out.printf("|%s|%s|%s|%s|\n",centerString(12,"Mã danh mục"), centerString(30,"Tên danh mục"),
                centerString(30,"Mô tả") ,centerString(17,"Trạng thái"));
    }
    public void printFooter(){
        System.out.println("-----------------------------------------------------------------------------------------------");
    }
    public static String centerString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }
}
