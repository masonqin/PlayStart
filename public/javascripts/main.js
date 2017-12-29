
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


$(document).ready(function(){

    var game_method = new Array("MMORPG","SLG","卡牌","竞技","策略","放置","经营","消除","竞速",
                                "赌博","棋牌","生存","单机","沙盒","IO","MOBA","射击","冒险","体育",
                                "模拟","街机","益智","音乐","文字");

    markDiv = document.getElementsByClassName("mark-group")[0];
    h4 = document.createElement("h4");
    h4.innerHTML = "游戏";
    h5 = document.createElement("h5");
    h5.innerHTML = "玩法";
    h4.appendChild(h5);
    markDiv.appendChild(h4);

    table = document.createElement("table");
    tbody = document.createElement("tbody");
    tr = document.createElement("tr");

    var line_num = 5;

    for(i=0;i<game_method.length;i++){
        if(i%line_num == 0){
            tbody.appendChild(tr);
            tr = document.createElement("tr");
        }
        else{
            td = document.createElement("td");
            label = document.createElement("label");
            input = document.createElement("input");
            input.class = "markbox";
            input.type = "checkbox";
            input.name = "markbox"
            label.appendChild(input);
            label.innerHTML = game_method[i];
            td.appendChild(label);
            tr.appendChild(td);
        }
    }
    tbody.appendChild(tr);
    table.appendChild(tbody);
    markDiv.appendChild(table);
});







