package com.zlw;

import com.zlw.rop.model.ResponseModel;
import com.rop.annotation.HttpAction;
import com.rop.annotation.IgnoreSignType;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;





@ServiceMethodBean
public class UserService{

	
	@ServiceMethod(ignoreSign= IgnoreSignType.YES, version="1.0" ,method="compex",needInSession = NeedInSessionType.NO)
	public String compex( MyUser myUser ){
		
		return "你好，中国！";
	}
	
	@ServiceMethod(ignoreSign= IgnoreSignType.NO, version="1.0" ,method="queryOrgList",needInSession = NeedInSessionType.NO)
	public String sayHelloworld(  ){
		
		
		return "你好，中国！";
	}
	
	
	@ServiceMethod(version="1.0" ,method="responseObject",needInSession = NeedInSessionType.NO, httpAction = HttpAction.POST)
	public User responseObject(  P p ){
		User user = new User();
		user.setPassword("1234321");
		user.setUsername("messi");
		return user;
	}
	
	@ServiceMethod(version="1.0" ,method="sign",needInSession = NeedInSessionType.YES, httpAction = HttpAction.POST,ignoreSign= IgnoreSignType.NO)
	public User testSign(  P p ){
		String name = p.getUsername();
		System.out.println( "name = "+name );
		User u = new User();
		u.setUsername(name);
		u.setPassword("password");
		return u;
	}
	
	@ServiceMethod(version="2.0" ,method="sign",needInSession = NeedInSessionType.NO, httpAction = HttpAction.POST,ignoreSign= IgnoreSignType.NO)
	public ResponseModel<User> testSign2(  P p ){
		p.getRopRequestContext().getSession();
		
		String name = p.getUsername();
		System.out.println( "name = "+name );
		User u = new User();
		u.setUsername(name);
		u.setPassword("password");
		return ResponseModel.newResponseModel(u);
	}
	
	
	
	@ServiceMethod(version="1.0" ,method="noparams",needInSession = NeedInSessionType.NO, httpAction = HttpAction.POST,ignoreSign= IgnoreSignType.YES)
	public User noparams(  ){
		User user = new User();
		user.setPassword("1234321");
		user.setUsername("messi");
		return user;
	}
	
	
}
