package com.englishtown.vertigo.when.impl;

import com.englishtown.promises.When;
import com.englishtown.vertigo.when.WhenComponentInstance;
import com.englishtown.vertigo.when.WhenVertigoHandler;
import com.englishtown.vertigo.when.WhenOutputCollector;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import net.kuujo.vertigo.component.ComponentInstance;
import net.kuujo.vertigo.io.VertigoMessage;

public class DefaultWhenComponentInstance<T> implements WhenComponentInstance {

    private final ComponentInstance component;
    private final When when;
    private DefaultWhenOutputCollector output;

    public DefaultWhenComponentInstance(ComponentInstance component, When when) {
        this.component = component;
        this.when = when;
    }

    @Override
    public ComponentInstance getComponentInstance() {
        return component;
    }

    @Override
    public WhenOutputCollector output() {
        if (output == null) {
            output = new DefaultWhenOutputCollector(component.output(), when);
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

}
