package controllers;

import static play.mvc.Controller.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.libs.ws.WSClient;

import play.mvc.Result;
import services.VideoQuery;
import views.html.video;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class VideoListController {

    @Inject
    WSClient wsClient;

    Map<String,JsonNode> videoUrlMap = new HashMap<>();

    public Result getVideoList() {

        long validTime = 1514131200; //2017-12-25 00:00:00
        VideoQuery videoQuery = new VideoQuery(wsClient);
        ObjectNode allVideoNode = videoQuery.queryVideo();
        ObjectNode validVideoNode = Json.newObject();
        ObjectNode videoNode = Json.newObject();
        try {
            Iterator<Map.Entry<String, JsonNode>> it = allVideoNode.get("data").fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                String videoUrlMD5 = entry.getKey();
                JsonNode videoInfo = entry.getValue();
                long saveTime = Long.parseLong(videoInfo.get("saveTime").asText())/1000;
                if(saveTime > validTime) {
                    videoNode.set(videoUrlMD5,videoInfo);
                    //videoUrlMap.put(videoUrlMD5, videoInfo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        validVideoNode.put("size",videoNode.size());
        validVideoNode.set("data",videoNode);

        return ok(validVideoNode);
    }

    public Result getVideo(String videoUrlMD5, String videoUrl) {

        //String videoUrl = videoUrlMap.get(videoUrlMD5).get("videoUrl").asText();
        return ok(video.render(videoUrl));
    }

    public Result setVideoLabel(){
        JsonNode videoLabelNode = request().body().asJson();
        if(videoLabelNode != null){
            System.out.println(videoLabelNode.toString());
            VideoQuery.setLabelInfo(videoLabelNode.get("videoUrlMD5").asText(), videoLabelNode.toString());
            return ok();
        }
        return notFound();
    }

}
