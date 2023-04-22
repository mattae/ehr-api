package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.*;
import io.github.jbella.snl.core.api.domain.AuditableEntity;
import io.github.jbella.snl.core.api.domain.AuditableView;
import io.github.jbella.snl.core.api.domain.Person;
import io.github.jbella.snl.core.api.id.UUIDV7;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@SQLDelete(sql = "update related_persons set archived = true, last_modified_date = current_timestamp where id = ?",
    check = ResultCheckStyle.COUNT)
@Where(clause = "archived = false")
@Table(name = "related_persons")
public class RelatedPerson extends AuditableEntity implements Serializable {

    @Id
    @UUIDV7
    private UUID id;

    @ManyToOne
    @NotNull
    @EqualsAndHashCode.Include
    private Patient patient;

    @ManyToOne
    @NotNull
    @EqualsAndHashCode.Include
    private Person person;

    @NotNull
    private String relationship;

    private Boolean archived = false;

    @EntityView(RelatedPerson.class)
    public interface IdView {
        @IdMapping
        UUID getId();
    }

    @EntityView(RelatedPerson.class)
    public interface View extends IdView {
        Person.View getPerson();

        String getRelationship();
    }

    @EntityView(RelatedPerson.class)
    @CreatableEntityView
    public interface CreateView extends IdView, AuditableView {

        @UpdatableMapping
        @NotNull
        Person.UpdateView getPerson();

        void setPerson(Person.UpdateView person);

        @NotNull
        Patient.IdView getPatient();

        void setPatient(Patient.IdView patient);

        @NotNull
        String getRelationship();

        void setRelationship(String relationship);
    }

    @EntityView(RelatedPerson.class)
    @CreatableEntityView
    @UpdatableEntityView
    public interface UpdateView extends CreateView {
        void setId(UUID id);
    }
}
