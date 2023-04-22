package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.*;
import com.blazebit.persistence.view.filter.GreaterOrEqualFilter;
import com.blazebit.persistence.view.filter.LessOrEqualFilter;
import io.github.jbella.snl.core.api.domain.AuditableEntity;
import io.github.jbella.snl.core.api.domain.AuditableView;
import io.github.jbella.snl.core.api.domain.Organisation;
import io.github.jbella.snl.core.api.id.UUIDV7;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "encounter")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = true)
@SQLDelete(sql = "update encounter set archived = true, last_modified_date = current_timestamp where id = ?",
        check = ResultCheckStyle.COUNT)
@Where(clause = "archived = false")
public class Encounter extends AuditableEntity implements Serializable {
    @Id
    @UUIDV7
    private UUID id;

    @NotNull
    private String type;

    @NotNull
    private String priority;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    private Encounter partOf;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organisation organisation;

    @ManyToMany
    @JoinTable(
            name = "encounter_reasons",
            joinColumns = @JoinColumn(name = "encounter_id"),
            inverseJoinColumns = @JoinColumn(name = "pathology_id")
    )
    @WhereJoinTable(clause = "archived = false")
    @SQLDeleteAll(sql = "update encounter_reasons set archived = true, last_modified_date = current_timestamp where id = ?",
            check = ResultCheckStyle.COUNT)
    private Set<Pathology> reason;

    @ManyToMany
    @JoinTable(
            name = "encounter_participants",
            joinColumns = @JoinColumn(name = "encounter_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    @WhereJoinTable(clause = "archived = false")
    @SQLDeleteAll(sql = "update encounter_participants set archived = true, last_modified_date = current_timestamp where id = ?",
            check = ResultCheckStyle.COUNT)
    private Set<Participant> participants;

    private Boolean archived = false;

    @EntityView(Encounter.class)
    public interface IdView {
        @IdMapping
        UUID getId();
    }

    @EntityView(Encounter.class)
    @CreatableEntityView
    public interface CreateView extends IdView, AuditableView {
        @NotNull
        String getType();

        void setType(String type);

        @NotNull
        String getPriority();

        void setPriority(String priority);

        @AttributeFilter(GreaterOrEqualFilter.class)
        @NotNull
        LocalDateTime getStartDate();

        void setStartDate(LocalDateTime startDate);

        @AttributeFilter(LessOrEqualFilter.class)
        LocalDateTime getEndDate();

        void setEndDate(LocalDateTime endDate);

        @NotNull
        Patient.IdView getPatient();

        void setPatient(Patient.IdView patient);

        Organisation.IdView getOrganisation();

        void setOrganisation(Organisation.IdView organisation);

        Set<Participant.UpdateView> getParticipants();

        void setParticipants(Set<Participant.UpdateView> participants);

        Set<Pathology.IdView> getReason();

        void setReason(Set<Pathology.IdView> reason);
    }

    @EntityView(Encounter.class)
    @UpdatableEntityView
    public interface UpdateView extends CreateView {
        void setId(UUID id);
    }
}
