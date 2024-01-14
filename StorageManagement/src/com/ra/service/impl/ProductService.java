package com.ra.service.impl;

import com.ra.entity.impl.Product;
import com.ra.font_color.ConsoleColors;
import com.ra.service.IProductService;

import java.io.*;
import java.util.*;

public class ProductService implements IProductService {
    public static List<Product> products = new ArrayList<>();

    public Product findById(String id){
        return products.stream().filter(p-> p.getId().equals(id)).findFirst().orElse(null);
    }
    @Override
    public void add(Scanner scanner) {
        Product product = new Product();
        product.inputData(scanner,"new");
        products.add(product);
        System.out.println(ConsoleColors.BLUE + "Thêm thành công!" +ConsoleColors.RESET);

    }

    public boolean update(String id , Scanner sc) {
        Product updateProduct = findById(id);
        if(updateProduct == null){
            System.err.println(ConsoleColors.RED +"Không tồn tại sản phẩm mã " + id  +ConsoleColors.RESET);
            return false;
        }else
            updateProduct.inputData(sc,"update");
        System.out.println(ConsoleColors.BLUE + "Cập nhật thành công!" +ConsoleColors.RESET);
        return true;
    }

    @Override
    public boolean delete(Scanner scanner) {
        System.out.print("Nhập mã sản phẩm cần xóa: ");
        String id = scanner.nextLine();
        Product deleteProduct = findById(id);
        if(deleteProduct== null){
            System.err.println(ConsoleColors.RED  +"Không tồn tại mã sản phẩm "+ id  +ConsoleColors.RESET);
            return false;
        }else {
            products.remove(deleteProduct);
            System.out.println(ConsoleColors.BLUE + "Xóa thành công!" +ConsoleColors.RESET);
            return true;
        }
    }

    @Override
    public List<Product> findByName(String name) {
       List<Product> result = new ArrayList<>();
       if(name != null){
           products.forEach(p-> {
               if(p.getName().contains(name)){
                   result.add(p);
               }
           });
           try{
               String searchPrice = Integer.toString(Integer.parseInt(name));
               products.forEach(p-> {
                   String getImportPrice = Double.toString(p.getImportPrice());
                   String getExportPrice = Double.toString(p.getExportPrice());
                   if(getImportPrice.contains(searchPrice)||getExportPrice.contains(searchPrice)){
                       result.add(p);
                   }
               });
           }catch (NumberFormatException e){

           }
       }
       return result;
    }

    @Override
    public List<Product> readData()  {
        List<Product> readData = null;
        File fileProduct = new File(PRODUCTS_FILE);
        try{
            FileInputStream fis = new FileInputStream(fileProduct);
            ObjectInputStream ois = new ObjectInputStream(fis);
            readData = (List<Product>)ois.readObject();
            ois.close();
            fis.close();
        } catch ( Exception ex ){
            readData = new ArrayList<>();
        }
    return readData;
    }

    @Override
    public void writeData() {
        File fileProduct = new File(PRODUCTS_FILE);
        try{
            FileOutputStream fos = new FileOutputStream(fileProduct);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(products);
            oos.close();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> sortedByName() {
        List<Product> sortList = new ArrayList<>(products);
        sortList.sort(Comparator.comparing(Product::getName));
        return sortList;
    }

    @Override
    public List<Product> sortedByProfit() {
        List<Product> sortList = new ArrayList<>(products);
        sortList.sort((p1,p2) -> (int) (p2.getProfit() - p1.getProfit()));
        return sortList;
    }
    public void printTitle(){
        System.out.printf("| %s | %s | %s | %s | %s | %s | %s |\n",centerString(13,"Mã sản phẩm"), centerString(20,"Tên sản phẩm"), centerString(13,"Giá nhập") ,
                centerString(13,"Giá bán"), centerString(13,"Lợi nhuận"), centerString(30,"Mô tả"),
                centerString(15,"Trạng thái"));
    }
    public void printFooter(){
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
    }
    public static String centerString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }
}
