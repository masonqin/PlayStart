
var videoList = {};
var totalNum;
var pageCount;
var PAGESIZE = 10;
var labelAuthor;

$(document).ready(function(){



});

function getVideoList(){

    labelAuthor = document.getElementById("labelAuthor").value;
    $.get("getVideoList",function(ret){
        videoList = ret.data;
        totalNum = ret.size;
        queue_size = ret.queue_size;
        console.log("Backend video queue size : " + queue_size);
        pageCount = Math.ceil(totalNum/PAGESIZE);
        pageIndexDiv = document.getElementById("pageIndexDiv");
//        for(i=1;i<=pageCount;i++){
//            pageLink = document.createElement("button");
//            pageLink.setAttribute("onclick", "pageDisplay(" + i + "," + PAGESIZE + ")");
//            pageLink.innerHTML = i;
//            pageIndexDiv.appendChild(pageLink);
//        }
//        pageDisplay(1,PAGESIZE);
          pageDisplay(1,1000);
    });

}

function pageDisplay(pageNumber,pageSize){

    videoIndex=1;

    var startIndex = (pageNumber-1)*pageSize + 1;

    videoBoard = document.getElementById("videoboard");
    videoBoard.innerHTML = "";

    $.each(videoList,function(videoUrlMD5,videoElement){

        if(videoIndex>=startIndex && videoIndex<startIndex+pageSize){

            videoBoard = document.getElementById("videoboard");
            videoDiv = document.createElement("div");
            videoDiv.setAttribute("class", "videodiv");
            videoDiv.setAttribute("id",videoUrlMD5);
            videoUrl = videoElement.videoUrl;
            videoDiv.setAttribute("videoUrl",videoUrl);

            span = document.createElement("span");
            span.innerHTML = "Index: " + videoIndex;
            videoDiv.appendChild(span);

            span = document.createElement("span");
            span.innerHTML = videoElement.platform;
            videoDiv.appendChild(span);

            videolink = document.createElement("a");
            labelAuthor = document.getElementById("labelAuthor").value;
            markUrl = "getVideo?videoUrlMD5=" + videoUrlMD5 + "&videoUrl=" + videoUrl + "&labelAuthor=" + labelAuthor;
            videolink.href = markUrl;
            videolink.setAttribute("target","_blank");
            videolink.innerHTML = "前去打标签";
            videoDiv.appendChild(videolink);

            videoBoard.appendChild(videoDiv);
        }
        videoIndex++;
    });
}







