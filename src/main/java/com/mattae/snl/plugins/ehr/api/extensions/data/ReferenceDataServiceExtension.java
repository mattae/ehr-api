package com.mattae.snl.plugins.ehr.api.extensions.data;

import com.mattae.snl.plugins.ehr.api.domain.ReferencedData;
import org.pf4j.ExtensionPoint;

import java.util.List;
import java.util.UUID;

public interface ReferenceDataServiceExtension extends ExtensionPoint {
    List<ReferencedData> getByKeyAndType(UUID referenceKey, String type, UUID organisationId) ;

    List<ReferencedData> getByReferenceKey(UUID referenceKey, UUID organisationId) ;
}
