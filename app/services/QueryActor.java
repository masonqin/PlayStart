package services;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.ActorMaterializerSettings;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import play.libs.ws.*;
import play.libs.ws.ahc.*;
import java.util.Optional;

public class QueryActor extends UntypedActor{

    // Set up Akka
    String name = "wsclient";
    ActorSystem system = ActorSystem.create(name);
    ActorMaterializerSettings settings = ActorMaterializerSettings.create(system);
    ActorMaterializer materializer = ActorMaterializer.create(settings, system, name);

    // Set up AsyncHttpClient directly from config
    AsyncHttpClientConfig asyncHttpClientConfig = new DefaultAsyncHttpClientConfig.Builder()
            .setMaxRequestRetry(0)
            .setShutdownQuietPeriod(0)
            .setShutdownTimeout(0).build();
    AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient(asyncHttpClientConfig);

    // Set up WSClient instance directly from asynchttpclient.
    WSClient client = new AhcWSClient(
            asyncHttpClientConfig,
            materializer
    );

    @Override
    public void onReceive(Object message){

        //send query and append call back
        //to send the response to the sender

        //System.out.println("Query actor thread name:" + Thread.currentThread().getName());
        ActorRef sender = getSender();
        //System.out.println("get sender from outside: " + sender);
        client.url("http://127.0.0.1:9001").get().whenComplete((resp, exception)->{
            Optional.ofNullable(resp).ifPresent(response -> {
                //System.out.println("Query response thread name:" + Thread.currentThread().getName());
                //System.out.println("WS get response: " + response.getBody().toString());
                //System.out.println("print sender from inside: " + sender);
                sender.tell(resp,getSelf());
            });
        }).thenRun(() -> {
            try {
                //client.close();
            } catch (Exception e) {
                //e.printStackTrace();
            }});
        //System.out.println("Query actor thread run to the end");
    }
}
