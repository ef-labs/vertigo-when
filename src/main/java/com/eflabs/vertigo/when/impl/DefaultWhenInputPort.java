package com.eflabs.vertigo.when.impl;

import com.eflabs.vertigo.when.WhenInputPort;
import com.englishtown.promises.Promise;
import com.englishtown.promises.Thenable;
import com.englishtown.promises.When;
import io.vertx.core.Handler;
import net.kuujo.vertigo.instance.InputPort;
import net.kuujo.vertigo.message.VertigoMessage;

import java.util.function.Function;


public class DefaultWhenInputPort<T> implements WhenInputPort<T> {

    private final InputPort<T> inputPort;
    private final When when;

    public DefaultWhenInputPort(InputPort<T> inputPort, When when) {
        this.inputPort = inputPort;
        this.when = when;
    }

    @Override
    public WhenInputPort<T> handler(Function<Promise<VertigoMessage<T>>, Thenable<?>> handler) {
        inputPort.handler(safeHandler(handler));
        return this;
    }

    @Override
    public String name() {
        return inputPort.name();
    }

    private Handler<VertigoMessage<T>> safeHandler(Function<Promise<VertigoMessage<T>>, Thenable<?>> handler) {
        return (message) -> {
            try {
                Promise<VertigoMessage<T>> promise = when.resolve(message);
                Thenable<?> result = handler.apply(promise);
                result.then(aVoid -> {
                    message.ack();
                    return null;
                }, throwable -> {
                    message.fail(throwable);
                    return null;
                });
            }
            catch (Throwable cause) {
                message.fail(cause);
            }
        };
    }


}
