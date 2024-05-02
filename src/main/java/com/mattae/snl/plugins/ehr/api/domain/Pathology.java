package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.*;
import com.blazebit.persistence.view.filter.ContainsIgnoreCaseFilter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity(name = "EHRPathology")
@Table(name = "ehr_pathology")
@EqualsAndHashCode(of = "name")
public class Pathology implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String code;

    private String chromosome;

    private String gene;

    private String protein;

    private Boolean active = true;

    private String info;

    @ManyToOne(fetch = FetchType.LAZY)
    private PathologyCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    private PathologyGroup group;

    @EntityView(Pathology.class)
    public interface IdView {
        @IdMapping
        Long getId();
    }

    @EntityView(Pathology.class)
    @CreatableEntityView
    public interface CreateView extends IdView {

        @AttributeFilter(ContainsIgnoreCaseFilter.class)
        String getName();

        void setName(String name);

        String getCode();

        void setCode(String code);

        String getChromosome();

        void setChromosome(String chromosome);

        String getGene();

        void setGene(String gene);

        String getProtein();

        void setProtein(String protein);

        Boolean getActive();

        void setActive(Boolean active);

        String getInfo();

        void setInfo(String info);

        PathologyCategory.IdView getCategory();

        void setCategory(PathologyCategory.IdView category);

        PathologyGroup.IdView getGroup();

        void setGroup(PathologyGroup.IdView group);
    }

    @UpdatableEntityView
    @EntityView(Pathology.class)
    public interface UpdateView extends CreateView {
        @IdMapping
        @NotNull
        Long getId();

        void setId(Long id);
    }
}
