package com.eflabs.vertigo.when.tests;

import com.eflabs.vertigo.when.testtools.WhenVertigoTestBase;
import net.kuujo.vertigo.network.NetworkConfig;
import net.kuujo.vertigo.network.builder.NetworkBuilder;
import net.kuujo.vertigo.reference.NetworkReference;
import org.junit.Test;

public class Execute_Ack_Test extends WhenVertigoTestBase {

    @Override
    protected NetworkConfig createNetwork() {
        NetworkBuilder builder = NetworkConfig.builder();

        builder.connect()
                .network().port("in")
                .to("A").identifier(AutoForwardingComponent.class.getName()).port("in");

        builder.connect()
                .component("A").port("out")
                .to("B").identifier(AutoForwardingComponent.class.getName()).port("in");

        return builder.build();
    }

    @Test
    public void test() {

        NetworkReference network = getNetworkReference();
        network.input().port("in")
                .send("Word", event -> {
                    assertTrue(event.succeeded());
                    testComplete();
                });

        await();
    }

}