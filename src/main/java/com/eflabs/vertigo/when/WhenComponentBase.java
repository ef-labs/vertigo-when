package com.eflabs.vertigo.when;

import com.englishtown.promises.When;
import io.vertx.core.Future;
import net.kuujo.vertigo.component.AbstractComponent;
import net.kuujo.vertigo.instance.InputCollector;
import net.kuujo.vertigo.instance.OutputCollector;

/**
 * Helper class for when based vertigo development.
 */
public abstract class WhenComponentBase extends AbstractComponent {

    private final When when;
    private final WhenVertigoFactory whenVertigoFactory;
    private WhenComponentInstance whenComponent;

    public WhenComponentBase(WhenVertigoFactory whenVertigoFactory) {
        this.whenVertigoFactory = whenVertigoFactory;
        this.when = this.whenVertigoFactory.getWhen();
    }

    @Override
    protected void initComponent(Future<Void> initFuture) throws Exception {
        this.whenComponent = whenVertigoFactory.getComponentInstance(this.component());
        super.initComponent(initFuture);
    }

    public When when() {
        return when;
    }

    public WhenComponentInstance whenComponent() {
        return whenComponent;
    }

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
