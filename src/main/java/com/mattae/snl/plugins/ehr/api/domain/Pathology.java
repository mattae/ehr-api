package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.*;
import com.blazebit.persistence.view.filter.ContainsIgnoreCaseFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "pathology")
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

        PathologyCategory.View getCategory();

        void setCategory(PathologyCategory.View category);

        PathologyGroup.View getGroup();

        void setGroup(PathologyGroup.View group);
    }

    @UpdatableEntityView
    @EntityView(Pathology.class)
    public interface UpdateView extends CreateView {
        void setId(Long id);
    }
}
