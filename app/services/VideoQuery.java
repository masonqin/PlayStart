package services;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.ws.*;
import play.libs.ws.WSResponse;
import play.libs.ws.WSRequest;
import model.Video;

import java.util.concurrent.CompletionStage;

public class VideoQuery {

    private WSClient wsClient;

    public VideoQuery(WSClient wsClient){
        this.wsClient = wsClient;
    }

    private final String url = "http://47.97.6.37:9002/videoUrlList";

    public JsonNode queryVideo() {
        JsonNode videosNode = Json.newObject();
        WSRequest request = wsClient.url(url);
        CompletionStage<JsonNode> responsePromise = request.get().thenApply(WSResponse::asJson);
        try {
            videosNode = responsePromise.toCompletableFuture().get();
            System.out.println(videosNode.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videosNode;
    }

}
