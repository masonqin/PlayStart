package services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import javax.jws.Oneway;
import java.util.*;

public class LabelJsonGen {

    int labelIndex = 0;

    String[] gameCatagory = {"动作游戏", "冒险游戏", "街机游戏", "桌面游戏", "卡牌游戏", "赌博游戏", "家庭游戏", "音乐游戏",
            "解谜游戏", "竞速游戏", "角色扮演游戏", "模拟游戏", "体育游戏", "策略游戏", "益智游戏", "消除游戏"};
    String[] gameCatagoryEN = {"ActionGames", "AdventureGames", "ArcadeGames", "TableGames", "CardGames",
            "GamblingGames", "FamilyGames", "MusicalGames", "PuzzleGames", "SpeedGame",
            "CosplayGame", "Simulationgame", "Sportsgame", "strategygame", "puzzlegame",
            "eliminationgame"};

    String[] gameMethod = {"剧情", "独立", "经营", "棋牌", "生存", "单机", "沙盒", "IO", "MOBA", "竞技", "战争", "放置", "射击"};
    String[] gameMethodEN = {"Plot", "Independence", "Business", "Chess", "Survival", "Standalone", "Sandbox", "IO",
            "MOBA", "Competitive", "War", "Placement", " shooting"};

    String[] gameTheme = {"卡通", "三国", "西游", "武侠", "仙侠", "传奇", "二次元", "现代", "古代", "魔幻", "科幻", "校园"};
    String[] gameThemeEN = {"Cartoon", "Three Kingdoms", "Journey to the West", "Swordsman", "Xian Xia", "Legend",
            "Second Yuan", "Modern", "Ancient", "Magic", "Sinence", "School"};

    String[] gameStyle = {"2D", "3D", "简约", "像素", "唯美", "水墨", "酷炫", "暴力", "惊悚", "欧美", "日系", "古典"};
    String[] gameStyleEN = {"2D", "3D", "Simple", "Pixel", "Aesthetic", "Ink", "Cool", "Violence", "Thriller", "Europe",
            "America", "Japanese", "Classic,"};

    String[] gameAudience = {"男性", "女性", "儿童", "学生", "上班族", "大龄", "小资", "大R"};
    String[] gameAudienceEN = {"Male", "Female", "Child", "Student", "Office Worker", "Older Age", "Petty Petty", "Big R"};

    String[][] gameClasses = {gameCatagory, gameMethod, gameTheme, gameStyle, gameAudience};
    String[][] gameClassesEN = {gameCatagoryEN, gameMethodEN, gameThemeEN, gameStyleEN, gameAudienceEN};
    String[] gameClassesName = {"类型", "方法", "题材", "风格", "受众"};
    String[] gameClassesNameEN = {"Type", "Method", "Theme", "Style", "Audience"};

    //==================================================================================

