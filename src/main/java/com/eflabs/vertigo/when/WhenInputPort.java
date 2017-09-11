package com.eflabs.vertigo.when;

import com.englishtown.promises.Thenable;
import net.kuujo.vertigo.instance.InputPort;
import net.kuujo.vertigo.message.VertigoMessage;

import java.util.function.Function;

/**
 * When wrapper for a Vertigo {@link InputPort}
 *
 * @param <T>
 */
public interface WhenInputPort<T> {
    WhenInputPort<T> handler(Function<VertigoMessage<T>, Thenable<?>> handler);

    /**
     * Returns the port name.
     *
     * @return The port name.
     */
    String name();
}
