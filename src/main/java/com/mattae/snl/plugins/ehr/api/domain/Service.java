package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.view.*;
import io.github.jbella.snl.core.api.domain.AuditableEntity;
import io.github.jbella.snl.core.api.domain.AuditableView;
import io.github.jbella.snl.core.api.domain.Organisation;
import io.github.jbella.snl.core.api.id.UUIDV7;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "service")
@EqualsAndHashCode(of = "name", callSuper = true)
@Data
@SQLDelete(sql = "update service set archived = true, last_modified_date = current_timestamp where id = ?",
        check = ResultCheckStyle.COUNT)
@Where(clause = "archived = false")
public class Service extends AuditableEntity implements Serializable {

    @Id
    @UUIDV7
    private UUID id;

    @NotNull
    private String name;

    private String description;

    private Boolean active;

    @ManyToOne
    private Organisation organisation;

    private Boolean archived = false;

    @EntityView(Service.class)
    public interface IdView {
        @IdMapping
        UUID getId();

        @NotBlank
        String getName();
    }

    @EntityView(Service.class)
    @CreatableEntityView
    public interface CreateView extends IdView, AuditableView {
        void setName(String name);

        String getDescription();

        void setDescription(String description);

        @NotNull
        Boolean getActive();

        void setActive(Boolean active);

        Organisation.IdView getOrganisation();

        void setOrganisation(Organisation.IdView organisation);

        @PostCreate
        default void postCreate() {
            setActive(true);
        }
    }

    @EntityView(Service.class)
    @UpdatableEntityView
    public interface UpdateView extends CreateView {
        @NotNull
        @IdMapping
        UUID getId();

        void setId(UUID id);
    }
}
