package com.eflabs.vertigo.when.impl;

import com.eflabs.vertigo.when.WhenInputPort;
import com.englishtown.promises.Thenable;
import io.vertx.core.Handler;
import net.kuujo.vertigo.instance.InputPort;
import net.kuujo.vertigo.message.VertigoMessage;

import java.util.function.Function;


public class DefaultWhenInputPort<T> implements WhenInputPort<T> {

    private final InputPort<T> inputPort;

    public DefaultWhenInputPort(InputPort<T> inputPort) {
        this.inputPort = inputPort;
    }

    @Override
    public WhenInputPort<T> handler(Function<VertigoMessage<T>, Thenable<?>> handler) {
        inputPort.handler(safeHandler(handler));
        return this;
    }

    @Override
    public String name() {
        return inputPort.name();
    }

    private Handler<VertigoMessage<T>> safeHandler(Function<VertigoMessage<T>, Thenable<?>> handler) {
        return (message) -> {
            try {
                Thenable<?> result = handler.apply(message);
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
