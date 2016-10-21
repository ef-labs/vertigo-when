package com.eflabs.vertigo.when;

import java.util.List;

/**
 * Created by Magnus.Koch on 9/5/2016.
 */
public interface WhenNetworkInputCollector {
    <T> WhenOutputPort<T> port(String port);
    List<WhenOutputPort> ports();
}
