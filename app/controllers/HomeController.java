package controllers;

import akka.actor.ActorRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;
import scala.compat.java8.FutureConverters;
import services.AkkaSystem;
import views.html.*;

import javax.inject.Inject;
import play.libs.ws.*;
import play.libs.ws.ahc.*;

import java.util.concurrent.CompletionStage;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    private final AkkaSystem actorSystem;
    private static final Logger logger= LoggerFactory.getLogger(HomeController.class);

    @Inject
    public HomeController() {

        actorSystem=new AkkaSystem();
    }

    public CompletionStage<Result> index() {

        return FutureConverters.toJava(
                actorSystem.ask(new Object())).
                thenApply(response->{logger.info("receive response:" + response);
                //System.out.println("Controller get response: " + ((WSResponse)response).getBody().toString());
                return ok(((WSResponse)response).getBody());
        });

        //return ok(index.render(""));

    }

}
