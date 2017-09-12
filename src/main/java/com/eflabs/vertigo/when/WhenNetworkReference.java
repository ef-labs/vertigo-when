package com.eflabs.vertigo.when;

import net.kuujo.vertigo.reference.NetworkReference;

/**
 * When wrapper for a Vertigo {@link NetworkReference}
 */
public interface WhenNetworkReference {

    /**
     * Returns the network input collector
     *
     * @return
     */
    WhenNetworkInputCollector input();

    /**
     * Returns the name of the network
     *
     * @return The name.
     */
    String name();

}
