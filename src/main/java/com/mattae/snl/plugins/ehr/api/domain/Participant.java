package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.UpdatableEntityView;
import io.github.jbella.snl.core.api.domain.AuditableEntity;
import io.github.jbella.snl.core.api.domain.AuditableView;
import io.github.jbella.snl.core.api.domain.Person;
import io.github.jbella.snl.core.api.id.UUIDV7;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "participant")
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@SQLDelete(sql = "update participant set archived = true, last_modified_date = current_timestamp where id = ?",
    check = ResultCheckStyle.COUNT)
@Where(clause = "archived = false")
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
    @UpdatableEntityView
    public interface UpdateView extends AuditableView {
        @IdMapping
        UUID getId();

        void setId(UUID id);

        Person.UpdateView getPerson();

        void setPerson(Person.UpdateView person);

        String getRole();

        void setRole(String role);
    }
}
