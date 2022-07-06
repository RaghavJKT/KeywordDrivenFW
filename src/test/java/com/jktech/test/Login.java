package com.jktech.test;

import org.testng.annotations.Test;

import ExcelUtility.KeyWordEngine;



public class Login{
	
	public KeyWordEngine keyword;
	
	@Test
	public void login() {
		keyword = new KeyWordEngine();
		keyword.startEngine("sheet1");
		
	}
	
	//
	
	

}
