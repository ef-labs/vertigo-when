package com.eflabs.vertigo.when.testtools;

import com.eflabs.vertigo.when.PromiseComponentBase;
import com.eflabs.vertigo.when.WhenVertigoFactory;
import com.eflabs.vertigo.when.impl.DefaultWhenVertigoFactory;
import com.englishtown.promises.Promise;
import com.englishtown.promises.Thenable;
import com.englishtown.promises.When;
import com.englishtown.promises.WhenFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.test.core.VertxTestBase;
import net.kuujo.vertigo.Vertigo;
import net.kuujo.vertigo.VertigoException;
import net.kuujo.vertigo.message.VertigoMessage;
import net.kuujo.vertigo.network.NetworkConfig;
import net.kuujo.vertigo.reference.NetworkReference;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class WhenVertigoTestBase extends VertxTestBase {

    private static Logger logger = LoggerFactory.getLogger(WhenVertigoTestBase.class.getName());
    protected static When when = WhenFactory.createSync();
    protected static WhenVertigoFactory whenVertigoFactory = new DefaultWhenVertigoFactory(when);

    protected static Void VOID = null;
    private NetworkReference networkReference;
    protected abstract NetworkConfig createNetwork();

    public void setUp() throws Exception {
        super.setUp();
        NetworkConfig network = this.createNetwork();
        CompletableFuture future = new CompletableFuture();
        this.vertx.runOnContext((aVoid) -> {
            Vertigo vertigo = Vertigo.vertigo(this.vertx);
            vertigo.deployNetwork(network, (result) -> {
                if(result.failed()) {
                    this.fail(result.cause().getMessage());
                } else {
                    this.networkReference = result.result();
                }

                future.complete(null);
            });
        });
        future.get();
    }

    protected static Logger logger() {
        return logger;
    }

    public NetworkReference getNetworkReference() {
        return this.networkReference;
    }

    public static class EventBusForwardingComponent extends PromiseComponentBase<Object> {
        private String forwardingAddress;

        public EventBusForwardingComponent() {
            super(whenVertigoFactory);
        }

        @Override
        protected void initComponent() {
            super.initComponent();
            this.forwardingAddress = this.context().config().getString("target");
            if(this.forwardingAddress == null) {
                throw new VertigoException(String.format("%s %s is missing a configuration value for \'target\'", new Object[]{this.getClass().getName(), this.name()}));
            }
        }

        @Override
        protected Thenable<?> handle(VertigoMessage<Object> vertigoMessage) {
            logger.info(this.context().name() + " received message " + vertigoMessage.body() + ", forwarding and acking.");
            this.vertx.eventBus().send(this.forwardingAddress, vertigoMessage.body());
            return null;
        }

        public static JsonObject config(String address) {
            return (new JsonObject()).put("target", address);
        }
    }

    public static class AutoForwardingComponent extends PromiseComponentBase<String> {

        public AutoForwardingComponent() {
            super(whenVertigoFactory);
        }

        @Override
        protected Thenable<?> handle(VertigoMessage<String> vertigoMessage) {
            logger.info(this.context().name() + " received message " + vertigoMessage.body());
            String trace = vertigoMessage.body() + " > " + this.context().name();
            if(this.output().<String>ports().size() > 0) {
                List<Promise<String>> sendPromises = output()
                        .ports()
                        .stream()
                        .map(port -> output()
                                .<String>port(port.name())
                                .send(trace))
                        .collect(Collectors.toList());
                return when().all(sendPromises);
            }
            else {
                return when().resolve(null);

            }
        }
    }

}
