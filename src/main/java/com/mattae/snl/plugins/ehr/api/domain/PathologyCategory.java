package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.UpdatableEntityView;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "pathology_category")
@Data
public class PathologyCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @ManyToOne
    private PathologyCategory parent;

    @EntityView(PathologyCategory.class)
    public interface View {
        @IdMapping
        Long getId();

        String getName();

    }

    @EntityView(PathologyCategory.class)
    @UpdatableEntityView
    @CreatableEntityView
    public interface UpdateView extends View {
        void setId(Long id);

        void setName(String name);

        View getParent();

        void setParent(View parent);
    }
}
