
$(document).ready(function(){

    $.get("getVideoList",function(ret){

        var videoList = ret.data;
        videoIndex = 0;
        $.each(videoList,function(videoUrlMD5,videoElement){

            videoBoard = document.getElementById("videoboard");
            videoDiv = document.createElement("div");
            videoDiv.setAttribute("class", "videodiv");
            videoDiv.setAttribute("id",videoUrlMD5);
            videoUrl = videoElement.videoUrl;
            videoDiv.setAttribute("videoUrl",videoUrl);

            span = document.createElement("span");
            span.innerHTML = "Index: " + videoIndex++;
            videoDiv.appendChild(span);

            span = document.createElement("span");
            span.innerHTML = videoElement.platform;
            videoDiv.appendChild(span);

            videolink = document.createElement("a");
            markUrl = "getVideo?videoUrlMD5=" + videoUrlMD5 + "&videoUrl=" + videoUrl
            videolink.href = markUrl;
            videolink.innerHTML = "前去打标签";
            videoDiv.appendChild(videolink);

            videoBoard.appendChild(videoDiv);

        });
    });

});








