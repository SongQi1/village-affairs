package com.bocs.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

public class HideIdCardTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String idCard;
	
	private int front;
	
	private int end;
	
	
	
	
	public String getIdCard() {
		return idCard;
	}




	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}




	public int getFront() {
		return front;
	}




	public void setFront(int front) {
		this.front = front;
	}




	public int getEnd() {
		return end;
	}




	public void setEnd(int end) {
		this.end = end;
	}


	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		String str = "";
		if(StringUtils.isNotBlank(idCard)){
			str = hiddenIdCard(idCard, front, end);
		}
		try {
			out.print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	
	/**
     * 隐藏身份证号信息
     * @author songqi
     * @param idCard 身份证号信息
     * @param front 开始显示的位数
     * @param end 结束显示的位数
     * @return
     */
    private String hiddenIdCard(String idCard, int front, int end) {
		String newStr = "";
		if(StringUtils.isNotBlank(idCard)){
			idCard = idCard.trim();
			if(idCard.length() <= (front + end)){
				newStr = idCard;
			}else{
				int hiddenLen = idCard.length() - (front + end);
				String frt = idCard.substring(0, front);
				String ed = idCard.substring((front+hiddenLen), idCard.length());
				for(int i=0;i<hiddenLen;i++){
					frt += "*";
				}
				newStr = frt + ed;
			}
		}
		return newStr;
	}

}