    String[] appTopLevelLabels = {"儿童", "教育", "购物", "摄影与录像", "效率", "美食佳饮",
            "生活", "健康健美", "旅游", "音乐", "体育", "商务", "新闻", "工具", "娱乐",
            "社交", "报刊杂志", "财务", "参考", "导航", "医疗", "图书", "天气"};
    String[] appTopLevelLabelsEN = {"Children", "Education", "Shopping", "Photography", "Efficiency",
            "Gourmet", "Life", "Health and Fitness", "Travel", "Music", "Sports", "Business", "News", "Tools",
            "Entertainment", "Social", "Newspaper", "Finance", "Reference", "Navigation", "Medical", "Book", "Weather"};
    //SubLevel
    String[] children = {"故事", "英语", "绘画", "音乐", "模拟"};
    String[] childrenEN = {"故事", "英语", "绘画", "音乐", "模拟"};
    String[] education = {"在线学堂", "考试", "出国留学", "语言学习", "专业学习"};
    String[] educationEN = {"在线学堂", "考试", "出国留学", "语言学习", "专业学习"};
    String[] shopping = {"网上商城", "海淘", "导购推荐", "团购特卖", "支付"};
    String[] shoppingEN = {"网上商城", "海淘", "导购推荐", "团购特卖", "支付"};
    String[] photography = {"图片美化", "相机", "相册图库", "图片社区"};
    String[] photographyEN = {"图片美化", "相机", "相册图库", "图片社区"};
    String[] efficiency = {"办公软件", "笔记", "邮箱", "管理"};
    String[] efficiencyEN = {"办公软件", "笔记", "邮箱", "管理"};
    String[] gourmet = {"东方美食", "西式餐点", "烹饪食谱", "饮品达人", "厨房工具"};
    String[] gourmetEN = {"东方美食", "西式餐点", "烹饪食谱", "饮品达人", "厨房工具"};
    String[] life = {"外卖", "生活服务", "求职招聘", "住房装修", "汽车", "购票服务"};
    String[] lifeEN = {"外卖", "生活服务", "求职招聘", "住房装修", "汽车", "购票服务"};
    String[] health = {"运动健身", "医疗用药", "健康养生", "经期避孕"};
    String[] healthEN = {"运动健身", "医疗用药", "健康养生", "经期避孕"};
    String[] travel = {"旅游服务", "旅游攻略", "周边游", "旅行住宿", "旅行工具"};
    String[] travelEN = {"旅游服务", "旅游攻略", "周边游", "旅行住宿", "旅行工具"};
    String[] music = {"在线音乐", "电台", "K歌", "个性铃声", "乐器"};
    String[] musicEN = {"在线音乐", "电台", "K歌", "个性铃声", "乐器"};
    String[] sports = {"球类", "摩托", "赛车", "运动"};
    String[] sportsEN = {"球类", "摩托", "赛车", "运动"};
    String[] business = {"文件档案", "邮件管理", "笔记"};
    String[] businessEN = {"文件档案", "邮件管理", "笔记"};
    String[] news = {"头条", "科技互联网", "军事资讯", "财经资讯", "汽车资讯", "创业资讯"};
    String[] newsEN = {"头条", "科技互联网", "军事资讯", "财经资讯", "汽车资讯", "创业资讯"};
    String[] tools = {"浏览器", "输入法", "WIFI", "生活工具", "通讯工具"};
    String[] toolsEN = {"浏览器", "输入法", "WIFI", "生活工具", "通讯工具"};
    String[] entertainment = {"影视", "直播", "播放器", "视频制作"};
    String[] entertainmentEN = {"影视", "直播", "播放器", "视频制作"};
    String[] social = {"聊天交友", "婚恋相亲", "社区", "微博空间"};
    String[] socialEN = {"聊天交友", "婚恋相亲", "社区", "微博空间"};
    String[] newspaper = {"时尚", "生活", "新闻"};
    String[] newspaperEN = {"时尚", "生活", "新闻"};
    String[] finance = {"记账钱包", "投资", "贷款", "手机银行", "彩票"};
    String[] financeEN = {"记账钱包", "投资", "贷款", "手机银行", "彩票"};
    String[] reference = {};
    String[] referenceEn = {};
    String[] navigation = {"语音导航", "实时路况", "超速提醒", "语音操控", "实景导航"};
    String[] navigationEN = {"语音导航", "实时路况", "超速提醒", "语音操控", "实景导航"};
    String[] medical = {};
    String[] medicalEN = {};
    String[] book = {"小说书籍", "漫画", "百科问答", "八卦段子"};
    String[] bookEN = {"小说书籍", "漫画", "百科问答", "八卦段子"};
    String[] weather = {};
    String[] weatherEN = {};

    String[][] subLevelLabelsIndexArray = {children, education, shopping, photography, efficiency, gourmet, life, health,
            travel, music, sports, business, news, tools, entertainment, social, newspaper, finance, reference, navigation, medical, book, weather};
    String[][] subLevelLabelsENIndexArray = {childrenEN, educationEN, shoppingEN, photographyEN, efficiencyEN, gourmetEN, lifeEN, healthEN,
            travelEN, musicEN, sportsEN, businessEN, newsEN, toolsEN, entertainmentEN, socialEN, newspaperEN, financeEN, referenceEn, navigationEN, medicalEN, bookEN, weatherEN};

