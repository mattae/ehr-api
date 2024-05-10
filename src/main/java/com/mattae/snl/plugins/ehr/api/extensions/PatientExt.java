package com.mattae.snl.plugins.ehr.api.extensions;

import io.github.jbella.snl.core.api.domain.Organisation;
import io.github.jbella.snl.core.api.domain.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PatientExt {
    private UUID id;

    private Boolean active;

    private String maritalStatus;

    private String bloodType;

    private String rhesus;

    private String hb;

    private String criticalInfo;

    private String generalInfo;

    private String education;

    private String occupation;

    private Organisation.IdView facility;

    private Person.CreateView person;
}
