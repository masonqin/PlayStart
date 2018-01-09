package services;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnFailure;
import akka.event.slf4j.Logger;
import akka.pattern.AskTimeoutException;
import akka.pattern.Backoff;
import akka.pattern.BackoffSupervisor;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import akka.util.Timeout;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class AkkaSystem {

    public  ActorRef actorRef = null;
    private ActorSystem actorSystem = null;

    public AkkaSystem(){

        //int size = Integer.parseInt(ConfigFactory.load().getString("systemactor.size"));
        actorSystem = ActorSystem.create("RTB-actor-system");
        Props masterProps = Props.create(QueryActor.class);

        //first test one actor's
        //actorRef = actorSystem.actorOf(masterProps,"QueryActor");
        //actor with router
        actorRef = actorSystem.actorOf(masterProps.withRouter(new RoundRobinPool(5)),"QueryActor");

    }

    public Future<Object> ask(Object message){

        Timeout timeout = new Timeout(Duration.create(ConfigFactory.load().getLong("http.common.timeout"), TimeUnit.MILLISECONDS));
        Future<Object> future = Patterns.ask(actorRef, message, timeout);
        //System.out.println("Ask function actor thread: " + Thread.currentThread().getName());

        future.onFailure(new OnFailure() {
            @Override
            public void onFailure(Throwable failure) throws Throwable {
            if (failure instanceof AskTimeoutException) {
                Logger.root().error("askTimeout :" + message + actorRef + "----" + failure);
            }
            }
        }, actorSystem.dispatcher());
        return future;
    }

}
