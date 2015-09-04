package cn.saymagic.weixin.server.bean;

public class TulingResult
{
	private int code;
	private String text;

	public TulingResult()
	{
	}

	public TulingResult(int resultCode, String msg)
	{
		this.code = resultCode;
		this.text = msg;
	}

	public TulingResult(int resultCode)
	{
		this.code = resultCode;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getText()
	{
		if (code > 400000 || text == null  
				|| text.trim().equals(""))  
		{  
			return "该功能等待开发...";  
		}
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}



}
