package com.englishtown.vertigo.when.impl;

import com.englishtown.promises.When;
import com.englishtown.vertigo.when.WhenOutputCollector;
import com.englishtown.vertigo.when.WhenOutputPort;
import net.kuujo.vertigo.io.OutputCollector;

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
        return new DefaultWhenOutputPort<T>(output.port(port), output, when);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<WhenOutputPort> ports() {
        return output.ports()
                .stream()
                .map(p -> new DefaultWhenOutputPort(p, output, when))
                .collect(Collectors.toSet());
    }

}
