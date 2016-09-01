package com.eflabs.vertigo.when;

import com.englishtown.promises.Promise;
import com.englishtown.promises.Thenable;
import net.kuujo.vertigo.instance.InputCollector;
import net.kuujo.vertigo.instance.OutputCollector;
import net.kuujo.vertigo.message.VertigoMessage;

/**
 * Helper class for when based vertigo development.
 */
public abstract class PromiseComponentBase<T> extends WhenComponentBase {

    public PromiseComponentBase(WhenVertigoFactory whenVertigoFactory) {
        super(whenVertigoFactory);
    }

    @Override
    protected void initComponent() {

        // Register all input ports automatically
        input()
                .ports()
                .forEach(port -> {
                    input()
                            .<T>port(port.name())
                            .handler(this::handle);
                });

    }

    protected abstract Thenable<?> handle(Promise<VertigoMessage<T>> messagePromise);

    /**
     * Returns the component's {@link InputCollector}. This is the element of the
     * component which provides an interface for receiving messages from other components.
     *
     * @return The components {@link InputCollector}.
     */

    public WhenInputCollector input() {
        return whenComponent().input();
    }

    /**
     * Returns the component's {@link OutputCollector}. This is the element of the
     * component which provides an interface for sending messages to other components.
     *
     * @return The component's {@link OutputCollector}.
     */
    public WhenOutputCollector output() {
        return whenComponent().output();
    }

}
