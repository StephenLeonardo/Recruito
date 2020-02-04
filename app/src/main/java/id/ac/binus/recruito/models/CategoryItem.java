package id.ac.binus.recruito.models;

public class CategoryItem {
    private String CategoryName;

    public CategoryItem(String categoryName) {
        this.CategoryName = categoryName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
