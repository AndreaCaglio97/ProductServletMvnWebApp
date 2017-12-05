package it.intre.ProductServletMvn.models;

public enum Category {
    FOOD,
    BOOK,
    MEDICINE,
    GENERAL,
    NOT_GENERAL;

    private static Category[] values = Category.values();

    public static Category getCategory(int i) {
        return values[i - 1];
    }
}
