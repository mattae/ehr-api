package com.mattae.snl.plugins.ehr.api.domain;

import com.blazebit.persistence.JoinType;
import com.blazebit.persistence.SubqueryInitiator;
import com.blazebit.persistence.view.*;
import com.blazebit.persistence.view.filter.ContainsIgnoreCaseFilter;
import com.blazebit.persistence.view.filter.EqualFilter;
import com.mattae.snl.plugins.ehr.api.domain.enumerations.BloodType;
import com.mattae.snl.plugins.ehr.api.domain.enumerations.HB;
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
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "EHRPatient")
@Table(name = "ehr_patient")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@SQLDelete(sql = "update ehr_patient set archived = true, last_modified_date = current_timestamp where id = ?",
    check = ResultCheckStyle.COUNT)
@SQLRestriction("archived = false")
@EntityListeners(AuditingEntityListener.class)
public class Patient extends AuditableEntity implements Serializable {
    @Id
    @UUIDV7
    @EqualsAndHashCode.Include
    protected UUID id;

    private Boolean active = true;

    @NotNull
    private String maritalStatus;

    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    private String rhesus;

    @Enumerated(EnumType.STRING)
    private HB hb;

    private String criticalInfo;

    private String generalInfo;

    private String education;

    private String occupation;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Organisation organisation;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @NotNull
    @EqualsAndHashCode.Include
    private Person person;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Set<RelatedPerson> relatedPersons = new HashSet<>();

    private Boolean archived = false;

    @EntityView(Patient.class)
    public interface IdView {
        @IdMapping
        @AttributeFilter(EqualFilter.class)
        UUID getId();
    }

    @CreatableEntityView
    @EntityView(Patient.class)
    public interface CreateView extends IdView, AuditableView {
        BloodType getBloodType();

        void setBloodType(BloodType type);

        String getMaritalStatus();

        void setMaritalStatus(String status);

        String getRhesus();

        void setRhesus(String rhesus);

        HB getHb();

        void setHb(HB hb);

        String getCriticalInfo();

        void setCriticalInfo(String info);

        String getGeneralInfo();

        void setGeneralInfo(String info);

        String getEducation();

        void setEducation(String education);

        String getOccupation();

        void setOccupation(String occupation);

        Organisation.IdView getOrganisation();

        void setOrganisation(Organisation.IdView organisation);

        @UpdatableMapping(cascade = {
            com.blazebit.persistence.view.CascadeType.DELETE,
            com.blazebit.persistence.view.CascadeType.PERSIST,
            com.blazebit.persistence.view.CascadeType.UPDATE
        })
        Person.UpdateView getPerson();

        void setPerson(Person.UpdateView person);

        @MappingInverse(removeStrategy = InverseRemoveStrategy.REMOVE)
        @UpdatableMapping(cascade = {
            com.blazebit.persistence.view.CascadeType.DELETE,
            com.blazebit.persistence.view.CascadeType.PERSIST,
            com.blazebit.persistence.view.CascadeType.UPDATE
        })
        Set<RelatedPerson.UpdateView> getRelatedPersons();

        void setRelatedPersons(Set<RelatedPerson.UpdateView> relatedPersons);
    }

    @EntityView(Patient.class)
    @UpdatableEntityView
    public interface UpdateView extends CreateView {
        @NotNull
        @IdMapping
        UUID getId();

        void setId(UUID id);
    }

    @EntityView(Patient.class)
    public interface PatientShortView {
        @IdMapping
        UUID getId();

        @Mapping("person.sex")
        String getSex();

        @Mapping("person.photoUrl")
        String getPhotoUrl();

        @Mapping("person.name.givenName")
        @AttributeFilter(ContainsIgnoreCaseFilter.class)
        String getGivenName();

        @Mapping("person.name.middleName")
        @AttributeFilter(ContainsIgnoreCaseFilter.class)
        String getMiddleName();

        @Mapping("person.name.familyName")
        @AttributeFilter(ContainsIgnoreCaseFilter.class)
        String getFamilyName();

        @Mapping("person.party.identifiers")
        Set<IdentifierView> getIdentifiers();

        @MappingSubquery(HospitalNumberProvider.class)
        String getHospitalNumber();

        @AttributeFilter(EqualFilter.class)
        Boolean getActive();

        @Mapping("person.dateOfBirth")
        LocalDate getBirthDate();

        class HospitalNumberProvider implements SubqueryProvider {
            @Override
            public <T> T createSubquery(SubqueryInitiator<T> subqueryBuilder) {
                return subqueryBuilder.from(Patient.class, "ident")
                    .join("person.party.identifiers", "ids", JoinType.LEFT)
                    .select("ids.value")
                    .where("ids.type").eq("HOSPITAL_NUMBER")
                    .where("id").eqExpression("EMBEDDING_VIEW(id)")
                    .end();
            }
        }

        @EntityView(Identifier.class)
        interface IdentifierView {
            String getType();

            String getRegister();

            @AttributeFilter(ContainsIgnoreCaseFilter.class)
            String getValue();
        }
    }
}
