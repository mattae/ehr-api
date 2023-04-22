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
import io.hypersistence.utils.hibernate.type.json.JsonNodeBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Data
@SQLDelete(sql = "update referenced_data set archived = true, last_modified_date = current_timestamp where id = ?",
    check = ResultCheckStyle.COUNT)
@Where(clause = "archived = false")
public class ReferencedData extends AuditableEntity {
    @Id
    @UUIDV7
    private UUID id;

    @NotNull
    private UUID referenceKey;

    @Type(JsonNodeBinaryType.class)
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
        void setId(UUID id);
    }
}
