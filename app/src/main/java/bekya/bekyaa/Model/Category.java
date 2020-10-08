package bekya.bekyaa.Model;


import bekya.bekyaa.Activites.ProductList;

import androidx.annotation.NonNull;

/**
 * Created by kunda on 10/2/2017.
 */
public class Category implements Comparable<Category> {
    private String cat_en;
    private String cat_ar;
    private String key;
    private String img;
    private int id;
    public Category() {

    }

    public Category(String cat_en, String cat_ar, String key, String img, int id) {
        this.cat_en = cat_en;
        this.cat_ar = cat_ar;
        this.key = key;
        this.img = img;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCat_en() {
        return cat_en;
    }

    public void setCat_en(String cart_en) {
        this.cat_en = cart_en;
    }

    public String getCat_ar() {
        return cat_ar;
    }

    public void setCat_ar(String cat_ar) {
        this.cat_ar = cat_ar;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @NonNull
    @Override
    public String toString() {
        if(ProductList.Language.equals("en")){
            return cat_en;
        }else {
            return cat_ar;

        }
    }

    @Override
    public int compareTo(Category category) {
        return this.id - category.getId();
    }
}
