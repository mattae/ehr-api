package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.PrePersist;
import com.blazebit.persistence.view.*;
import io.github.jbella.snl.core.api.domain.AuditableEntity;
import io.github.jbella.snl.core.api.domain.AuditableView;
import io.github.jbella.snl.core.api.domain.Person;
import io.github.jbella.snl.core.api.domain.Name;
import io.github.jbella.snl.core.api.id.UUIDV7;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@SQLDelete(sql = "update health_professional set archived = true, last_modified_date = current_timestamp where id = ?",
    check = ResultCheckStyle.COUNT)
@Where(clause = "archived = false")
public class HealthProfessional extends AuditableEntity {
    @Id
    @UUIDV7
    private UUID id;

    private String info;

    @NotNull
    private String specialty;

    @ElementCollection
    @CollectionTable(
        name = "health_professional_specialties",
        joinColumns = @JoinColumn(name = "professional_id")
    )
    @Column(name = "specialty")
    @Where(clause = "archived = false")
    @SQLDeleteAll(sql = "update health_professional_specialties set archived = true, last_modified_date = current_timestamp where id = ?", check = ResultCheckStyle.COUNT)
    private Set<String> specialties;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @NotNull
    private Person person;

    private Boolean archived = false;

    private Boolean active = true;

    @EntityView(HealthProfessional.class)
    public interface IdView {
        @IdMapping
        UUID getId();
    }

    @EntityView(HealthProfessional.class)
    public interface View extends IdView {
        String getInfo();

        String getSpecialty();

        @Mapping("person.name")
        Name.View getName();

        @Mapping("person.sex")
        String getSex();

        @Mapping("person.email")
        String getEmail();

        @Mapping("person.phone")
        String getPhone();

        @Mapping("person.photoUrl")
        String getPhotoUrl();
    }

    @EntityView(HealthProfessional.class)
    @UpdatableEntityView
    @CreatableEntityView
    public interface UpdateView extends IdView, AuditableView {
        @IdMapping
        @NotNull
        UUID getId();

        void setId(UUID id);

        String getInfo();

        String getSpecialty();

        void setSpecialty(String specialty);

        void setInfo(String info);

        Set<String> getSpecialties();

        void setSpecialties(Set<String> specialties);

        @UpdatableMapping(cascade = {
            com.blazebit.persistence.view.CascadeType.DELETE,
            com.blazebit.persistence.view.CascadeType.PERSIST,
            com.blazebit.persistence.view.CascadeType.UPDATE
        })
        Person.UpdateView getPerson();

        void setPerson(Person.UpdateView person);

        Boolean getActive();

        void setActive(Boolean active);

        @PrePersist
        default void prePersist() {
            setActive(true);
        }
    }
}