    //ThirdLevel
    String[] examination = {"驾照", "公务员考试", "四六级", "考研", "艺考", "司法考试", "资格考试"};
    String[] examinationEN = {"驾照", "公务员考试", "四六级", "考研", "艺考", "司法考试", "资格考试"};
    String[] studyabroad = {"雅思", "托福", "出国服务"};
    String[] studyabroadEN = {"雅思", "托福", "出国服务"};
    String[] onlinestore = {"综合商城", "美妆商城", "服饰商城", "生鲜超市", "数码商城", "奢侈品商城"};
    String[] onlinestoreEN = {"综合商城", "美妆商城", "服饰商城", "生鲜超市", "数码商城", "奢侈品商城"};
    String[] picturebeautification = {"滤镜", "拼图", "贴纸", "绘画", "海报", "美颜"};
    String[] picturebeautificationEN = {"滤镜", "拼图", "贴纸", "绘画", "海报", "美颜"};
    String[] camera = {"自拍神器", "专业相机", "证件照", "特效相机"};
    String[] cameraEN = {"自拍神器", "专业相机", "证件照", "特效相机"};
    String[] albumgallery = {"图片管理", "私密相册", "美女图库", "表情图库"};
    String[] albumgalleryEN = {"图片管理", "私密相册", "美女图库", "表情图库"};
    String[] photocommunity = {"二次元", "表情", "摄影作品"};
    String[] photocommunityEN = {"二次元", "表情", "摄影作品"};
    String[] takeaway = {"餐厅推荐", "生鲜配送"};
    String[] takeawayEN = {"餐厅推荐", "生鲜配送"};
    String[] domesticservices = {"家政", "做饭", "美容", "美甲", "按摩", "搬家", "证件通"};
    String[] domesticservicesEN = {"家政", "做饭", "美容", "美甲", "按摩", "搬家", "证件通"};
    String[] jobhunting = {"实习", "找工作"};
    String[] jobhuntingEN = {"实习", "找工作"};
    String[] housingdecoration = {"租房", "装修", "智能家居"};
    String[] housingdecorationEN = {"租房", "装修", "智能家居"};
    String[] car = {"二手车", "租车", "卖车", "违章查询", "停车", "学车"};
    String[] carEN = {"二手车", "租车", "卖车", "违章查询", "停车", "学车"};
    String[] ticketingservices = {"电影票", "戏剧票", "演唱会门票"};
    String[] ticketingservicesEN = {"电影票", "戏剧票", "演唱会门票"};
    String[] workout = {"跑步", "健身", "瑜伽", "运动社区", "运动记录", "运动装备"};
    String[] workoutEN = {"跑步", "健身", "瑜伽", "运动社区", "运动记录", "运动装备"};
    String[] travelservices = {"签证", "换汇", "旅游路线", "景点门票", "旅行团"};
    String[] travelservicesEN = {"签证", "换汇", "旅游路线", "景点门票", "旅行团"};
    String[] travellingguideline = {"穷游", "游记"};
    String[] travellingguidelineEN = {"穷游", "游记"};
    String[] touraround = {"自驾游", "周末游", "亲子游", "农家乐"};
    String[] touraroundEN = {"自驾游", "周末游", "亲子游", "农家乐"};
    String[] travelaccommodation = {"特色民宿", "旅游酒店", "旅行短租"};
    String[] travelaccommodationEN = {"特色民宿", "旅游酒店", "旅行短租"};
    String[] traveltools = {"户外导航", "轨迹记录", "户外装备", "指南针"};
    String[] traveltoolsEN = {"户外导航", "轨迹记录", "户外装备", "指南针"};
    String[] onlinemusic = {"音乐社区", "无损音乐"};
    String[] onlinemusicEN = {"音乐社区", "无损音乐"};
    String[] livingtools = {"日历", "计算器", "手电筒", "闹钟", "遥控器", "日记"};
    String[] livingtoolsEN = {"日历", "计算器", "手电筒", "闹钟", "遥控器", "日记"};
    String[] television = {"电影", "动漫", "体育视频", "VR", "短视频"};
    String[] televisionEN = {"电影", "动漫", "体育视频", "VR", "短视频"};
    String[] live = {"体育", "游戏", "美女"};
    String[] liveEN = {"体育", "游戏", "美女"};
    String[] chatfriends = {"陌生人", "兴趣", "匿名聊天", "私密聊天", "同志", "拉拉"};
    String[] chatfriendsEN = {"陌生人", "兴趣", "匿名聊天", "私密聊天", "同志", "拉拉"};
    String[] community = {"兴趣社区", "问答社区", "短视频社区", "宠物社区", "育儿社区"};
    String[] communityEN = {"兴趣社区", "问答社区", "短视频社区", "宠物社区", "育儿社区"};
    String[] investment = {"基金", "股票", "保险", "期贷"};
    String[] investmentEN = {"基金", "股票", "保险", "期贷"};

    String[] thirdLevelLabelParents = {"考试", "出国留学", "网上商城", "图片美化", "相机", "相册图库", "图片社区", "外卖",
            "生活服务", "求职招聘", "住房装修", "汽车", "购票服务", "运动健身", "旅游服务", "旅游攻略", "周边游", "旅行住宿",
            "旅行工具", "在线音乐", "生活工具", "影视", "直播", "聊天交友", "社区", "投资"};


    String[][] thirdLevelLabelsIndexArray = {examination, studyabroad, onlinestore, picturebeautification, camera,
            albumgallery, photocommunity, takeaway, domesticservices, jobhunting, housingdecoration, car, ticketingservices, workout,
            travelservices, travellingguideline, touraround, travelaccommodation, traveltools, onlinemusic, livingtools,
            television, live, chatfriends, community, investment};

    String[][] thirdLevelLabelsENIndexArray = {examinationEN, studyabroadEN, onlinestoreEN, picturebeautificationEN, cameraEN,
            albumgalleryEN, photocommunityEN, takeawayEN, domesticservicesEN, jobhuntingEN, housingdecorationEN, carEN, ticketingservicesEN, workoutEN,
            travelservicesEN, travellingguidelineEN, touraroundEN, travelaccommodationEN, traveltoolsEN, onlinemusicEN, livingtoolsEN,
            televisionEN, liveEN, chatfriendsEN, communityEN, investmentEN};

