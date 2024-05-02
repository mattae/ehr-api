package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.*;
import com.blazebit.persistence.view.filter.EqualFilter;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.jbella.snl.core.api.domain.AuditableEntity;
import io.github.jbella.snl.core.api.domain.AuditableView;
import io.github.jbella.snl.core.api.domain.Organisation;
import io.github.jbella.snl.core.api.id.UUIDV7;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "EHRObservation")
@Table(name = "ehr_observation")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = true)
@SQLDelete(sql = "update ehr__observation set archived = true, last_modified_date = current_timestamp where id = ?",
        check = ResultCheckStyle.COUNT)
@SQLRestriction("archived = false")
@ToString(of = {"id", "encounter", "organisation"})
public class Observation extends AuditableEntity {
    @Id
    @UUIDV7
    private UUID id;

    @NotNull
    @ManyToOne
    private Patient patient;

    @ManyToOne
    @NotNull
    private Organisation organisation;

    @NotNull
    @ManyToOne
    private Encounter encounter;

    @NotNull
    private String type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    @NotNull
    private JsonNode data;

    private Boolean archived = false;

    @EntityView(Observation.class)
    public interface IdView {
        @IdMapping
        UUID getId();
    }

    @EntityView(Observation.class)
    public interface View extends IdView {
        @AttributeFilter(EqualFilter.class)
        @NotNull
        String getType();

        Patient.IdView getPatient();

        Encounter.IdView getEncounter();

        @NotNull
        JsonNode getData();
    }

    @EntityView(Observation.class)
    @CreatableEntityView
    public interface CreateView extends View, AuditableView {

        void setType(String type);

        void setPatient(Patient.IdView patient);

        void setEncounter(Encounter.IdView encounter);

        Organisation.IdView getOrganisation();

        void setOrganisation(Organisation.IdView organisation);

        void setData(JsonNode data);
    }

    @EntityView(Observation.class)
    @UpdatableEntityView
    public interface UpdateView extends CreateView {
        @NotNull
        @IdMapping
        UUID getId();

        void setId(UUID id);
    }
}
