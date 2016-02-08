package com.englishtown.vertigo.when;

import java.util.Collection;

/**
 * When wrapper for a Vertigo OutputCollector
 */
public interface WhenOutputCollector {

    /**
     * Returns a wrapper for a Vertigo port.
     * @param port The name of the output port.
     * @param <T> The type of message to send.
     * @return The port.
     */
    <T> WhenOutputPort<T> port(String port);

    /**
     * Returns a collection of all output ports
     * @return All ports specified in the network.
     */
    Collection<WhenOutputPort> ports();

}
