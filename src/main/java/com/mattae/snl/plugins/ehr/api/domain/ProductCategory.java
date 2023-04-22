package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.UpdatableEntityView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ehr_product_category")
@Data
@EqualsAndHashCode(of = "id")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToOne
    private ProductCategory parent;

    @EntityView(ProductCategory.class)
    public interface IdView {
        @IdMapping
        Long getId();

        String getName();
    }

    @EntityView(ProductCategory.class)
    @CreatableEntityView
    public interface CreateView extends IdView {

        void setName(String name);

        IdView getParent();

        void setParent(IdView parent);
    }

    @EntityView(ProductCategory.class)
    @UpdatableEntityView
    public interface UpdateView extends CreateView {
        void setId(Long id);
    }
}
