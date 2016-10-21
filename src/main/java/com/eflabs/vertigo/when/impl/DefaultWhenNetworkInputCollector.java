package com.eflabs.vertigo.when.impl;

import com.eflabs.vertigo.when.WhenNetworkInputCollector;
import com.eflabs.vertigo.when.WhenOutputPort;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.PromiseAdapter;
import net.kuujo.vertigo.reference.InputPortReference;
import net.kuujo.vertigo.reference.InputReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultWhenNetworkInputCollector implements WhenNetworkInputCollector {

    private final InputReference input;
    private final When when;
    private final PromiseAdapter promiseAdapter;
    private final Map<String, DefaultWhenNetworkInputPort> ports;

    @SuppressWarnings("unchecked")
    public DefaultWhenNetworkInputCollector(InputReference input, When when, PromiseAdapter promiseAdapter) {
        this.input = input;
        this.when = when;
        this.promiseAdapter = promiseAdapter;
        this.ports = input
                .ports()
                .stream()
                .collect(Collectors.toConcurrentMap(
                        InputPortReference::name,
                        p -> new DefaultWhenNetworkInputPort<>(input.port(p.name()), when, promiseAdapter)
                ));

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> WhenOutputPort<T> port(String name) {
        DefaultWhenNetworkInputPort port = ports.get(name);
        if (port == null) {
            port = new DefaultWhenNetworkInputPort<>(input.port(name), when, promiseAdapter);
            ports.put(name, port);
        }
        return port;
    }

    @Override
    public List<WhenOutputPort> ports() {
        return new ArrayList<>(ports.values());
    }


}
