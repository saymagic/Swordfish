package cn.saymagic.weixin.server.handler;

import java.io.IOException;
import java.util.Date;

import cn.saymagic.weixin.server.bean.MsgRequest;
import cn.saymagic.weixin.server.util.TulingUtil;



public class TextHandler extends BaseHandler {

	@Override
	public String doHandleMsg(MsgRequest msgRequest) {
		try {
			return getResponseStringByContent(TulingUtil.getContentStr(null==msgRequest.getContent()?msgRequest.getRecognition():msgRequest.getContent(), msgRequest.getFromUserName()),msgRequest);
		} catch (IOException e) {
			return getResponseStringByContent("很抱歉暂时无法处理您的消息，请稍后重试",msgRequest);
		}

	}

}
