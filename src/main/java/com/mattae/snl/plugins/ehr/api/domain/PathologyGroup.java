package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "pathology_group")
@Data
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
    public interface View {
        @IdMapping
        Long getId();

        String getName();

        String getCode();
    }
}
