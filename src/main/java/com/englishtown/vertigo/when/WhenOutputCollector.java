package com.englishtown.vertigo.when;

import java.util.Collection;

/**
 * When wrapper for a Vertigo OutputCollector
 */
public interface WhenOutputCollector {

    /**
     * Returns a wrapper for a Vertigo port.
     * @param port
     * @param <T>
     * @return
     */
    <T> WhenOutputPort<T> port(String port);

    /**
     * Returns a collection of all output ports
     * @return
     */
    Collection<WhenOutputPort> ports();

}
