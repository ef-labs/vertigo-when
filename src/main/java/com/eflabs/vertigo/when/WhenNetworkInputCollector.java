package com.eflabs.vertigo.when;

import java.util.List;

/**
 *
 */
public interface WhenNetworkInputCollector {
    <T> WhenOutputPort<T> port(String port);

    List<WhenOutputPort> ports();
}
