package com.mattae.snl.plugins.ehr.api.extensions.checklist;

import com.mattae.snl.plugins.ehr.api.extensions.PatientExt;
import io.github.jbella.snl.core.api.extensions.OrderedExtension;

import java.util.UUID;

public interface ChecklistProviderExtension extends OrderedExtension {
    boolean applicableTo(PatientExt patient);

    String getName();

    String getType();

    UUID getFormTemplateId();

    default String getDescription() {
        return "";
    }
}
