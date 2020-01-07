package com.example.zyjulib.resource;

import android.Manifest;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.zyjulib.BuildConfig;
import com.example.zyjulib.utile.CommonUtils;

import static com.example.zyjulib.utile.CommonUtils.getContext;
import static com.example.zyjulib.utile.CommonUtils.getFileDownRootPath;

/**
 * Created by zyju on 2019/6/3.
 */

public class Contect {

    public static String LoginType = loginType.Default + "";//登陆类型

    public static boolean isTestHttp = BuildConfig.ISTEST;//是否测试环境上线调整

    public static String htmlStyle="<style>*{padding:0;margin:0;}html,body{margin:0px;padding:0px;font-family:Helvetica Neue,Helvetica,PingFang SC,Hiragino Sans GB,Microsoft YaHei,SimSun,sans-serif;font-size:16px;line-height:1.8;letter-spacing:1;-webkit-font-smoothing:antialiased;}img{max-width:100%;display:block;height:auto;}a{text-decoration:none;}iframe{width:100%;}p{line-height:1.8;margin:0px;padding:0;font-size:16px;letter-spacing:1px;}table{word-wrap:break-word;word-break:break-all;max-width:100%;border:none;border-color:#999;}.mce-object-iframe{width:100%;box-sizing:border-box;margin:0;padding:0;}ul,ol{list-style-position:inside;}</style>";
    //表情包
    public static int EMOTICON_CLICK_TEXT = 1;
    public static int EMOTICON_CLICK_BIGIMAGE = 2;

    public interface filePath{
        String picture = getFileDownRootPath() + "/picture";
        String publicFileDowns = getFileDownRootPath() + "/" + getContext().getPackageName() + "/publicFileDowns";
        String publicYaSuoImgFiles = getFileDownRootPath() + "/" + getContext().getPackageName() + "/publicYaSuoImgFiles";
    }
    public interface EmailConfig{
        String ReceiveEmail = "zyj@shinnor.com";
        String SendEmail = "275213508@qq.com";
        String SendEmailPassword = "yybnycyqtfejbgjg";
        String SMTPHost = "smtp.qq.com";
        String Port = "465";
    }
    public interface eventPost {
        int baseActivity = 601;
        int baseFragment = 602;

        int upAllActivity = 1001;//所有,慎用
        int LoginOut = 1002;//登出
        int UpdateUserInfoUI = 1003;//更新Myfragment
        int Login = 1004;//登录
        int web = 1005;//webActivity


        int UnLogin = 100;
        int notNetError = 101;
        int nulldata = 102;
        int dataError = 103;

        int GuaPinEdit = 100010;//瓜评编辑
        int GuaPinImageEdit = 100011;//图文瓜评编辑
        int GuaPinTextEdit = 100012;//文字瓜评编辑
        int main = 10000;//首页
        int homepageFragment2 = 10001;//首页
        int xshd = 10002;//限时活动
        int hdyg = 10003;//活动预告
        int xx_xx = 10004;//消息
        int xx_tz = 10005;//通知
        int my = 10006;//我的
        int lx = 10007;//立享
        int xx = 10008;//消息
        int xqinfo = 10009;//消息
        int userinfo = 10010;//消息
        int userinfofgDraFt = 10011;//消息
        int userinfofg = 10011;//消息

        //推送通知:type:
        int jzdp = 1;//1极致单品
        int yzpl = 2;//2优质评论
        int cnxh = 3;//3猜你喜欢
        int xttz = 4;//4系统通知
        int dz = 5;//5点赞
        int pl = 6;//6评论
        int fx = 7;//7分享
        int sc = 8;//8收藏
        int jpxx = 9;//私信
    }

    public interface loginType {
        int Default = 0;
        int QQ = 1;
        int WeiXin = 2;

    }

