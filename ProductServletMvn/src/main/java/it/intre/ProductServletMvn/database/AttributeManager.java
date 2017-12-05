package it.intre.ProductServletMvn.database;

public class AttributeManager {
    public static String checkIdProductString(String idProduct) {
        String idProductString = "";
        if(!idProduct.equals("")) {
            idProductString = "id_product = " + idProduct + " and\n";
        }
        return idProductString;
    }

    public static String checkIsImportedString(String isImported) {
        String isImportedString = "";
        if(!isImported.equals("all")) {
            isImportedString = "and is_imported = " + isImported + "\n";
        }
        return isImportedString;
    }

    public static String checkMinimumPriceString(String minimumPrice) {
        String minimumPriceString = "";
        if(!minimumPrice.equals("")) {
            minimumPriceString = " and price >= " + minimumPrice + "\n";
        }
        return minimumPriceString;
    }

    public static String checkMaximumPriceString(String maximumPrice) {
        String maximumPriceString = "";
        if(!maximumPrice.equals("")) {
            maximumPriceString = " and price <= " + maximumPrice + "\n";
        }
        return maximumPriceString;
    }

    public static String checkCategoryString(String category) {
        String categoryString = "";
        if(!category.equals("ALL")) {
            categoryString = "and category = '" + category + "' \n";
        }
        return categoryString;
    }
}
