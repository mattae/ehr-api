package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.UpdatableEntityView;
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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "EHRReferencedData")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Getter
@Setter
@Table(name = "ehr_referenced_data")
@SQLDelete(sql = "update ehr_referenced_data set archived = true, last_modified_date = current_timestamp where id = ?",
        check = ResultCheckStyle.COUNT)
@SQLRestriction("archived = false")
public class ReferencedData extends AuditableEntity {
    @Id
    @UUIDV7
    private UUID id;

    @NotNull
    private UUID referenceKey;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    @NotNull
    private JsonNode data;

    @NotNull
    private String type;

    private Boolean archived = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Organisation organisation;

    @EntityView(ReferencedData.class)
    @CreatableEntityView
    public interface CreateView extends AuditableView {
        @IdMapping
        UUID getId();

        UUID getReferenceKey();

        void setReferenceKey(UUID key);

        String getType();

        void setType(String type);

        JsonNode getData();

        void setData(JsonNode data);

        Organisation.IdView getOrganisation();

        void setOrganisation(Organisation.IdView organisation);
    }

    @EntityView(ReferencedData.class)
    @UpdatableEntityView
    public interface UpdateView extends CreateView {
        @IdMapping
        @NotNull
        UUID getId();

        void setId(UUID id);
    }
}