    public interface config {
        /**
         * 默认每页加载数量
         */
        int defaultPageSize = 20;
        /**
         * dialog延迟结束时间
         */
        int dialogDimesTime = 0;
        /**
         * 保存草稿周期时间 ms
         */
        int saveDraftTime = 5000;
        /**
         * 加载更多完成后FootView的停留时间
         */
        int LoadMoreTime = 500;
        /**
         * 上拉弹性动画高度
         */
        int LoadMoreScollorHeight = 600;
        /**
         * 压缩中执行压缩条件.忽略大小
         * 单位:kb
         */
        int ZoomIgnoreBy = 800;
    }

    public interface path {
        String path = CommonUtils.getPicturePath() + "/FenXiangTu.png";
        String WebUrl = "http://ps.shinnor.com/gua-h5/privacy.html";
    }

    public interface sendLenth {
        int youdianLenth = 0;
        int quedianLenth = 0;
    }

    public interface AppKeyAndAppSecret {
        String UMAppKey = "5d15c78b0cafb2e7f300110f";

        String JPUSH_APPKEY = "6451913c81f695a7f37486e6";
//        String JPUSH_APPKEY = "6df79dad9421b20b46997851";//测试版

        String QQZoneKey = "1109384844";
        String QQZoneSecret = "oc47loPNmVURjeAd";

        String WeixinKey = "wxbc1ddc2dad031df5";
        String WeixinSecret = "ff7090e9a8e560896d4599697cccc45e";

        String SinaWeiboKey = "3528104041";
        String SinaWeiboSecret = "7f7dbf535727d25e44524bb56c7fab66";
        String SinaWeibohttp = "http://sns.whalecloud.com";
    }

    public interface SpConstant {
        //引导界面标识位
        String SP_LOGIN_REQUEST_USER_INFO = "SP_LOGIN_REQUEST_USER_INFO";
        String SP_Q_USER_INFO = "SP_Q_USER_INFO";
        String USER_INFO = "USER_INFO";
        String SAVE_DRAFT = "SAVE_DRAFT";
        String BASE_URL = "BASE_URL";

        String SP_SHORCH_HISTORY = "SP_SHORCH_HISTORY";
        String HISTORY = "HISTORY";

        String SPNAME_CONFIG = "config";


        //引导界面标识位
        String SPKEY_ISPROMPTED = "isPrompted";
        String SPKEY_ISREADXIEYI = "isReadXY";
        //是否所有权限都赋予
        String SPKEY_ISPERMITION = "isPermission";


        String SP_ISLOGIN = "sp_islogin";
        String SP_ISTEST_URL = "SP_ISTEST_URL";

        String SP_USER_ANDROID_ID = "SP_USER_ANDROID_ID";
        String SP_USER_ID = "SP_USER_ID";
        String SP_USER_NAME = "SP_USER_NAME";
        String SP_USER_PHOTO = "SP_USER_PHOTO";
        String SP_USER_NICHENG = "SP_USER_NICHENG";
        String SP_USER_PHONE = "SP_USER_PHONE";
        String SP_USER_GEADER = "SP_USER_GEADER";
        String SP_USER_ADDRESS = "SP_USER_ADDRESS";
        String SP_USER_XXADDRESS = "SP_USER_XXADDRESS";
        String SP_USER_ABOUT = "SP_USER_ABOUT";
        String SP_USER_IDCODE = "SP_USER_IDCODE";
        String SP_USER_LEVEL = "SP_USER_LEVEL";
        String SP_USER_SYNOPSIS = "SP_USER_SYNOPSIS";
        String SP_USER_MAIL = "SP_USER_MAIL";


    }


    public static String UserFanKuitake_phone_path;

    public interface RequestCode {

        /**
         * 拍照
         */
        int CHECK_PHONE = 11;
        int CHECK_YOUDIANPHONE = 12;
        int CHECK_QUEDIANPHONE = 13;
        /**
         * 选择图片
         */
        int CHECK_PICTURES = 1;
        int CHECK_YOUDIANPICTURES = 2;
        int CHECK_QUEDIANPICTURES = 3;

        /**
         * 编辑头像
         */
        int EDIT_PHOTO_CHECK = 101;
        int EDIT_PHOTO_SELECT = 102;
        /**
         * 编辑瓜评
         */
        int EDIT_IMG_GUAPIN = 1001;
        int EDIT_TEXT_GUAPING = 1002;
        /**
         * 后台运行权限
         */
        int BACKGROUD_RUN_PERSSION = 10001;
    }

