package com.itc.weixin.service;

import com.jfinal.weixin.sdk.msg.in.InImageMsg;
import com.jfinal.weixin.sdk.msg.in.InLinkMsg;
import com.jfinal.weixin.sdk.msg.in.InLocationMsg;
import com.jfinal.weixin.sdk.msg.in.InShortVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.InVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InVoiceMsg;
import com.jfinal.weixin.sdk.msg.in.event.InCustomEvent;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InLocationEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMassEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.in.event.InPoiCheckNotifyEvent;
import com.jfinal.weixin.sdk.msg.in.event.InQrCodeEvent;
import com.jfinal.weixin.sdk.msg.in.event.InShakearoundUserShakeEvent;
import com.jfinal.weixin.sdk.msg.in.event.InTemplateMsgEvent;
import com.jfinal.weixin.sdk.msg.in.event.InVerifyFailEvent;
import com.jfinal.weixin.sdk.msg.in.event.InVerifySuccessEvent;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.OutMsg;

/**
 * 业务逻辑组件的抽象类，实际处理各类信息对象，所有逻辑隔离的业务场景都要继承这个类，然后去实现相应的方法
 * 
 * @author airman33
 * create_date 2016年4月14日
 */
public abstract class MsgService
{
	public abstract OutMsg processInTextMsg(InTextMsg inTextMsg);
	
	public OutMsg processInMenuEvent(InMenuEvent inMenuEvent){
		return null;
	}
	
	public void processInFollowEvent(InFollowEvent inFollowEvent){
		
	}

	public void processInImageMsg(InImageMsg inImageMsg) {
		
	}
	
	public void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
		
	}
	
	public void processInVideoMsg(InVideoMsg inVideoMsg) {
		
	}
	
	public void processInLocationMsg(InLocationMsg inLocationMsg) {
		
	}
	
	public void processInLinkMsg(InLinkMsg inLinkMsg) {
		
	}
	
	public void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
		
	}
	
	public void processInLocationEvent(InLocationEvent inLocationEvent) {
		
	}
	
	public void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
		
	}
	
	public void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {

	}

	public void processInMassEvent(InMassEvent inMassEvent) {

	}

	public void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {

	}

	public void processInCustomEvent(InCustomEvent inCustomEvent) {

	}

	public void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {

	}

	public void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {

	}

	public void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent){

	}

	public void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent) {
		
	}
}
