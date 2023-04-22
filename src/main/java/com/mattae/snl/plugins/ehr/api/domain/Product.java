package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ehr_product")
@Data
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private Double listPrice;

    private Double costPrice;

    @NotNull
    private String type;

    @NotNull
    @ManyToOne
    private ProductCategory category;

    @EntityView(Product.class)
    public interface IdView {
        @IdMapping
        Long getId();
    }

    @EntityView(Product.class)
    @CreatableEntityView
    public interface CreateView extends IdView {

        String getName();

        void setName(String name);

        Double getListPrice();

        void setListPrice(Double listPrice);

        Double getCostPrice();

        void setCostPrice(Double costPrice);

        String getType();

        void setType(String type);

        @UpdatableMapping
        ProductCategory.UpdateView getCategory();

        void setCategory(ProductCategory.UpdateView category);
    }

    @EntityView(Product.class)
    @UpdatableEntityView
    public interface UpdateView extends CreateView {
        void setId(Long id);
    }
}
