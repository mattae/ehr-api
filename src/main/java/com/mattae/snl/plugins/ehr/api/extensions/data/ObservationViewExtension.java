package com.mattae.snl.plugins.ehr.api.extensions.data;

import com.mattae.snl.plugins.ehr.api.domain.Patient;
import com.mattae.snl.plugins.ehr.api.extensions.ApplicableExtension;

public interface ObservationViewExtension extends ApplicableExtension {
    String getTitle();

    default String getIcon() {
        return "";
    }

    default String getTooltip() {
        return "";
    }

    @Override
    default boolean applicableTo(Patient.IdView patient) {
        return true;
    }
}
