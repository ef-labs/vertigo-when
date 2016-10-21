package com.eflabs.vertigo.when.impl;

import com.eflabs.vertigo.when.WhenOutputCollector;
import com.eflabs.vertigo.when.WhenOutputPort;
import com.englishtown.promises.When;
import net.kuujo.vertigo.instance.OutputCollector;

import java.util.Collection;
import java.util.stream.Collectors;

public class DefaultWhenOutputCollector implements WhenOutputCollector {
    private final OutputCollector output;
    private final When when;

    public DefaultWhenOutputCollector(OutputCollector output, When when) {
        this.output = output;
        this.when = when;
    }

    @Override
    public <T> WhenOutputPort<T> port(String port) {
        return new DefaultWhenOutputPort<>(output.port(port), when);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<WhenOutputPort> ports() {
        return output.ports()
                .stream()
                .map(p -> new DefaultWhenOutputPort(p, when))
                .collect(Collectors.toSet());
    }

}
