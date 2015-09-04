package cn.saymagic.weixin.server.handler;


import cn.saymagic.weixin.server.bean.MsgRequest;

public class EventHandler extends BaseHandler {
	@Override
	public String doHandleMsg(MsgRequest msgRequest) {
		if(!"event".equals(msgRequest.getMsgType()))
			return null;
		else
			if("subscribe".equals(msgRequest.getEvent())){
				return getResponseStringByContent("欢迎您关注瑰族，我将定期为您推荐一些经典美食、美食心得、美食做法。要知道，吃，是一种情怀。欢迎您加入吃货的世界。", msgRequest);
			}else if("CLICK".equals(msgRequest.getEvent())){
				String eventKey = msgRequest.getEventKey();
				String content = "";
				content = "Click Menu"+msgRequest.getEventKey();
				return getResponseStringByContent(content, msgRequest);
			}
			else{ 
				return getResponseStringByContent("暂时无法处您的请求，请稍后再试。", msgRequest);
			}

	}
}
