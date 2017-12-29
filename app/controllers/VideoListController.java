package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.mvc.*;
import services.VideoQuery;
import views.html.video;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static play.mvc.Results.ok;


public class VideoListController {

    @Inject
    WSClient wsClient;

    Map<String,JsonNode> videoUrlMap = new HashMap<>();

    public Result getVideoList() {

        VideoQuery videoQuery = new VideoQuery(wsClient);
        JsonNode returnNode = videoQuery.queryVideo();
        try {
            Iterator<Map.Entry<String, JsonNode>> it = returnNode.get("data").fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                String videoUrlMD5 = entry.getKey();
                JsonNode videoInfo = entry.getValue();
                videoUrlMap.put(videoUrlMD5, videoInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok(returnNode);
    }

    public Result getVideo(String videoUrlMD5) {

        String videoUrl = videoUrlMap.get(videoUrlMD5).get("videoUrl").asText();
        System.out.println("Video MD5: " + videoUrlMD5);
        System.out.println("Video Url: " + videoUrl);
        return ok(video.render(videoUrl));

    }

}
