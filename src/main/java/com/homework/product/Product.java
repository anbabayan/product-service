package com.homework.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

//using mongoDB
@Document
public class Product  implements Serializable {
    @Id
    private String productId;
    private String name;
    private Long price;
    private boolean isPurchased;

    public Product() {
    }

    public Product(String productId, String name, Long price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.isPurchased = false;
    }

    public Product(String name, Long price) {
        this.name = name;
        this.price = price;
        this.isPurchased = false;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
