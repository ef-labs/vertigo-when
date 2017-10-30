package com.eflabs.vertigo.when.impl;

import com.eflabs.vertigo.when.WhenComponentInstance;
import com.eflabs.vertigo.when.WhenInputCollector;
import com.eflabs.vertigo.when.WhenOutputCollector;
import com.englishtown.promises.When;
import net.kuujo.vertigo.context.ComponentContext;
import net.kuujo.vertigo.instance.ComponentInstance;

public class DefaultWhenComponentInstance implements WhenComponentInstance {

    private final ComponentInstance componentInstance;
    private final When when;
    private WhenOutputCollector output;
    private WhenInputCollector input;

    public DefaultWhenComponentInstance(When when, ComponentInstance componentInstance) {
        this.componentInstance = componentInstance;
        this.when = when;
    }

    @Override
    public WhenInputCollector input() {
        if (input == null) {
            input = new DefaultWhenInputCollector(componentInstance.input());
        }
        return input;
    }

    @Override
    public WhenOutputCollector output() {
        if (output == null) {
            output = new DefaultWhenOutputCollector(componentInstance.output(), when);
        }
        return output;
    }

    @Override
    public ComponentContext context() {
        return componentInstance.context();
    }

    @Override
    public ComponentInstance componentInstance() {
        return componentInstance;
    }

}
