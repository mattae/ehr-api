package com.mattae.snl.plugins.ehr.api.extensions.encounter;

import com.mattae.snl.plugins.ehr.api.domain.Patient;
import io.github.jbella.snl.core.api.domain.Organisation;
import org.pf4j.ExtensionPoint;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ObservationResultProviderExtension extends ExtensionPoint {

    boolean providesFor(String category, String code);

    default Optional<ObservationResult> getLastResult(Patient.IdView patient, Organisation.IdView org, String category,
                                                      String code) {
        return Optional.empty();
    }

    default Optional<ObservationResult> getFirstResult(Patient.IdView patient, Organisation.IdView org, String category,
                                                       String code) {
        return Optional.empty();
    }

    default Optional<ObservationResult> getLastResultAt(Patient.IdView patient, Organisation.IdView org, String category,
                                                        String code, LocalDate at) {
        return Optional.empty();
    }

    default Optional<ObservationResult> getFirstResultFrom(Patient.IdView patient, Organisation.IdView org,
                                                           String category, String code, LocalDate from) {
        return Optional.empty();
    }

    default List<ObservationResult> getResultsBetween(Patient.IdView patient, Organisation.IdView org, String category,
                                                      String code, LocalDate start, LocalDate end) {
        return Collections.emptyList();
    }

    record ObservationResult(Object result, LocalDate date) {
    }
}
