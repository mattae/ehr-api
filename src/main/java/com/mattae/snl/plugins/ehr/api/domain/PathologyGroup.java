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

@Entity(name = "EHRPathologyGroup")
@Table(name = "ehr_pathology_group")
@Getter
@Setter
public class PathologyGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String code;

    @NotNull
    @Column(nullable = false)
    private String description;

    private String info;

    @EntityView(PathologyGroup.class)
    public interface IdView {
        @IdMapping
        Long getId();

        String getName();
    }

    @EntityView(PathologyGroup.class)
    @CreatableEntityView
    public interface CreateView extends IdView {
        @NotNull
        String getName();

        void setName(String name);

        @NotNull
        String getCode();

        void setCode(String code);

        @NotNull
        String getDescription();

        void setDescription(String description);

        String getInfo();

        void setInfo(String info);
    }

    @EntityView(PathologyGroup.class)
    @UpdatableEntityView
    @CreatableEntityView
    public interface UpdateView extends CreateView {
        @IdMapping
        @NotNull
        Long getId();

        void setId(Long id);
    }
}
