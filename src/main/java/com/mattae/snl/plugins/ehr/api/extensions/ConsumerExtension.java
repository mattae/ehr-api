package com.mattae.snl.plugins.ehr.api.extensions;


import org.pf4j.ExtensionPoint;

public interface ConsumerExtension extends ExtensionPoint {
    boolean consumes(String type);
}