    public interface PermissionsConstant {
        public static final int REQUEST_CAMERA = 1;
        public static final int REQUEST_EXTERNAL_READ = 2;
        public static final int REQUEST_EXTERNAL_WRITE = 3;
        public static final int ACCESS_FINE_LOCATION = 4;
        public static final int REQUEST_EXTERNAL_READ_WRITE = 5;

        public static final String[] PERMISSIONS_CAMERA = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        public static final String[] PERMISSIONS_EXTERNAL_WRITE = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public static final String[] PERMISSIONS_EXTERNAL_READ = {
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        public static final String[] PERMISSIONS_EXTERNAL_READ_WRITE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public static final String[] PERMISSIONS_BLUETOOTH = {
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public static final String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA,
//                Manifest.permission.INTERNET,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.CALL_PHONE,
//                Manifest.permission.SYSTEM_ALERT_WINDOW,
//                Manifest.permission.GET_ACCOUNTS,
        };

    }

    /**
     * 友盟统计
     * 事件id，事件名称，事件类型（1表示计算事件，0表示多参数类型事件）参考参数
     */
    public interface TongJiKey{
        String uid = "uid";
        String keyword = "keyword";
        String gua_id = "gua_id";
        String plaform = "plaform";
        String gua_title = "gua_title";


        String action_uid = "action_uid";
        String banner_id = "banner_id";

        /**
         * 友盟自定义事件
         */
        // app相关
        String gt_app_launch ="gt_app_launch";//App启动
        String gt_app_backgroundh ="gt_app_backgroundh";//App进入后台
        String gt_app_foreground ="gt_app_foreground";//App进入前台

        // 瓜评相关
        String gt_gp_search="gt_gp_search";//搜索瓜评
        String gt_gp_report="gt_gp_report";//举报瓜评
        String gt_gp_like="gt_gp_like";//瓜评点赞
        String gt_gp_unlike="gt_gp_unlike";//取消瓜评点赞
        String gt_gp_collect="gt_gp_collect";//瓜评收藏
        String gt_gp_uncollect="gt_gp_uncollect";//取消瓜评收藏
        String gt_gp_comment="gt_gp_comment";//瓜评评论
        String gt_gp_share="gt_gp_share";// 瓜评分享
        String gt_gp_publish="gt_gp_publish";// 发布瓜评
        String gt_gp_edit="gt_gp_edit";// 修改瓜评
        String gt_gp_delete="gt_gp_delete";//  删除瓜评
        String gt_gp_draft_edit="gt_gp_draft_edit";// 编辑瓜评草稿
        String gt_gp_draft_delete="gt_gp_draft_delete";// 删除瓜评草稿


        // 用户相关  action_uid: 被操作的用户id
        String gt_user_login="gt_user_login";// 用户登录
        String gt_user_logout="gt_user_logout";// 用户登出
        String gt_user_follow="gt_user_follow";// 关注用户
        String gt_user_unfollow="gt_user_unfollow";// 取消关注用户
        String gt_user_share="gt_user_share";// 分享用户
        String gt_user_addblack="gt_user_addblack";// 用户拉黑
        String gt_user_im="gt_user_im";// 私信用户
        String gt_im_addblack="gt_im_addblack";// 私信拉黑
        String gt_im_removeblack="gt_im_removeblack";// 私信取消拉黑
        String gt_user_report="gt_user_report";//  举报用户
        String gt_banner_click="gt_banner_click";// 用户点击banner

    }
    public interface HttpPostKey {
        //开始页数
        String pageNum = "pageNum";
        //每页数量
        String pageSize = "pageSize";
        //cloumnId
        String cloumnId = "cloumnId";
        //唯一id
        String uuid = "uuid";
        //用户Uuid
        String userUuid = "userUuid";
        //android ios区分
        String guaapp = "guaapp";
        //用户id
        String userId = "userId";
        //活动id
        String activityId = "activityId";
        //产品id
        String productId = "productId";
        //中奖id
        String winId = "winId";
        //手机号
        String phone = "phone";
        //地址
        String address = "address";
        //类型(1:关注列表，2:男神,3:女神,4:逛友圈)
        // 瓜评类型(0:文字，1:图文)
        String type = "type";
        String token = "token";

