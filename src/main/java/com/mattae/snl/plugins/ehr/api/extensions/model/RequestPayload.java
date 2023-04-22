package com.mattae.snl.plugins.ehr.api.extensions.model;

import com.mattae.snl.plugins.ehr.api.domain.Encounter;
import com.mattae.snl.plugins.ehr.api.domain.Patient;
import com.mattae.snl.plugins.ehr.api.domain.ReferencedData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class RequestPayload<T> {
    @NotNull
    private T data;
    @NotNull
    private Patient.IdView patient;
    @NotNull
    private Encounter.IdView encounter;
    private List<ReferencedData.UpdateView> extras = new ArrayList<>();
}
