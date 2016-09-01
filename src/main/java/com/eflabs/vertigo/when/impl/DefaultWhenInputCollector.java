package com.eflabs.vertigo.when.impl;

import com.eflabs.vertigo.when.WhenInputCollector;
import com.eflabs.vertigo.when.WhenInputPort;
import com.englishtown.promises.When;
import net.kuujo.vertigo.instance.InputCollector;

import java.util.Collection;
import java.util.stream.Collectors;

public class DefaultWhenInputCollector implements WhenInputCollector {

    private final InputCollector input;
    private final When when;

    public DefaultWhenInputCollector(InputCollector input, When when) {
        this.input = input;
        this.when = when;
    }

    @Override
    public <T> WhenInputPort<T> port(String port) {
        return new DefaultWhenInputPort<T>(input.port(port), when);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<WhenInputPort> ports() {
        return input.ports()
                .stream()
                .map(p -> new DefaultWhenInputPort(p, when))
                .collect(Collectors.toSet());
    }
}
