package com.eflabs.vertigo.when.impl;

import com.eflabs.vertigo.when.WhenInputCollector;
import com.eflabs.vertigo.when.WhenOutputCollector;
import com.englishtown.promises.When;
import com.eflabs.vertigo.when.WhenComponentInstance;
import com.eflabs.vertigo.when.WhenVertigoHandler;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import net.kuujo.vertigo.context.ComponentContext;
import net.kuujo.vertigo.instance.ComponentInstance;
import net.kuujo.vertigo.message.VertigoMessage;

public class DefaultWhenComponentInstance implements WhenComponentInstance {

    private final ComponentInstance componentInstance;
    private final When when;
    private WhenOutputCollector output;
    private WhenInputCollector input;

    public DefaultWhenComponentInstance(When when, ComponentInstance componentInstance) {
        this.componentInstance = componentInstance;
        this.when = when;
    }

//    @Override
//    public ComponentInstance getComponentInstance() {
//        return componentInstance;
//    }

    @Override
    public WhenInputCollector input() {
        if (input == null) {
            input = new DefaultWhenInputCollector(componentInstance.input(), when);
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
    public <T> WhenVertigoHandler<T> handler(Handler<AsyncResult<Void>> completeHandler) {
        return new DefaultWhenVertigoHandler<>(when, completeHandler);
    }

    @Override
    public <T> WhenVertigoHandler<T> handler(VertigoMessage<?> message) {
        return new DefaultWhenVertigoHandler<>(when, message::handle);
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
