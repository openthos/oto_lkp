package com.autoTestUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class otoDisplayRun extends UiAutomatorTestCase{
	
	UiDevice mydevice;
	otoDisplayRun(UiDevice device){
		mydevice = device;
	}

	final int CLICK_ID = 1111;
	final int CLICK_TEXT = 2222;
	
	public boolean ClickById(String id){
		return ClickByInfo(CLICK_ID,id);
	}
	
	public boolean ClickByText(String text){
		return ClickByInfo(CLICK_TEXT,text);
	}
	
	private boolean ClickByInfo(int CLICK,String str){
		UiSelector uiselector = null;
		switch(CLICK)
		{
		case CLICK_ID:
			uiselector = new UiSelector().resourceId(str);
			break;
		case CLICK_TEXT:	
			uiselector = new UiSelector().text(str);
			break;
		default:
			return false;
		}
		UiObject myobject = new UiObject(uiselector);
		int i = 0;
		while(!myobject.exists() && i < 5){
			SolveProblems();
			sleep(2000);
			if(i == 4){
				TakeScreen(str.substring(str.indexOf('/')+1)+"----not find");
				System.out.println("----------[failed]:" + str + " not find !!!测试未通过");
				return false;
			}
			i++;
		}
		try {
			myobject.click();
		} catch (UiObjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private void SolveProblems(){
		sleep(2000);
		boolean dumpFirstStart = new UiObject(new UiSelector().text("是否创建桌面快捷方式")).exists();
		if (dumpFirstStart == true) {
			UiObject sureButton = new UiObject(new UiSelector().text("确定"));
			try {
				sureButton.click();
			} catch (UiObjectNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boolean dumpFirstStart1 = new UiObject(new UiSelector().text("检测到新版本")).exists();
		if (dumpFirstStart1 == true) {
			UiObject sureButton = new UiObject(new UiSelector().text("以后再说"));
			try {
				sureButton.click();
			} catch (UiObjectNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void TakeScreen(String descript){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		Date time = new Date();
		String timestr = format.format(time);
		File files = new File("/storage/sdcard0/Pictures/"+timestr+"_"+descript+".jpg");
		mydevice.takeScreenshot(files);
	}
	
	public static int execCmdNoSave(String cmd) throws InterruptedException {
		int ret = 0;
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			//错误输出流,将标准错误转为标准输出（防止子进程运行阻塞）
			InputStream errorInput = p.getErrorStream();
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(
					errorInput));
			String eline = null;
			while ((eline = errorReader.readLine()) != null) {
				System.out.println(eline);
			}
			ret = p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
}