        //瓜评标题名称
        String titleName = "titleName";
        //产品标题
        String contextTitle = "contextTitle";
        String tempId = "tempId";
        //发布类型
        String contextType = "contextType";
        //瓜评内容Json串
        String productContextDescribe = "productContextDescribe";
        //父评论的Id(如果是针对瓜评的评论就写0，回复别人的评论就写别人的评论Id)
        String parentCommentId = "parentCommentId";
        //评论内容
        String context = "context";

        //瓜评Id
        String contextId = "contextId";
        //评论Id
        String commentId = "commentId";
        //投诉类型(1:滥用原创,2:诱导行为,3：不实行为)
        String complaintType = "complaintType";
        //投诉描述
        String complaintDescribe = "complaintDescribe";
        //投诉图片1
        String complaintImage1 = "complaintImage1";
        //投诉图片2
        String complaintImage2 = "complaintImage2";
        //投诉图片3
        String complaintImage3 = "complaintImage3";
        //投诉图片4
        String complaintImage4 = "complaintImage4";

        //被关注用户Uuid
        String beConcernedAboutUserUuid = "beConcernedAboutUserUuid";

        //瓜评内容用户id
        String contextUserUuid = "contextUserUuid";
        //分享渠道，0微信，1QQ，2微博，3朋友圈
        String sharingChannels = "sharingChannels";
        //栏目id
        String id = "id";
        //用户身份证号
        String idCode = "idCode";
        //用户头像
        String icon = "icon";
        //用户头像
        String bgImage = "bgImage";
        //用户真实姓名
        String name = "name";
        //用户昵称
        String nickName = "nickName";
        //用户性别
        String sex = "sex";
        //用户个性签名
        String personalizedSign = "personalizedSign";
        //简介
        String synopsis = "synopsis";
        //邮箱
        String mail = "mail";
        //反馈类型, 0,咨询 1,建议 2,其他
        String feedbackType = "feedbackType";
        //反馈信息
        String info = "info";
        //图片，多张图片逗号隔开，英文’,’
        String feedbackImg = "feedbackImg";
        //联系方式
        String contactWay = "contactWay";

        //手机设备id
        String gid = "gid";

        //手机号
        String mobile = "mobile";
        //验证码
        String smsValidCode = "smsValidCode";
        String code = "code";
        String areaCode = "areaCode";

        //手机型号
        String model = "model";
        //手机品牌
        String brand = "brand";
        //手机系统
        String os = "os";
        //手机系统版本
        String osVersion = "osVersion";
        //手机运营商
        String operator = "operator";
        //手机分辨率
        String resolution = "resolution";
        //手机网络
        String network = "network";
        //app版本
        String appVersion = "appVersion";
        //app版本号
        String versionNumber = "versionNumber";
        //iOS, Android
        String appId = "appId";
        //通知类型，1极致单品，2优质评论，3猜你喜欢
        String dataType = "dataType";

        //提示评论开关 0,关闭 1，开启 默认1
        String promptComment = "promptComment";
        //提示分享开关 0,关闭 1，开启 默认1
        String promptShare = "promptShare";
        //提示点赞开关 0,关闭 1，开启 默认1
        String promptPraised = "promptPraised";
        //提示系统通知开关 0,关闭 1，开启 默认1
        String promptRecord = "promptRecord";

        //第三方授权信息
        String openId = "openId";
        String unionId = "unionId";
        String source = "source";

        //被举报人uuid
        String beReportUserUuid = "beReportUserUuid";
        //举报理由
        String reasonsReporting = "reasonsReporting";
        //举报描述
        String reportDescription = "reportDescription";
        //举报图片1
        String reportImage1 = "reportImage1";

    }

}
