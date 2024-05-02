package com.mattae.snl.plugins.ehr.api.extensions;


import org.pf4j.ExtensionPoint;

/**
 * An extension for consumers of observation data
 */
public interface ConsumerExtension extends ExtensionPoint {
    /**
     * The type of Observation this consumer handles
     * @param type the Observation type
     * @return true if it handles this consumer type
     */
    boolean consumes(String type);
}
