package controllers;

import static play.mvc.Controller.*;
import com.fasterxml.jackson.databind.JsonNode;
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
