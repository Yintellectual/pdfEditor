package com.hanchen.pdfEditor.entity.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contractor {
    private String name;
	private String phone;
	private String wechat;
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getPhone(){
		return phone;
	}
	public void setpPhone(String phone){
		this.phone = phone;
	}
	
	public String getWechat(){
		return wechat;
	}
	public void setWechat(String wechat){
		this.wechat = wechat;
	}
}
