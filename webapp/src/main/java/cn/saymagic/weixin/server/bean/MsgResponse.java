package cn.saymagic.weixin.server.bean;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * 公众号回复给用户的消息-基本信息
 * 
 */
@DatabaseTable(tableName = "msg_response")
public class MsgResponse implements Serializable{
	
	private static final long serialVersionUID = -2672453770325583072L;

	@DatabaseField(generatedId=true)
	private Long id;
	
	@DatabaseField(width=500)
	private String MsgType;
	
	@DatabaseField(width=500)
	private String FromUserName;
	
	@DatabaseField(width=500)
	private String ToUserName;
	
	@DatabaseField(width=500)
	private Long CreateTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public Long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
	
}
