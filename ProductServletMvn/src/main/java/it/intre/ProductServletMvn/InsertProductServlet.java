package it.intre.ProductServletMvn;


import it.intre.ProductServletMvn.models.Category;
import it.intre.ProductServletMvn.models.Product;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static it.intre.ProductServletMvn.database.DBManager.*;

public class InsertProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Inside doPost");
        Product product = getFormFields(request);
        insertSingleProductToDB(product);
        printHtmlResponse(response, product);
    }

    private static Product getFormFields(HttpServletRequest request) {
        String name = request.getParameter("name");
        boolean isImported = Boolean.parseBoolean(request.getParameter("is_imported"));
        double price = Double.parseDouble(request.getParameter("price"));
        Category category = Category.valueOf(request.getParameter("category"));
        return new Product(name,isImported,price,category,1);
    }

    private static void printHtmlResponse(HttpServletResponse response, Product product) throws IOException {
        PrintWriter writer = response.getWriter();
        String htmlResponse = htmlResponseMaker(product);
        writer.println(htmlResponse);
    }

    private static String htmlResponseMaker(Product product) {
        String htmlResponse = "<html>" +
                "<head>" +
                "   <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css\"> " +
                "   <link href=\"table.css\" rel=\"stylesheet\" type=\"text/css\">" +
                "</head>" +
                "<body>" +
                "<h1>New Product in DB</h1>" +
                "<table class=\"responstable\">" +
                "<tr>\n" +
                "    <th>Name</th>\n" +
                "    <th data-th=\"Product details\"><span>Is Imported</span></th>\n" +
                "    <th>Price</th>\n" +
                "    <th>Category</th>\n" +
                "</tr>";
        htmlResponse += "<tr>";
        htmlResponse += "<td>" + product.getName() + "</td>";
        htmlResponse += "<td>" + product.isImported() + "</td>";
        htmlResponse += "<td>" + product.getPrice() + "</td>";
        htmlResponse += "<td>" + product.getCategory() + "</td>";
        htmlResponse += "</tr>";
        htmlResponse += "</table>" +
                "<script src='http://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.js'></script>" +
                "</body>" +
                "</html>";
        return htmlResponse;
    }
}