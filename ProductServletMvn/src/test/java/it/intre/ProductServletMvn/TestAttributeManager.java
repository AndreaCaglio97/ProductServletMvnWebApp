package it.intre.ProductServletMvn.test;

import org.junit.Test;

import static it.intre.ProductServletMvn.database.AttributeManager.*;
import static org.junit.Assert.assertEquals;

public class TestAttributeManager {
    @Test
    public void emptyIdProductReturnEmptyStringTest() {
        assertEquals("",checkIdProductString(""));
    }

    @Test
    public void notEmptyIdProductReturnStringForSelectTest() {
        assertEquals("id_product = 3 and\n",checkIdProductString("3"));
    }

    @Test
    public void allIsImportedReturnEmptyStringTest() {
        assertEquals("",checkIsImportedString("all"));
    }

    @Test
    public void trueIsImportedReturnStringForSelectTest() {
        assertEquals("and is_imported = true\n",checkIsImportedString("true"));
    }

    @Test
    public void falseIsImportedReturnStringForSelectTest() {
        assertEquals("and is_imported = false\n",checkIsImportedString("false"));
    }

    @Test
    public void emptyMinimumPriceReturnEmptyStringTest() {
        assertEquals("",checkMinimumPriceString(""));
    }

    @Test
    public void notEmptyMinimumPriceReturnStringForSelectTest() {
        assertEquals(" and price >= 5\n",checkMinimumPriceString("5"));
    }

    @Test
    public void emptyMaximumPriceReturnEmptyStringTest() {
        assertEquals("",checkMaximumPriceString(""));
    }

    @Test
    public void notEmptyMaximumPriceReturnStringForSelectTest() {
        assertEquals(" and price <= 90\n",checkMaximumPriceString("90"));
    }

    @Test
    public void ALLCategoryReturnEmptyStringTest() {
        assertEquals("",checkCategoryString("ALL"));
    }

    @Test
    public void FOODCategoryReturnStringForSelectTest() {
        assertEquals("and category = 'FOOD' \n",checkCategoryString("FOOD"));
    }

    @Test
    public void BOOKCategoryReturnStringForSelectTest() {
        assertEquals("and category = 'BOOK' \n",checkCategoryString("BOOK"));
    }

    @Test
    public void MEDICINECategoryReturnStringForSelectTest() {
        assertEquals("and category = 'MEDICINE' \n",checkCategoryString("MEDICINE"));
    }

    @Test
    public void GENERALCategoryReturnStringForSelectTest() {
        assertEquals("and category = 'GENERAL' \n",checkCategoryString("GENERAL"));
    }

    @Test
    public void NOT_GENERALCategoryReturnStringForSelectTest() {
        assertEquals("and category = 'NOT_GENERAL' \n",checkCategoryString("NOT_GENERAL"));
    }
}
