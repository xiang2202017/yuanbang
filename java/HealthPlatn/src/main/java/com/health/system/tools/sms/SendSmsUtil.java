package com.health.system.tools.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.health.system.tools.sms.entity.SendSmsRequest;
import com.health.system.tools.sms.entity.SendSmsResponse;

/**
 * 短信发送类
 * @author XiangYu
 *
 */
public class SendSmsUtil {
	
	//产品名称:云通信短信API产品
    static final String product = "Dysmsapi";
    //产品域名
    static final String domain = "dysmsapi.aliyuncs.com";

    // 阿里云短信服务器API访问权限用户名及密码
    static final String accessKeyId = "LTAIyWh3qMVOrnlE";
    static final String accessKeySecret = "3I7FpZkPR5GOrHoi4MUZOjJkgCyD8O";
    
	/**
	 * 短信验证码发送方法
	 * @param phone 要发送的手机号码
	 * @param params 发送的json格式变量字符串，如：String params = "{\"number\":"+ num +"}",其中number表示templateCode模版中的变量名，发送的变量名和模板中的变量名必须一致
	 * @param signName 短信签名,取值Const.SMS_SIGN_NAME
	 * @param templateCode 短信模版,取值Const.SMS_TEMPLATE_XXX
	 */
	public static SendSmsResponse sendCodeSms(String phone, String params, String signName, String templateCode) throws ClientException{
		try{
			//超时时间
	        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
	        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
	        
	        //初始化acsClient,暂不支持region化
	        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
	        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
	        IAcsClient acsClient = new DefaultAcsClient(profile);
	        
	        //组装请求对象
	        SendSmsRequest request = new SendSmsRequest();
	        //待发送手机号
	        request.setPhoneNumbers(phone);
	        //短信签名
	        request.setSignName(signName);
	        //短信模板
	        request.setTemplateCode(templateCode);
	        //模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
	        request.setTemplateParam(params);
	        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
	        return sendSmsResponse;
		}catch(ClientException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 短信发送异常原因
	 * @param code
	 */
	public static String getErrMsg(String code){
		String result = "";
		switch (code) {
		case "":
			
			break;

		default:
			break;
		}
		return result;
	}
	
}
