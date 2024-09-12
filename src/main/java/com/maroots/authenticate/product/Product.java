package com.maroots.authenticate.product;

import com.mongodb.lang.NonNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
@Getter
@Setter
@Document(collection = "products")

public class Product {

    @Id
    private String id;

    @NonNull
    private String name;

    private BigDecimal price;
}
