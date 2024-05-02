package com.mattae.snl.plugins.ehr.api.extensions.encounter;

import com.mattae.snl.plugins.ehr.api.domain.Encounter;
import io.github.jbella.snl.core.api.domain.Organisation;
import org.pf4j.ExtensionPoint;

import java.util.List;

/**
 * Provide an Observation summary of what happened in an Encounter
 */
public interface EncounterSummaryExtension extends ExtensionPoint {
    Summary getByEncounter(Encounter.IdView encounter, Organisation.IdView organisation);

    /**
     * @param title Title of the summary
     * @param items List of summaries of what took place
     */
    record Summary(String title, List<String> items) {
    }
}
