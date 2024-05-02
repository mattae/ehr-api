package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.PrePersist;
import com.blazebit.persistence.view.*;
import io.github.jbella.snl.core.api.domain.*;
import io.github.jbella.snl.core.api.id.UUIDV7;
import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.SQLRestriction;

import java.util.Set;
import java.util.UUID;

@Entity(name = "EHRHealthProfessional")
@Table(name = "ehr_health_professional")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = true)
@SQLDelete(sql = "update ehr_health_professional set archived = true, last_modified_date = current_timestamp where id = ?",
        check = ResultCheckStyle.COUNT)
@SQLRestriction("archived = false")
public class HealthProfessional extends AuditableEntity {
    @Id
    @UUIDV7
    private UUID id;

    private String info;

    private String specialty;

    @ElementCollection
    @CollectionTable(
            name = "ehr_health_professional_specialties",
            joinColumns = @JoinColumn(name = "professional_id")
    )
    @Column(name = "specialty")
    @SQLRestriction("archived = false")
    @SQLDeleteAll(sql = "update ehr_health_professional_specialties set archived = true, last_modified_date = current_timestamp where id = ?", check = ResultCheckStyle.COUNT)
    private Set<String> specialties;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @NotNull
    private Person person;

    @ManyToOne
    @NotNull
    private Organisation organisation;

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
    @CreatableEntityView
    public interface CreateView extends IdView, AuditableView {
        String getInfo();

        void setInfo(String info);

        String getSpecialty();

        void setSpecialty(String specialty);

        Set<String> getSpecialties();

        void setSpecialties(Set<String> specialties);

        @UpdatableMapping(cascade = {
                com.blazebit.persistence.view.CascadeType.DELETE,
                com.blazebit.persistence.view.CascadeType.PERSIST,
                com.blazebit.persistence.view.CascadeType.UPDATE
        })
        Person.UpdateView getPerson();

        void setPerson(Person.UpdateView person);

        Organisation.IdView getOrganisation();

        void setOrganisation(Organisation.IdView organisation);

        Boolean getActive();

        void setActive(Boolean active);

        @PrePersist
        default void prePersist() {
            setActive(true);
        }
    }

    @EntityView(HealthProfessional.class)
    @UpdatableEntityView
    public interface UpdateView extends CreateView {
        @IdMapping
        @NotNull
        UUID getId();

        void setId(UUID id);
    }
}
