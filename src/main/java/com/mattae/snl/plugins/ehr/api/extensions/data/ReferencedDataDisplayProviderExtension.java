package com.mattae.snl.plugins.ehr.api.extensions.data;

import io.github.jbella.snl.core.api.domain.Organisation;
import org.pf4j.ExtensionPoint;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public interface ReferencedDataDisplayProviderExtension extends ExtensionPoint {
    boolean applicableTo(String type, String name);

    List<DisplayElement> getDisplayElements(UUID patientId, Organisation.IdView org, String lang);

    default int getPriority() {
        return 100;
    }

    default List<String> getRoles() {
        return Collections.emptyList();
    }

    record DisplayElement(String label, String value) {
    }
}
