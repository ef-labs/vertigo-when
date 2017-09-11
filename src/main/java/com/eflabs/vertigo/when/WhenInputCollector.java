package com.eflabs.vertigo.when;

import net.kuujo.vertigo.instance.OutputCollector;

import java.util.Collection;

/**
 * When wrapper for a Vertigo {@link OutputCollector}
 */
public interface WhenInputCollector {

    /**
     * Returns a wrapper for a Vertigo port.
     * @param port The name of the output port.
     * @param <T> The type of message to send.
     * @return The port.
     */
    <T> WhenInputPort<T> port(String port);

    /**
     * Returns a collection of all output ports
     * @return All ports specified in the network.
     */
    Collection<WhenInputPort> ports();

}
