package me.trung.projectdemotwo.Model;

import java.io.Serializable;

public class SubCategory implements Serializable {
    private int SubCategoryId;
    private int CategoryId;
    private String SubCategoryName;
    private String SubCategoryIcon;

    public SubCategory(int subCategoryId, int categoryId, String subCategoryName, String subCategoryIcon) {
        SubCategoryId = subCategoryId;
        CategoryId = categoryId;
        SubCategoryName = subCategoryName;
        SubCategoryIcon = subCategoryIcon;
    }

    public int getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        SubCategoryId = subCategoryId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getSubCategoryName() {
        return SubCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        SubCategoryName = subCategoryName;
    }

    public String getSubCategoryIcon() {
        return SubCategoryIcon;
    }

    public void setSubCategoryIcon(String subCategoryIcon) {
        SubCategoryIcon = subCategoryIcon;
    }
}
