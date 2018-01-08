package services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.ConfigFactory;
import play.libs.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.ws.*;
import play.libs.ws.WSResponse;
import play.libs.ws.WSRequest;

import java.util.concurrent.CompletionStage;
import redis.clients.jedis.Jedis;

public class VideoQuery {

    private static final Logger logger= LoggerFactory.getLogger(VideoQuery.class);

    private WSClient wsClient;

    public VideoQuery(WSClient wsClient){
        this.wsClient = wsClient;
    }

    private final String url = "http://47.97.6.37:9002/videoUrlList";

    public ObjectNode queryVideo() {
        JsonNode videosNode = Json.newObject();
        WSRequest request = wsClient.url(url);
        CompletionStage<JsonNode> responsePromise = request.get().thenApply(WSResponse::asJson);
        try {
            videosNode = responsePromise.toCompletableFuture().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ObjectNode)videosNode;
    }

    public static void setLabelInfo(String videoUrlMD5, String lable){
        JedisUtils.sadd(JedisUtils.ADX_VIDEO_LABEL_PRE+videoUrlMD5,lable);
        logger.info("VideoUrlMD5: {} ======== VideoLabelNode: {}.",JedisUtils.ADX_VIDEO_LABEL_PRE+videoUrlMD5,lable);
    }

}
