
import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/retail_db";
        String user = "root";
        String password = "YOUR_MYSQL_PASSWORD";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    url, user, password);

            Scanner sc = new Scanner(System.in);

            int choice;

            do {

                System.out.println("\n===== INVENTORY MANAGEMENT =====");
                System.out.println("1. Insert Products");
                System.out.println("2. Retrieve Products");
                System.out.println("3. Update Product Stock");
                System.out.println("4. Search and Update Price");
                System.out.println("5. Inventory Value and Delete");
                System.out.println("6. Exit");

                System.out.println("Enter your choice:");
                choice = sc.nextInt();

                switch (choice) {

                    // CASE 1: INSERT PRODUCTS

                    case 1:

                        String insert = "INSERT INTO Product VALUES (?, ?, ?, ?, ?)";

                        PreparedStatement ps1 =
                                con.prepareStatement(insert);

                        for (int i = 1; i <= 3; i++) {

                            System.out.println("Enter Product ID:");
                            int id = sc.nextInt();
                            sc.nextLine();

                            System.out.println("Enter Product Name:");
                            String name = sc.nextLine();

                            System.out.println("Enter Category:");
                            String category = sc.nextLine();

                            System.out.println("Enter Quantity:");
                            int qty = sc.nextInt();

                            System.out.println("Enter Price:");
                            double price = sc.nextDouble();

                            ps1.setInt(1, id);
                            ps1.setString(2, name);
                            ps1.setString(3, category);
                            ps1.setInt(4, qty);
                            ps1.setDouble(5, price);

                            try {
                                ps1.executeUpdate();
                                System.out.println("Product Inserted!");

                            } catch (SQLIntegrityConstraintViolationException e) {
                                System.out.println("Duplicate Product ID!");
                            }
                        }

                        ps1.close();
                        break;


                    // CASE 2: RETRIEVE PRODUCTS

                    case 2:

                        sc.nextLine();

                        System.out.println("Enter Category:");
                        String cat = sc.nextLine();

                        String select = "SELECT * FROM Product WHERE category = ? AND quantity < 20 ORDER BY price DESC";

                        PreparedStatement ps2 =
                                con.prepareStatement(select);

                        ps2.setString(1, cat);

                        ResultSet rs2 = ps2.executeQuery();

                        while (rs2.next()) {

                            System.out.println("ID: " +
                                    rs2.getInt("product_id"));

                            System.out.println("Name: " +
                                    rs2.getString("product_name"));

                            System.out.println("Category: " +
                                    rs2.getString("category"));

                            System.out.println("Quantity: " +
                                    rs2.getInt("quantity"));

                            System.out.println("Price: " +
                                    rs2.getDouble("price"));

                            System.out.println("------------------");
                        }

                        rs2.close();
                        ps2.close();
                        break;


                    // CASE 3: UPDATE STOCK

                    case 3:

                        System.out.println("Enter Product ID:");
                        int pid = sc.nextInt();

                        System.out.println("Enter Quantity to Add:");
                        int add = sc.nextInt();

                        String update = "UPDATE Product SET quantity = quantity + ? WHERE product_id = ?";

                        PreparedStatement ps3 =
                                con.prepareStatement(update);

                        ps3.setInt(1, add);
                        ps3.setInt(2, pid);

                        int rows = ps3.executeUpdate();

                        if (rows > 0) {
                            System.out.println("Stock Updated Successfully!");
                        } else {
                            System.out.println("Product Does Not Exist!");
                        }

                        ps3.close();
                        break;


                    // CASE 4: SEARCH AND UPDATE PRICE

                    case 4:

                        System.out.println("Enter Product ID:");
                        int searchId = sc.nextInt();

                        System.out.println("Enter New Price:");
                        double newPrice = sc.nextDouble();

                        String search = "SELECT * FROM Product WHERE product_id = ?";

                        PreparedStatement ps4 =
                                con.prepareStatement(search);

                        ps4.setInt(1, searchId);

                        ResultSet rs4 = ps4.executeQuery();

                        if (rs4.next()) {

                            rs4.close();
                            ps4.close();

                            if (newPrice > 0) {

                                String updatePrice = "UPDATE Product SET price = ? WHERE product_id = ?";

                                PreparedStatement ps5 =
                                        con.prepareStatement(updatePrice);

                                ps5.setDouble(1, newPrice);
                                ps5.setInt(2, searchId);

                                ps5.executeUpdate();

                                System.out.println("Price Updated!");

                                String display = "SELECT * FROM Product WHERE product_id = ?";

                                PreparedStatement ps6 =
                                        con.prepareStatement(display);

                                ps6.setInt(1, searchId);

                                ResultSet rs6 = ps6.executeQuery();

                                if (rs6.next()) {

                                    System.out.println("Product ID: " +
                                            rs6.getInt("product_id"));

                                    System.out.println("Name: " +
                                            rs6.getString("product_name"));

                                    System.out.println("Category: " +
                                            rs6.getString("category"));

                                    System.out.println("Quantity: " +
                                            rs6.getInt("quantity"));

                                    System.out.println("Price: " +
                                            rs6.getDouble("price"));
                                }

                                rs6.close();
                                ps6.close();
                                ps5.close();

                            } else {

                                System.out.println(
                                        "Price Must Be Greater Than Zero!");
                            }

                        } else {

                            System.out.println("Product Does Not Exist!");

                            rs4.close();
                            ps4.close();
                        }

                        break;


                    // CASE 5: INVENTORY VALUE AND DELETE

                    case 5:

                        String sum = "SELECT SUM(quantity * price) AS total_value FROM Product";

                        PreparedStatement ps7 =
                                con.prepareStatement(sum);

                        ResultSet rs7 = ps7.executeQuery();

                        if (rs7.next()) {

                            System.out.println("Total Inventory Value: " +
                                    rs7.getDouble("total_value"));
                        }

                        rs7.close();
                        ps7.close();

                        System.out.println("Enter Product ID to Delete:");
                        int deleteId = sc.nextInt();

                        String delete = "DELETE FROM Product WHERE product_id = ? AND quantity = 0";

                        PreparedStatement ps8 =
                                con.prepareStatement(delete);

                        ps8.setInt(1, deleteId);

                        int deleted = ps8.executeUpdate();

                        if (deleted > 0) {
                            System.out.println("Product Deleted Successfully!");
                        } else {
                            System.out.println(
                                    "Product Not Found or Quantity is Not Zero!");
                        }

                        ps8.close();
                        break;


                    // CASE 6: EXIT

                    case 6:

                        System.out.println("Exiting Program...");
                        break;


                    default:

                        System.out.println("Invalid Choice!");

                }

            } while (choice != 6);

            con.close();
            sc.close();

        } catch (Exception e) {

            System.out.println(e);

        }
    }
}
