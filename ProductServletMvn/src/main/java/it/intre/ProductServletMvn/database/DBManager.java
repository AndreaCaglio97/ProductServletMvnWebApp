package it.intre.ProductServletMvn.database;

import it.intre.ProductServletMvn.models.Category;
import it.intre.ProductServletMvn.models.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static it.intre.ProductServletMvn.database.AttributeManager.*;

public class DBManager {

    public static void viewTable(Connection conn)  {

        Statement stmt = null;
        String query = "SELECT  name,  price, category\n" +
                        "FROM product\n" +
                        "WHERE is_imported = true\n" +
                        "ORDER BY id_product";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Category category =  Category.valueOf(rs.getString("category"));
                System.out.println(name + "\t" + price + "\t" + category);
            }
        } catch (SQLException e ) {
            System.out.println("ERROR! query NOT successfully completed");
        } finally {
            SQLExceptionHandling(stmt);
        }
    }

    public static String productList(String[] attributes) {
        ConnectionManager connManager = ConnectionManager.getConnectionSingleton();
        Statement stmt = connManager.createStatement();
        String idProductString = checkIdProductString(attributes[0]);
        String isImportedString = checkIsImportedString(attributes[2]);
        String minimumPriceString = checkMinimumPriceString(attributes[3]);
        String maximumPriceString = checkMaximumPriceString(attributes[4]);
        String categoryString = checkCategoryString(attributes[5]);

        String query = "SELECT  *\n" +
                "FROM product\n" +
                "WHERE " + idProductString + "name like '%" + attributes[1] + "%'" + isImportedString + minimumPriceString + maximumPriceString + categoryString +
                "ORDER BY id_product";

        return tableGenerator(stmt,query);
    }

    public static String tableGenerator(Statement stmt, String query) {
        String htmlResponse = "";
        try {
            ResultSet rs = stmt.executeQuery(query);
            htmlResponse += "<html>" +
                    "<head>" +
                    "   <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css\"> " +
                    "   <link href=\"table.css\" rel=\"stylesheet\" type=\"text/css\">" +
                    "</head>" +
                    "<body>" +
                    "<table class=\"responstable\">" +
                    "<tr>\n" +
                    "    <th>Id Product</th>\n" +
                    "    <th data-th=\"Product details\"><span>Name</span></th>\n" +
                    "    <th>Is Imported</th>\n" +
                    "    <th>Price</th>\n" +
                    "    <th>Category</th>\n" +
                    "</tr>";
            while (rs.next()) {
                htmlResponse += "<tr>";
                htmlResponse += "<td>" + rs.getInt("id_product") + "</td>";
                htmlResponse += "<td>" + rs.getString("name") + "</td>";
                htmlResponse += "<td>" + rs.getBoolean("is_imported") + "</td>";
                htmlResponse += "<td>" + rs.getDouble("price") + "</td>";
                htmlResponse += "<td>" + Category.valueOf(rs.getString("category")) + "</td>";
                htmlResponse += "</tr>";
            }
            htmlResponse += "</table>" +
                    "<script src='http://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.js'></script>" +
                    "</body>" +
                    "</html>";
        } catch (SQLException e ) {
            System.out.println("ERROR! query NOT successfully completed");
        } finally {
            SQLExceptionHandling(stmt);
            return htmlResponse;
        }
    }

    public static Product productFromDB(int id_product)  {
        ConnectionManager connManager = ConnectionManager.getConnectionSingleton();
        Statement stmt = connManager.createStatement();
        Product product = null;
        String query = "SELECT  *\n" +
                "FROM product\n" +
                "WHERE id_product = " + id_product;
        try {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String name = rs.getString("name");
            boolean isImported = rs.getBoolean("is_imported");
            double price = rs.getDouble("price");
            Category category =  Category.valueOf(rs.getString("category"));
            product = new Product(name,isImported,price,category,1);
        } catch (SQLException e ) {
            System.out.println("ERROR! query NOT successfully completed");
        } finally {
            SQLExceptionHandling(stmt);
        }
        return product;
    }

    private static void SQLExceptionHandling(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e){
            System.err.println("ERROR! query NOT successfully completed");
        }
    }

    public static void inputDBProductFromFileCSV(String fileName) {
        try {
            Scanner inputStream = new Scanner(new File(fileName));
            inputStream.nextLine();
            while (inputStream.hasNextLine()) {
                readSingleProductFromFileCSV(inputStream);
            }
            inputStream.close();
        } catch(FileNotFoundException e) {
            System.out.println("Cannot find file " + fileName);
        }
    }

    private static void readSingleProductFromFileCSV(Scanner inputStream) {
        String line;
        line = inputStream.nextLine();
        String[] productAttributes = line.split(",");
        Product product = parseStringLineToProduct(productAttributes);
        insertSingleProductToDB(product);
    }

    private static Product parseStringLineToProduct(String[] productAttributes) {
        Product product = new Product();
        product.setName(productAttributes[0]);
        product.setImported(Boolean.parseBoolean(productAttributes[1]));
        product.setPrice(Double.parseDouble(productAttributes[2]));
        product.setCategory(Category.valueOf(productAttributes[3]));
        return product;
    }


    public static void insertSingleProductToDB(Product product)  {
        ConnectionManager connManager = ConnectionManager.getConnectionSingleton();
        Statement stmt = connManager.createStatement();

        String query = "INSERT INTO product(\n" +
                "\tname, is_imported, price, category)\n" +
                "\tVALUES ('" + product.getName() + "', " + product.isImported() + ", " + product.getPrice() + ", '" + product.getCategory() + "');";
        try {
            stmt.executeUpdate(query);
            System.out.println("Record has been inserted into product table!");
        } catch (SQLException e ) {
            System.err.println("ERROR! query NOT successfully completed");
            System.out.println(e.getMessage());
        } finally {
            SQLExceptionHandling(stmt);
        }
    }
}
