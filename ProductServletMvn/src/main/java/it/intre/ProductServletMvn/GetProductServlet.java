package it.intre.ProductServletMvn;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static it.intre.ProductServletMvn.database.DBManager.*;

public class GetProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Inside doPost");
        String[] attributes = getAttributes(request);
        printAttributes(attributes);
        printHtmlResponse(response, attributes);
    }

    private static String[] getAttributes(HttpServletRequest request) {
        String[] attributes = new String[6];
        attributes[0] = request.getParameter("id_product");
        attributes[1] = request.getParameter("name");
        attributes[2] = request.getParameter("is_imported");
        attributes[3] = request.getParameter("minimum_price");
        attributes[4] = request.getParameter("maximum_price");
        attributes[5] = request.getParameter("category");
        return attributes;
    }

    private static void printAttributes(String[] attributes) {
        for(String c : attributes) {
            System.out.println(c);
        }
    }

    private void printHtmlResponse(HttpServletResponse response, String[] attributes) throws IOException {
        PrintWriter writer = response.getWriter();
        String htmlResponse = productList(attributes);
        writer.println(htmlResponse);
    }
}
