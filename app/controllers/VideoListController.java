package controllers;

import static play.mvc.Controller.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.ConfigFactory;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.libs.ws.WSClient;

import play.mvc.Result;
import services.AtomicCounter;
import services.LabelJsonGen;
import services.VideoQuery;
import views.html.video;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import akka.event.slf4j.Logger;


public class VideoListController {

    @Inject
    WSClient wsClient;

    @Inject
    AtomicCounter counter;

    private static final org.slf4j.Logger logger= LoggerFactory.getLogger(VideoListController.class);

    public Result getVideoList() {


        long validTime = Long.parseLong(ConfigFactory.load().getString("validTime"));
        synchronized (counter) {
            if (counter.queueSize() < 20) {
                logger.info("Queue size: {}, it's time to get video list.",counter.queueSize());
                VideoQuery videoQuery = new VideoQuery(wsClient);
                ObjectNode allVideoNode = videoQuery.queryVideo();
                try {
                    Iterator<Map.Entry<String, JsonNode>> it = allVideoNode.get("data").fields();
                    while (it.hasNext()) {
                        Map.Entry<String, JsonNode> entry = it.next();
                        JsonNode videoInfo = entry.getValue();
                        long saveTime = Long.parseLong(videoInfo.get("saveTime").asText()) / 1000;
                        if (saveTime > validTime) {
                            counter.queuePut(entry);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Map.Entry<String, JsonNode> entry;
        ObjectNode videoNode = Json.newObject();
        for(int i=0;i<10;i++) {
            entry = counter.queueGet();
            String videoUrlMD5 = entry.getKey();
            JsonNode videoInfo = entry.getValue();
            videoNode.set(videoUrlMD5, videoInfo);
        }

        ObjectNode validVideoNode = Json.newObject();
        validVideoNode.put("size", videoNode.size());
        validVideoNode.put("queue_size", counter.queueSize());
        validVideoNode.set("data", videoNode);

        return ok(validVideoNode);
    }

    public Result getVideo(String videoUrlMD5, String videoUrl) {

        //String videoUrl = videoUrlMap.get(videoUrlMD5).get("videoUrl").asText();
        return ok(video.render(videoUrl));
    }

    public Result setVideoLabel() {
        JsonNode videoLabelNode = request().body().asJson();
        if (videoLabelNode != null) {
            //System.out.println(videoLabelNode.toString());
            VideoQuery.setLabelInfo(videoLabelNode.get("videoUrlMD5").asText(), videoLabelNode.toString());
            return ok();
        }
        return notFound();
    }

    public Result getVideoLabelJson(){
        return ok((new LabelJsonGen().genLabelJson()));
    }

    public Result getVideoLabel(String videoUrlMD5) {
        return ok(VideoQuery.getLabelInfo(videoUrlMD5).toString());
    }

    public Result delVideoLabel(String videoUrlMD5) {
        VideoQuery.delLabelInfo(videoUrlMD5);
        return ok();
    }

}
