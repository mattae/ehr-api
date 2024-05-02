package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.UpdatableEntityView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "EHRProduct")
@Table(name = "ehr_product")
@Getter
@Setter
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

        ProductCategory.IdView getCategory();

        void setCategory(ProductCategory.IdView category);
    }

    @EntityView(Product.class)
    @UpdatableEntityView
    public interface UpdateView extends CreateView {
        @IdMapping
        @NotNull
        Long getId();

        void setId(Long id);
    }
}
