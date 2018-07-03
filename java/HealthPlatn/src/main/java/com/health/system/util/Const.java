package com.health.system.util;

import org.springframework.context.ApplicationContext;
/**
 * 项目名称：
 * 
*/
public class Const {
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_MEMBER = "sessionMember";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_menuList = "menuList";			//当前菜单
	public static final String SESSION_allmenuList = "allmenuList";		//全部菜单
	public static final String SESSION_FRONT_MENULIST = "frontMenulist"; //前台菜单
	public static final String SESSION_QX = "QX";
	public static final String SESSION_userpds = "userpds";			
	public static final String SESSION_USERROL = "USERROL";				//用户对象
	public static final String SESSION_USERNAME = "USERNAME";			//用户名
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String LOGIN = "/manager.do";				//登录地址
	public static final String MEMBER_LOGIN = "/web/member/member_login";	//会员登录地址
	public static final String SYSNAME = "admin/config/SYSNAME.txt";	//系统名称路径
	public static final String PAGE	= "admin/config/PAGE.txt";			//分页条数配置路径
	public static final String EMAIL = "admin/config/EMAIL.txt";		//邮箱服务器配置路径
	public static final String SMS1 = "admin/config/SMS1.txt";			//短信账户配置路径1
	public static final String SMS2 = "admin/config/SMS2.txt";			//短信账户配置路径2
	public static final String FWATERM = "admin/config/FWATERM.txt";	//文字水印配置路径
	public static final String IWATERM = "admin/config/IWATERM.txt";	//图片水印配置路径
	public static final String WEIXIN	= "admin/config/WEIXIN.txt";	//微信配置路径
	public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";	//图片上传路径
	public static final String FILEPATHFILE = "uploadFiles/file/";		//文件上传路径
	public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; //二维码存放路径
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)).*";	//不对匹配该值的访问路径拦截（正则）
	public static final Integer STR_LENGTH = 35;		//指定资讯内容摘要的字数
	public static final Integer NEWS_TYPE_COMPONY = 1;		//公司资讯
	public static final Integer NEWS_TYPE_HEALTH = 2;		//健康资讯
	public static final Integer NEWS_TYPE_EDUCATION = 3;	//教育资讯
	public static final Integer NEWS_TYPE_TRAVEL = 4;		//旅游资讯
	public static final Integer NEWS_MENU_COMPONY = 8;		//公司资讯
	public static final Integer NEWS_MENU_HEALTH = 9;		//健康资讯
	public static final Integer NEWS_MENU_EDUCATION = 20;	//教育资讯
	public static final Integer NEWS_MENU_TRAVEL = 21;		//旅游资讯
	public static final Integer MENU_PRODUCT = 3;		//菜单id号
	
	
	public static final String FILE_UPLOAD_TEMP = "\\uploadFiles\\uploadImgs\\temp\\";
	public static final String FILE_UPLOAD_DIR = "\\uploadFiles\\uploadImgs\\normal\\";
	
	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	
	public static final String PATH_HOME = "home.jsp";		//首页
	public static final String PATH_NEWS = "news/newsMain.jsp";	//资讯列表页
	
	/**
	 * APP Constants
	 */
	//app注册接口_请求协议参数)
	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[]{"countries","uname","passwd","title","full_name","company_name","countries_code","area_code","telephone","mobile"};
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[]{"国籍","邮箱帐号","密码","称谓","名称","公司名称","国家编号","区号","电话","手机号"};
	
	//app根据用户名获取会员信息接口_请求协议中的参数
	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};
	
	//登录用户类型
	public static final String LOGIN_TYPE_ADMIN = "Admin";
	public static final String LOGIN_TYPE_USER = "User";
	
	//会员类型
	public static final String MEMBER_TYPE_CUSTOMER = "1";
	public static final String MEMBER_TYPE_BUSSINESS = "2";
	
	//会员状态
	public static final String MEMBER_STATUS_NORMAL = "1";	//正常
	public static final String MEMBER_STATUS_EXPIRE = "2";	//过期
	
	//会员续约状态
	public static final String MEMBER_RENEW_STATUS_FAIL = "3";//申请失败
	public static final String MEMBER_RENEW_STATUS_DONE = "4";//已签约
	public static final String MEMBER_RENEW_STATUS_EXPIRE = "5";//已过期
	
	//短信相关
	public static final String SMS_SIGN_NAME = "绿色产品购物网站";//短信签名
	public static final String SMS_TEMPLATE_CODE = "SMS_94945019";//短信验证码模版
	
	//短信通知模版（续解约申请提醒）: 
	//尊敬的${name}，您的会员资格即将在一个星期后过期，为了不影响您的使用，请尽快登录网站进行续约或解约申请！
	//参数说明: name:会员名
	public static final String SMS_TEMPLATE_INFO = "SMS_95030013";	
	//短信通知成功模版（续解约申请成功提醒）:
	//模版内容：尊敬的${name}（会员号：${cardno}），您的会员${type}申请已成功，请您前往附近门店办理相关手续
	//参数说明：name:会员名, cardno:会员号, type:赋值为"续约"或"解约"
	public static final String SMS_TEMPLATE_INFO_OK = "SMS_95760009";	
	
	public static final String SMS_TEMPLATE_PHONE = "SMS_95760006";	//修改手机号码
	public static final String SMS_TEMPLATE_PWD = "SMS_95750010";  //忘记密码手机验证
	public static final int SESSION_SMS_TIME = 5;	//session中存储的短信验证码的有效时长（分钟）
	public static final int SMS_SEND_MAX_NUM = 3;		//手机每天发送验证码的最大次数

	public static final String MENU_ORDER_TODO = "13";		//待处理菜单
	public static final String MENU_ORDER_ALL  = "14";		//所有菜单
	public static final String MENU_ORDER_TOPAY = "15";		//待付款菜单
	public static final String MENU_ORDER_TORECEIVE = "16";		//待收货菜单
	public static final String MENU_ORDER_FINISHED = "17";		//已完成菜单
	public static final String MENU_ORDER_CLOSED = "18";		//已关闭菜单
	
	public static final String REFUND_STATUS_REFUND = "1";			//退款申请
	public static final String REFUND_STATUS_REFUND_GOODS = "2";    //退货退款申请
	public static final String REFUND_STATUS_GOODS_SEND = "3";		//货物已寄出
	public static final String REFUND_STATUS_REFUNDED = "4";		//退款完成
	
}
