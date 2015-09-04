package cn.saymagic.weixin.server.bean;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * 用户发送给公众号的消息；
 * 
 */
@DatabaseTable(tableName = "msg_request")
public class MsgRequest{
	
	@DatabaseField(generatedId=true)
	private Long id;
	
	@DatabaseField
	private String MsgType;//消息类型
	
	@DatabaseField(width=500)
	private String MsgId;
	
	@DatabaseField(width=500)
	private String FromUserName;//openid
	
	@DatabaseField(width=500)
	private String ToUserName;
	
	@DatabaseField(width=500)
	private String CreateTime;
	
	//文本消息
	@DatabaseField(width=500)
	private String Content;
	
	//语音消息
	@DatabaseField(width=500)
	private String Recognition;
	
	//图片消息
	@DatabaseField(width=500)
	private String PicUrl;
	
	//地理位置消息
	@DatabaseField(width=500)
	private String Location_X;
	
	@DatabaseField(width=500)
	private String Location_Y;
	
	@DatabaseField(width=500)
	private String Scale;
	
	@DatabaseField(width=500)
	private String Label;
	
	//事件消息
	@DatabaseField(width=500)
	private String Event;
	
	@DatabaseField(width=500)
	private String EventKey;
	
	@DatabaseField(canBeNull = true, foreign = true, columnName = "MsgResponse")  
	private MsgResponse msgResponse;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MsgResponse getMsgResponse() {
		return msgResponse;
	}
	public void setMsgResponse(MsgResponse msgResponse) {
		this.msgResponse = msgResponse;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
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
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getLocation_X() {
		return Location_X;
	}
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}
	public String getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}
	public String getScale() {
		return Scale;
	}
	
	public String getRecognition() {
		return Recognition;
	}
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	
}
