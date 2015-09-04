package cn.saymagic.weixin.server.handler;

import java.util.Date;

import cn.saymagic.weixin.server.bean.MsgRequest;
import cn.saymagic.weixin.server.bean.MsgResponseText;
import cn.saymagic.weixin.server.util.MsgXmlUtil;
import cn.saymagic.weixin.server.util.MsicUtil;



public abstract class BaseHandler<T extends MsgRequest> {

	
	public abstract String doHandleMsg(T msgRequest);
	
	protected String getResponseStringByContent(String content,MsgRequest msgRequest){
		MsgResponseText reponseText = new MsgResponseText();
		reponseText.setToUserName(msgRequest.getFromUserName());
		reponseText.setFromUserName(msgRequest.getToUserName());
		reponseText.setMsgType("text");
		reponseText.setCreateTime(new Date().getTime());
		reponseText.setContent(content);
		msgRequest.setMsgResponse(reponseText);
		return MsicUtil.formatString(MsgXmlUtil.textToXml(reponseText));
	}
	
}
