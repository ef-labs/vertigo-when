package com.eflabs.vertigo.when.tests;

import com.eflabs.vertigo.when.WhenComponentBase;
import com.eflabs.vertigo.when.testtools.WhenVertigoTestBase;
import io.vertx.core.Future;
import net.kuujo.vertigo.VertigoException;
import net.kuujo.vertigo.network.NetworkConfig;
import net.kuujo.vertigo.network.builder.NetworkBuilder;
import net.kuujo.vertigo.reference.NetworkReference;
import org.junit.Test;

public class Execute_Error_Test extends WhenVertigoTestBase {

    @Override
    protected NetworkConfig createNetwork() {
        NetworkBuilder builder = NetworkConfig.builder();

        builder.connect()
                .network().port("in")
                .to("A").identifier(AutoForwardingComponent.class.getName()).port("in");

        builder.connect("A").port("out")
                .to("B").identifier(ErrorComponent.class.getName()).port("in");

        return builder.build();
    }

    @Test
    public void test() {

        NetworkReference network = getNetworkReference();

        network.input().port("in")
                .send("Word", event -> {
                    assertTrue(event.failed());
                    assertTrue(event.cause().getMessage().contains("Computer says no."));
                    testComplete();
                });

        await();
    }

    public static class ErrorComponent extends WhenComponentBase {

        public ErrorComponent() {
            super(whenVertigoFactory);
        }

        /**
         * Override this method to register input port handlers and other initialization work.
         */
        @Override
        protected void initComponent() throws Exception {
            input()
                    .ports()
                    .forEach(port -> input()
                            .port(port.name())
                            .handler(message -> {
                                throw new VertigoException("Computer says no.");
                            }));
        }

    }

}