    public ObjectNode genGameLabelJson() {

        ObjectNode gameRootNode = Json.newObject();
        gameRootNode.put("className", "game");
        gameRootNode.put("classID", "001");

        for (int i = 0; i < gameClasses.length; i++) {
            ObjectNode classNode = Json.newObject();
            classNode.put("categoryID", String.format("%04d", i));
            classNode.put("categoryChName", gameClassesName[i]);
            classNode.put("categoryEnName", gameClassesNameEN[i]);
            ObjectNode labelNode = Json.newObject();
            for (int j = 0; j < gameClasses[i].length; j++) {
                ObjectNode labelNodeDetail = Json.newObject();
                labelNodeDetail.put("labelID", String.format("%04d", labelIndex++));
                labelNodeDetail.put("labelChName", gameClasses[i][j]);
                labelNodeDetail.put("labelEnName", gameClassesEN[i][j]);
                labelNodeDetail.put("labelMark", "");
                labelNode.set(gameClassesEN[i][j], labelNodeDetail);
            }
            classNode.set("labels", labelNode);
            gameRootNode.set(gameClassesNameEN[i], classNode);
        }
        return gameRootNode;
    }

    public ObjectNode genAppLabelJson() {

        ObjectNode appRootNode = Json.newObject();

        Map<String, String[]> appSubLevelLabels = new LinkedHashMap<>();
        Map<String, String[]> appSubLevelLabelsEN = new LinkedHashMap<>();
        for (int i = 0; i < appTopLevelLabels.length; i++) {
            appSubLevelLabels.put(appTopLevelLabels[i], subLevelLabelsIndexArray[i]);
            appSubLevelLabelsEN.put(appTopLevelLabels[i], subLevelLabelsENIndexArray[i]);
        }

        Map<String, String[]> appThirdLevelLabels = new LinkedHashMap<>();
        Map<String, String[]> appThirdLevelLabelsEN = new LinkedHashMap<>();
        for (int i = 0; i < thirdLevelLabelParents.length; i++) {
            appThirdLevelLabels.put(thirdLevelLabelParents[i], thirdLevelLabelsIndexArray[i]);
            appThirdLevelLabelsEN.put(thirdLevelLabelParents[i], thirdLevelLabelsENIndexArray[i]);
        }

        appRootNode.put("className", "app");
        appRootNode.put("classID", "002");

        for (int i = 0; i < appTopLevelLabels.length; i++) {
            ObjectNode classNode = Json.newObject();
            classNode.put("categoryID", String.format("%04d", i));
            classNode.put("categoryChName", appTopLevelLabels[i]);
            classNode.put("categoryEnName", appTopLevelLabelsEN[i]);
            ObjectNode labelNode = Json.newObject();
            String[] subLevel = appSubLevelLabels.get(appTopLevelLabels[i]);
            String[] subLevelEN = appSubLevelLabelsEN.get(appTopLevelLabels[i]);
            for (int j = 0; j < subLevel.length; j++) {
                ObjectNode labelNodeDetail = Json.newObject();
                labelNodeDetail.put("labelID", String.format("%04d", labelIndex++));
                labelNodeDetail.put("labelChName", subLevel[j]);
                labelNodeDetail.put("labelEnName", subLevelEN[j]);
                labelNodeDetail.put("labelMark", "");


                String[] thirdLevel = appThirdLevelLabels.get(subLevel[j]);
                if (thirdLevel != null) {
                    String[] thirdLevelEN = appThirdLevelLabelsEN.get(subLevel[j]);
                    ObjectNode subLabelNode = Json.newObject();
                    for (int k = 0; k < thirdLevel.length; k++) {
                        ObjectNode subLabelNodeDetail = Json.newObject();
                        subLabelNodeDetail.put("subLabelID", String.format("%04d", labelIndex++));
                        subLabelNodeDetail.put("subLabelChName", thirdLevel[k]);
                        subLabelNodeDetail.put("subLabelEnName", thirdLevelEN[k]);
                        subLabelNodeDetail.put("subLabelMark", "");
                        subLabelNode.set(thirdLevelEN[k], subLabelNodeDetail);
                    }
                    labelNodeDetail.set("subLabels", subLabelNode);
                }
                labelNode.set(subLevelEN[j], labelNodeDetail);
            }
            classNode.set("labels", labelNode);
            appRootNode.set(appTopLevelLabelsEN[i], classNode);
        }
        return appRootNode;
    }

    public ObjectNode genLabelJson() {

        ObjectNode gameNode = genGameLabelJson();
        ObjectNode appNode = genAppLabelJson();
        ObjectNode rootNode = Json.newObject();
        rootNode.set("game", gameNode);
        rootNode.set("app", appNode);
        return rootNode;
    }

}
