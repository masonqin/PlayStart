
$(document).ready(function(){

    $.get("getVideoList",function(ret){

        var videoList = ret.data;
        videoIndex = 0;
        $.each(videoList,function(md5,videoElement){

            videoBoard = document.getElementById("videoboard");
            videoDiv = document.createElement("div");
            videoDiv.setAttribute("class", "videodiv");
            videoDiv.setAttribute("id",md5);

            span = document.createElement("span");
            span.innerHTML = "Index: " + videoIndex++;
            videoDiv.appendChild(span);

            span = document.createElement("span");
            span.innerHTML = videoElement.platform;
            videoDiv.appendChild(span);

            videolink = document.createElement("a");
            markUrl = "getVideo?videoUrlMD5=" + md5
            videolink.href = markUrl;
            videolink.innerHTML = "前去打标签";
            videoDiv.appendChild(videolink);

            videoBoard.appendChild(videoDiv);

        });
    });

});








