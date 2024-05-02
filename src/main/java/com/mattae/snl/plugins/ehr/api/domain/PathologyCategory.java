package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.UpdatableEntityView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity(name = "EHRPathologyCategory")
@Table(name = "ehr_pathology_category")
@Getter
@Setter
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
    public interface IdView {
        @IdMapping
        Long getId();

        String getName();
    }

    @EntityView(PathologyCategory.class)
    @CreatableEntityView
    public interface CreateView extends IdView {
        @NotNull
        String getName();

        void setName(String name);

        IdView getParent();

        void setParent(IdView parent);
    }

    @EntityView(PathologyCategory.class)
    @UpdatableEntityView
    @CreatableEntityView
    public interface UpdateView extends CreateView {
        @IdMapping
        @NotNull
        Long getId();

        void setId(Long id);
    }
}
