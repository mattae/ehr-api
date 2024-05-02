package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.UpdatableEntityView;
import io.github.jbella.snl.core.api.domain.AuditableEntity;
import io.github.jbella.snl.core.api.domain.AuditableView;
import io.github.jbella.snl.core.api.domain.Person;
import io.github.jbella.snl.core.api.id.UUIDV7;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serializable;
import java.util.UUID;

@Entity(name = "EHRParticipant")
@Table(name = "ehr_participant")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = true)
@SQLDelete(sql = "update ehr_participant set archived = true, last_modified_date = current_timestamp where id = ?",
        check = ResultCheckStyle.COUNT)
@SQLRestriction("archived = false")
public class Participant extends AuditableEntity implements Serializable {
    @Id
    @UUIDV7
    private UUID id;

    @ManyToOne
    private Person person;

    @NotNull
    private String role;

    private Boolean archived = false;

    @EntityView(Participant.class)
    @CreatableEntityView
    public interface CreateView extends AuditableView {
        @IdMapping
        UUID getId();

        Person.UpdateView getPerson();

        void setPerson(Person.UpdateView person);

        String getRole();

        void setRole(String role);
    }

    @EntityView(Participant.class)
    @UpdatableEntityView
    public interface UpdateView extends CreateView {
        @IdMapping
        @NotNull
        UUID getId();

        void setId(UUID id);
    }
}
