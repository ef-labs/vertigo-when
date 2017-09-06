package com.eflabs.vertigo.when;

import com.englishtown.promises.Thenable;
import net.kuujo.vertigo.message.VertigoMessage;

import java.util.function.Function;

public interface WhenInputPort<T> {
    WhenInputPort<T> handler(Function<VertigoMessage<T>, Thenable<?>> handler);
    String name();
}
