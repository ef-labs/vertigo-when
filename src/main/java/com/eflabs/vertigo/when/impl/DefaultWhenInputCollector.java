package com.eflabs.vertigo.when.impl;

import com.eflabs.vertigo.when.WhenInputCollector;
import com.eflabs.vertigo.when.WhenInputPort;
import net.kuujo.vertigo.instance.InputCollector;

import java.util.Collection;
import java.util.stream.Collectors;

public class DefaultWhenInputCollector implements WhenInputCollector {

    private final InputCollector input;

    public DefaultWhenInputCollector(InputCollector input) {
        this.input = input;
    }

    @Override
    public <T> WhenInputPort<T> port(String port) {
        return new DefaultWhenInputPort<T>(input.port(port));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<WhenInputPort> ports() {
        return input.ports()
                .stream()
                .map(p -> new DefaultWhenInputPort(p))
                .collect(Collectors.toSet());
    }
}
