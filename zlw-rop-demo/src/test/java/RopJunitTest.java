import com.zlw.commons.codec.AES;
import com.rop.utils.RopUtils;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RopJunitTest {

//	public static final String SERVER_URL = "http://10.128.31.133:8081/personnelReport/router";
	public static final String SERVER_URL = "http://127.0.0.1:8080/router";

	@Test
	public void login() {
		RestTemplate restTemplate = new RestTemplate();
		org.springframework.util.MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("method", "login");// <--指定方法名称
		form.add("messageFormat", "json");// <--指定消息应答格式json / xml
		form.add("v", "1.0");// 版本号
		form.add("appKey", "00001");// 版本号
		form.add("username", "sssss");// 版本号
		String params = "{\"username\": \"messi\",\"pwd\": \"bug\"}";
		form.add("params", params);
		String response = restTemplate.postForObject(SERVER_URL, form,String.class);
		System.out.println("response responseObject = " + response);

	}
	
	
	@Test
	public void test() {
		RestTemplate restTemplate = new RestTemplate();
		org.springframework.util.MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("method", "mobReport.soOrgStructure");// <--指定方法名称
		// form.add("messageFormat", "json");// <--指定消息应答格式json / xml
		form.add("v", "1.0");// 版本号
		form.add("appKey", "00001");// 版本号
		String params = "{\"parentNo\":\"50000027\",\"levelDetail\":\"\"}";
		form.add("sessionId", "5440e34fe82c408c977649ed963aded6");// 
		form.add("params", params);
		String response = restTemplate.postForObject(SERVER_URL, form, String.class);
		System.out.println("response responseObject = " + response);
	}

	@Test
	public void testObject() {
		RestTemplate restTemplate = new RestTemplate();
		org.springframework.util.MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		// sessionid 登录时服务端返回值，有效期默认30分钟
		// form.add("sessionId", "FCB34BCA001BAA087A3212C5AA831426");
		form.add("method", "compex");// <--指定方法名称
		form.add("messageFormat", "json");// <--指定方法名称
		form.add("v", "1.0");// 版本号
		form.add("appKey", "00001");// 版本号
		
		String params = "{\"address\": \"成都市\",\"user\": {\"username\": \"messi\",\"password111\": \"bug\"}}";
        form.add("params", params);//params必须参数，没有时传 {}
		
		String response = restTemplate.postForObject(SERVER_URL, form, String.class);
		System.out.println("response test = " + response);

	}

	String key = "EA0CC42AC1581A06";
	
	@Test
	public void testSign() {
		RestTemplate restTemplate = new RestTemplate();
		org.springframework.util.MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("method", "sign");// <--指定方法名称
//		form.add("format", "xml");// <--指定消息应答格式json / xml
		form.add("format", "json");// <--指定消息应答格式json / xml
		form.add("v", "2.0");// 版本号
		form.add("appKey", "00001");// 数据签名key
		form.add("paramsKey", "0-00001");//参数加密/解密key
//		form.add("sessionId", "5440e34fe82c408c977649ed963aded6");// 
		form.add("sessionId", "messi");// 
		
		String params = "{\"username\": \"messi\",\"pwd\": \"bug\"}";
        params = AES.Encrypt(params, key);
        form.add("params", params);//params必须参数，没有时传 {}
//        Map<String,String> requestP= new HashMap<>();
//        requestP.put("username","messi");
//        requestP.put("pwd","bug");
		String sign = RopUtils.sign(params ,"abcdeabcdeabcdeabcdeabcde");
        form.add("sign", sign);
        
		String response = restTemplate.postForObject(SERVER_URL, form, String.class);
		System.out.println("response test = " + response);
	}

	@Test
	public void noparams() {
		RestTemplate restTemplate = new RestTemplate();
		org.springframework.util.MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("method", "noparams");// <--指定方法名称
//		form.add("format", "xml");// <--指定消息应答格式json / xml
//		form.add("format", "json");// <--指定消息应答格式json / xml
		form.add("v", "1.0");// 版本号
		form.add("appKey", "00001");// 数据签名key
//		form.add("paramsKey", "0-00001");//参数加密/解密key
//		form.add("sessionId", "5440e34fe82c408c977649ed963aded6");// 
//		form.add("sessionId", "messi");// 
		
		String params = "{\"username\": \"messi\",\"pwd\": \"bug\"}";
        params = AES.Encrypt(params, key);
        form.add("params", params);//params必须参数，没有时传 {}
//        Map<String,String> requestP= new HashMap<>();
//        requestP.put("username","messi");
//        requestP.put("pwd","bug");
		String sign = RopUtils.sign(params ,"abcdeabcdeabcdeabcdeabcde");
        form.add("sign", sign);
//        
		String response = restTemplate.postForObject(SERVER_URL, form, String.class);
		System.out.println("response test = " + response);
	}
}
