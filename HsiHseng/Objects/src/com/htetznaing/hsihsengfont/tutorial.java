package com.htetznaing.hsihsengfont;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class tutorial extends Activity implements B4AActivity{
	public static tutorial mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.htetznaing.hsihsengfont", "com.htetznaing.hsihsengfont.tutorial");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (tutorial).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, true))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.htetznaing.hsihsengfont", "com.htetznaing.hsihsengfont.tutorial");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.htetznaing.hsihsengfont.tutorial", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (tutorial) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (tutorial) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return tutorial.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (tutorial) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (tutorial) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        Object[] o;
        if (permissions.length > 0)
            o = new Object[] {permissions[0], grantResults[0] == 0};
        else
            o = new Object[] {"", false};
        processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scl = null;
public anywheresoftware.b4a.objects.LabelWrapper _txt = null;
public anywheresoftware.b4a.objects.LabelWrapper _txt1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _txt2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _txt3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _txt4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _txt5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _txt6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _txt7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btni = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhi = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btntuto = null;
public anywheresoftware.b4a.keywords.constants.TypefaceWrapper _my = null;
public anywheresoftware.b4a.admobwrapper.AdViewWrapper _adview3 = null;
public com.drive.license2.australia _australia = null;
public com.htetznaing.hsihsengfont.main _main = null;
public com.htetznaing.hsihsengfont.help _help = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _about_click() throws Exception{
int _i = 0;
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 121;BA.debugLine="Sub about_Click";
 //BA.debugLineNum = 122;BA.debugLine="File.Delete(File.DirRootExternal,\"HsiHseng.apk\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"HsiHseng.apk");
 //BA.debugLineNum = 123;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 124;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 125;BA.debugLine="i = Msgbox2(\"App Name: Hsi Hseng Font\" &CRLF&CRLF";
_i = anywheresoftware.b4a.keywords.Common.Msgbox2("App Name: Hsi Hseng Font"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Version: 1.0"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Developed By: Khun Htetz Naing","About App","Rate App","","Ok",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 126;BA.debugLine="If i = DialogResponse.POSITIVE Then";
if (_i==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 127;BA.debugLine="StartActivity(p.OpenBrowser(\"https://play.google";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("https://play.google.com/store/apps/details?id=com.htetznaing.hsihsengfont")));
 };
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 21;BA.debugLine="AdView3.Initialize(\"AdView3\",\"ca-app-pub-41733485";
mostCurrent._adview3.Initialize(mostCurrent.activityBA,"AdView3","ca-app-pub-4173348573252986/1903160154");
 //BA.debugLineNum = 22;BA.debugLine="AdView3.LoadAd";
mostCurrent._adview3.LoadAd();
 //BA.debugLineNum = 24;BA.debugLine="btni.Initialize(\"btni\")";
mostCurrent._btni.Initialize(mostCurrent.activityBA,"btni");
 //BA.debugLineNum = 25;BA.debugLine="btni.Text = \"Download iFont\"";
mostCurrent._btni.setText((Object)("Download iFont"));
 //BA.debugLineNum = 26;BA.debugLine="btni.TextSize = 14";
mostCurrent._btni.setTextSize((float) (14));
 //BA.debugLineNum = 28;BA.debugLine="btnhi.Initialize(\"btnhi\")";
mostCurrent._btnhi.Initialize(mostCurrent.activityBA,"btnhi");
 //BA.debugLineNum = 29;BA.debugLine="btnhi.Text = \"Download HiFont\"";
mostCurrent._btnhi.setText((Object)("Download HiFont"));
 //BA.debugLineNum = 30;BA.debugLine="btnhi.TextSize = 14";
mostCurrent._btnhi.setTextSize((float) (14));
 //BA.debugLineNum = 32;BA.debugLine="btntuto.Initialize(\"btntuto\")";
mostCurrent._btntuto.Initialize(mostCurrent.activityBA,"btntuto");
 //BA.debugLineNum = 33;BA.debugLine="btntuto.Text = \"Go to Full Tutorial\"";
mostCurrent._btntuto.setText((Object)("Go to Full Tutorial"));
 //BA.debugLineNum = 34;BA.debugLine="btntuto.TextSize = 14";
mostCurrent._btntuto.setTextSize((float) (14));
 //BA.debugLineNum = 35;BA.debugLine="scl.Initialize(1000dip)";
mostCurrent._scl.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1000)));
 //BA.debugLineNum = 36;BA.debugLine="txt.Initialize(\"txt\")";
mostCurrent._txt.Initialize(mostCurrent.activityBA,"txt");
 //BA.debugLineNum = 37;BA.debugLine="txt.Text = \"လောလောဆယ်မှာတော့ ဒီ Font ဟာ \"&CRLF&\"F";
mostCurrent._txt.setText((Object)("လောလောဆယ်မှာတော့ ဒီ Font ဟာ "+anywheresoftware.b4a.keywords.Common.CRLF+"Font Style ပါတဲ့ဖုန်းတွေမှာပဲ"+anywheresoftware.b4a.keywords.Common.CRLF+"အသုံးပြုလို့ရနိုင်မှာဖြစ်ပါတယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"အခြားဖုန်းတွေအတွက်"+anywheresoftware.b4a.keywords.Common.CRLF+"နောက်ဗားရှင်းတွေမှာ"+anywheresoftware.b4a.keywords.Common.CRLF+"ထည့်သွင်းပေးသွားပါမယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"နောက်ပြီး Samsung ဖုန်း"+anywheresoftware.b4a.keywords.Common.CRLF+"Android 6.0အထက်တွေမှာလည်း"+anywheresoftware.b4a.keywords.Common.CRLF+"အသုံးပြုလို့အဆင်ပြေမည်မဟုတ်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"အဲ့ဒီအတွက်နည်းလမ်းရှာဖွေနေဆဲ"+anywheresoftware.b4a.keywords.Common.CRLF+"ဖြစ်ပါတယ်။Samsung 6.0"+anywheresoftware.b4a.keywords.Common.CRLF+"အောက်တွေမှာတော့"+anywheresoftware.b4a.keywords.Common.CRLF+"အားလုံးအဆင်ပြေနိုင်ပါတယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"အချို့ Android 5.0 အလုံးတွေမှာလည်း"+anywheresoftware.b4a.keywords.Common.CRLF+"Font သွားချိန်းတဲ့အခါ"+anywheresoftware.b4a.keywords.Common.CRLF+"Font not supported လို့"+anywheresoftware.b4a.keywords.Common.CRLF+"ပြလာတတ်ပါတယ်။အဲ့ဒီအချိန်မှာ"+anywheresoftware.b4a.keywords.Common.CRLF+"ဘယ်လိုလုပ်ရမလဲဆိုတာ"+anywheresoftware.b4a.keywords.Common.CRLF+"အောက်မှာရှင်းပြပေးထားပါတယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"အရမ်းလွယ်ကူလွန်းလှတဲ့အတွက်"+anywheresoftware.b4a.keywords.Common.CRLF+"ပုံနဲ့ရှင်းမပြတော့ပါ။"));
 //BA.debugLineNum = 38;BA.debugLine="txt.TextColor = Colors.Black";
mostCurrent._txt.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 39;BA.debugLine="txt.TextSize = 14";
mostCurrent._txt.setTextSize((float) (14));
 //BA.debugLineNum = 40;BA.debugLine="txt.Typeface = my";
mostCurrent._txt.setTypeface((android.graphics.Typeface)(mostCurrent._my.getObject()));
 //BA.debugLineNum = 41;BA.debugLine="txt.Gravity = Gravity.CENTER_HORIZONTAL";
mostCurrent._txt.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 43;BA.debugLine="txt1.Initialize(\"txt1\")";
mostCurrent._txt1.Initialize(mostCurrent.activityBA,"txt1");
 //BA.debugLineNum = 44;BA.debugLine="txt1.Text = \"အသုံးပြုနည်းပထမဦးဆုံး Install Font ဆ";
mostCurrent._txt1.setText((Object)("အသုံးပြုနည်းပထမဦးဆုံး Install Font ဆိုတဲ့"+anywheresoftware.b4a.keywords.Common.CRLF+"ခလုတ်ကိုနှိပ်ပြီး Install လုပ်ပေးလိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ပြီးရင် Change Font ခလုတ်ကိုနှိပ်လိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"Font သို့မဟုတ် Font Style ကိုရှာပြီးဆက်ဝင်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"အဲ့ဒီထဲမှာကိုယ် Install လုပ်လိုက်တဲ့"+anywheresoftware.b4a.keywords.Common.CRLF+"ဖောင့်ကိုရှာပြီးရွေးပေးလိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒါဆိုအဆင်ပြေသွားပါပြီ :)"+anywheresoftware.b4a.keywords.Common.CRLF+"အကယ်၍ Font ချိန်းလိုက်တဲ့အခါ"+anywheresoftware.b4a.keywords.Common.CRLF+"Font not supported လို့"+anywheresoftware.b4a.keywords.Common.CRLF+"ပြောလာရင်အောက်ပါအတိုင်း"+anywheresoftware.b4a.keywords.Common.CRLF+"ဖြေရှင်းလို့ရနိုင်ပါတယ် :)"));
 //BA.debugLineNum = 45;BA.debugLine="txt1.TextColor= Colors.Black";
mostCurrent._txt1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 46;BA.debugLine="txt1.TextSize = 14";
mostCurrent._txt1.setTextSize((float) (14));
 //BA.debugLineNum = 47;BA.debugLine="txt1.Typeface = my";
mostCurrent._txt1.setTypeface((android.graphics.Typeface)(mostCurrent._my.getObject()));
 //BA.debugLineNum = 48;BA.debugLine="txt1.Gravity = Gravity.CENTER_HORIZONTAL";
mostCurrent._txt1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 50;BA.debugLine="txt2.Initialize(\"txt2\")";
mostCurrent._txt2.Initialize(mostCurrent.activityBA,"txt2");
 //BA.debugLineNum = 51;BA.debugLine="txt2.Text = \"Font not supported ပြဿနာဖြေရှင်းနည်း";
mostCurrent._txt2.setText((Object)("Font not supported ပြဿနာဖြေရှင်းနည်းများ"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Samsung 5.0 Lollipop အချို့အလုံးတွေမှာ"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒီပြဿနာဖြစ်ပေါ်နိုင်ပါတယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"တကယ်လို့ဒီလိုဖြစ်လာရင်"+anywheresoftware.b4a.keywords.Common.CRLF+"iFont Donate သို့မဟုတ် Hifont နဲ့"+anywheresoftware.b4a.keywords.Common.CRLF+"ပြန်သွင်းပေးရင်အဆင်ပြေနိုင်ပါတယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"ပထမဦးဆုံးကျွန်တော် iFont Donate နဲ့"+anywheresoftware.b4a.keywords.Common.CRLF+"အရင်စမ်းကြည့်ဖို့အကြံပြုလိုပါတယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"iFont Donate နဲ့မရတော့မှ"+anywheresoftware.b4a.keywords.Common.CRLF+"HiFont နဲ့ထပ်လုပ်ကြည့်ပါ။"));
 //BA.debugLineNum = 52;BA.debugLine="txt2.TextColor = Colors.Black";
mostCurrent._txt2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 53;BA.debugLine="txt2.TextSize = 14";
mostCurrent._txt2.setTextSize((float) (14));
 //BA.debugLineNum = 54;BA.debugLine="txt2.Typeface = my";
mostCurrent._txt2.setTypeface((android.graphics.Typeface)(mostCurrent._my.getObject()));
 //BA.debugLineNum = 55;BA.debugLine="txt2.Gravity = Gravity.CENTER_HORIZONTAL";
mostCurrent._txt2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 57;BA.debugLine="txt3.Initialize(\"txt3\")";
mostCurrent._txt3.Initialize(mostCurrent.activityBA,"txt3");
 //BA.debugLineNum = 58;BA.debugLine="txt3.Text = \"iFont ကိုအသုံးပြုပြီး\"&CRLF&\"Font no";
mostCurrent._txt3.setText((Object)("iFont ကိုအသုံးပြုပြီး"+anywheresoftware.b4a.keywords.Common.CRLF+"Font not supported ပြဿနာဖြေရှင်းနည်း"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"ပထမဦးဆုံး iFont Donate ကို"+anywheresoftware.b4a.keywords.Common.CRLF+"မိမိတို့ဖုန်းမှာ Install လုပ်ထားပါ။ "+anywheresoftware.b4a.keywords.Common.CRLF+"မရှိသေးရင်အောက်က Download iFont ခလုတ်ကို"+anywheresoftware.b4a.keywords.Common.CRLF+"နှိပ်ပြီး သွားဒေါင်းလိုက်ပါ။"));
 //BA.debugLineNum = 59;BA.debugLine="txt3.TextColor = Colors.Black";
mostCurrent._txt3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 60;BA.debugLine="txt3.TextSize = 14";
mostCurrent._txt3.setTextSize((float) (14));
 //BA.debugLineNum = 61;BA.debugLine="txt3.Typeface = my";
mostCurrent._txt3.setTypeface((android.graphics.Typeface)(mostCurrent._my.getObject()));
 //BA.debugLineNum = 62;BA.debugLine="txt3.Gravity = Gravity.CENTER_HORIZONTAL";
mostCurrent._txt3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 64;BA.debugLine="txt4.Initialize(\"txt4\")";
mostCurrent._txt4.Initialize(mostCurrent.activityBA,"txt4");
 //BA.debugLineNum = 65;BA.debugLine="txt4.Text = \"ပြီးရင် iFont Donate ကိုဖုန်းမှာ\"&CR";
mostCurrent._txt4.setText((Object)("ပြီးရင် iFont Donate ကိုဖုန်းမှာ"+anywheresoftware.b4a.keywords.Common.CRLF+"Install လုပ်ပြီး ဖွင့်လိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ညာဘက်အပေါ်နားက My ကိုနှိပ်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ပြီးရင် My Install ဆိုတာကိုထပ်နှိပ်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒါဆိုကိုယ် Install လုပ်ထားတဲ့"+anywheresoftware.b4a.keywords.Common.CRLF+"ဖောင့်တွေအကုန်လုံးမြင်တွေ့ရပါလိမ့်မယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"အဲ့ဒီထဲကကိုယ် Font ချိန်းတုန်းက"+anywheresoftware.b4a.keywords.Common.CRLF+"Font not supported လို့"+anywheresoftware.b4a.keywords.Common.CRLF+"ပြတဲ့ဖောင့်ကိုရှာပါ။ပြီးရင်နှိပ်လိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ပြီးရင် Set ဆိုတာကိုနှိပ်ပေးလိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒီနေရာမှာတကယ်လို့သင့်ဖုန်းဟာ"+anywheresoftware.b4a.keywords.Common.CRLF+"iFont ကိုတခါမှအသုံးမပြုဖူးသေးရင်"+anywheresoftware.b4a.keywords.Common.CRLF+"အသုံးပြုနည်းကြည့်မလားလို့မေးလာပါလိမ့်မယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"Skip ကိုသာနှိပ်လိုက်ပါ။ပြီးရင်နောက်ထပ်"+anywheresoftware.b4a.keywords.Common.CRLF+"Message တစ်ခုတက်လာပါလိမ့်မယ်"+anywheresoftware.b4a.keywords.Common.CRLF+"Ok သာပေးလိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ပြီးရင် Font App လေးတစ်ခုအနေနဲ့ "+anywheresoftware.b4a.keywords.Common.CRLF+"Install လုပ်ခိုင်းပါလိမ့်မယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"Install လုပ်ပေးလိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"Install လုပ်ပြီးတာနဲ့ "+anywheresoftware.b4a.keywords.Common.CRLF+"Display Settng ထဲကိုတခါတည်း"+anywheresoftware.b4a.keywords.Common.CRLF+"Auto ရောက်သွားပါလိမ့်မယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"အဲ့ဒီထဲက Font Style သို့မဟုတ်"+anywheresoftware.b4a.keywords.Common.CRLF+"Font ကိုရှာပြီး ဝင်လိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"အဲ့ဒီထဲက ကိုယ်ခုလေးတင်"+anywheresoftware.b4a.keywords.Common.CRLF+"Install လုပ်လိုက်တဲ့ဖောင့်ကို"+anywheresoftware.b4a.keywords.Common.CRLF+"ရှာပြီးရွေးပေးလိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒါဆိုအဆင်ပြေသွားပါပြီ :)"+anywheresoftware.b4a.keywords.Common.CRLF+"အဆင်မပြေသေးရင်"+anywheresoftware.b4a.keywords.Common.CRLF+"HiFont နဲ့ထပ်လုပ်ကြတာပေါ့ :3"));
 //BA.debugLineNum = 66;BA.debugLine="txt4.TextColor = Colors.Black";
mostCurrent._txt4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 67;BA.debugLine="txt4.TextSize = 14";
mostCurrent._txt4.setTextSize((float) (14));
 //BA.debugLineNum = 68;BA.debugLine="txt4.Typeface = my";
mostCurrent._txt4.setTypeface((android.graphics.Typeface)(mostCurrent._my.getObject()));
 //BA.debugLineNum = 69;BA.debugLine="txt4.Gravity = Gravity.CENTER_HORIZONTAL";
mostCurrent._txt4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 71;BA.debugLine="txt5.Initialize(\"txt5\")";
mostCurrent._txt5.Initialize(mostCurrent.activityBA,"txt5");
 //BA.debugLineNum = 72;BA.debugLine="txt5.Text = \"HiFont အသုံးပြုပြီး\"&CRLF&\"Font not";
mostCurrent._txt5.setText((Object)("HiFont အသုံးပြုပြီး"+anywheresoftware.b4a.keywords.Common.CRLF+"Font not supported ပြဿနာဖြေရှင်းနည်း"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"ပထမဦးဆုံးမိမိတို့ဖုန်းမှာ HiFont ကို"+anywheresoftware.b4a.keywords.Common.CRLF+"Install လုပ်ထားပေးဖို့လိုအပ်ပါတယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"မရှိသေးရင် Play Store ကနေဒေါင်းလို့ရပါတယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"အောက်က Download HiFont ခလုတ်ကို"+anywheresoftware.b4a.keywords.Common.CRLF+"နှိပ်ပြီးတော့လည်းသွားဒေါင်းလို့ရပါတယ်။"));
 //BA.debugLineNum = 73;BA.debugLine="txt5.TextColor = Colors.Black";
mostCurrent._txt5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 74;BA.debugLine="txt5.TextSize = 14";
mostCurrent._txt5.setTextSize((float) (14));
 //BA.debugLineNum = 75;BA.debugLine="txt5.Typeface = my";
mostCurrent._txt5.setTypeface((android.graphics.Typeface)(mostCurrent._my.getObject()));
 //BA.debugLineNum = 76;BA.debugLine="txt5.Gravity = Gravity.CENTER_HORIZONTAL";
mostCurrent._txt5.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 78;BA.debugLine="txt6.Initialize(\"txt6\")";
mostCurrent._txt6.Initialize(mostCurrent.activityBA,"txt6");
 //BA.debugLineNum = 79;BA.debugLine="txt6.Text = \"ဒေါင်းပြီးမိမိတို့ဖုန်းမှာ Install လ";
mostCurrent._txt6.setText((Object)("ဒေါင်းပြီးမိမိတို့ဖုန်းမှာ Install လုပ်လိုက်ပါ :)"+anywheresoftware.b4a.keywords.Common.CRLF+"ပြီးရင်ဖွင့်လိုက်ပါ။ဘယ်ဘက်အပေါ်ဒေါင့်က"+anywheresoftware.b4a.keywords.Common.CRLF+"Menu ခလုတ်ကိုနှိပ်လိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒါမှမဟုတ်ဖုန်းက Menu ခေါ်တဲ့"+anywheresoftware.b4a.keywords.Common.CRLF+"ခလုတ်ကိုနှိပ်လိုက်ရင်လည်းရပါတယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"Menu ထဲက My Font ကိုနှိပ်လိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ပြီးရင် Custom Font ကိုထပ်နှိပ်ပေးပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"အဲ့ဒီမှာအခုဒီ App က"+anywheresoftware.b4a.keywords.Common.CRLF+"Font လေးတွေ့ရပါလိမ့်မယ်"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒါကတကယ်တမ်းကျတော့"+anywheresoftware.b4a.keywords.Common.CRLF+"ဖုန်း Stroage ထဲက HiFont ဆိုတဲ့"+anywheresoftware.b4a.keywords.Common.CRLF+"ဖိုဒါထဲကဖောင့်ကိုတွေဒီမှာလာပြနေတာပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ကိုယ်ချိန်းချင်တဲ့ဖောင့်တွေကို ttf ဖိုင်နဲ့"+anywheresoftware.b4a.keywords.Common.CRLF+"အဲ့ဒီ HiFont ဆိုတဲ့ဖိုဒါထဲကို"+anywheresoftware.b4a.keywords.Common.CRLF+"အရင်သွားထည့်ပေးရပါတယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒါမှဒီနေရာမှာကိုယ်ချိန်းချင်တဲ့ဖောင့်"+anywheresoftware.b4a.keywords.Common.CRLF+"ရောက်နေမှာပါ။အခုဒီ App ကဖောင့်ကိုတော့"+anywheresoftware.b4a.keywords.Common.CRLF+"App ထဲမှာတင်ကျွန်တော်တခါတည်း"+anywheresoftware.b4a.keywords.Common.CRLF+"သွားထည့်ပေးဖို့လုပ်ထားလိုက်လို့ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒါကြောင့်အဲ့ဒီဖိုဒါထဲ ဒီဖောင့်ရောက်နေတာပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"အခြားဖောင့်တွေကိုချိန်းချင်ရင်"+anywheresoftware.b4a.keywords.Common.CRLF+"ကိုယ်ချိန်းလိုတဲ့ဖောင့်ရဲ့ ttf ဖိုင်ကို"+anywheresoftware.b4a.keywords.Common.CRLF+"HiFont ဖိုဒါထဲကိုအရင်သွားထည့်ပေးရမှာပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"Custom font ထဲက ကိုယ့်ဖောင့်ကိုနှိပ်လိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ပြီးရင် Use ကိုနှိပ်လိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒီနေရာမှာ ကိုယ့်ဖုန်းကခုမှ HiFont ကို"+anywheresoftware.b4a.keywords.Common.CRLF+"အသုံးပြုတာဆိုရင်Custom ဖောင့်တွေ"+anywheresoftware.b4a.keywords.Common.CRLF+"အသုံးပြုဖို့ Custom Font plugin ကို"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒေါင်းခိုင်းပါလိမ့်မယ်။Ok သာနှိပ်ပေးလိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒါဆို Custom font plugin ကို"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒေါင်းချပေးပါလိမ့်မယ်။ပြီးသွားရင်"+anywheresoftware.b4a.keywords.Common.CRLF+"Install လုပ်ခိုင်းပါလိမ့်မယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"Install လုပ်ပေးလိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"Install လုပ်လို့ပြီးသွားရင် Done ကိုပဲ"+anywheresoftware.b4a.keywords.Common.CRLF+"နှိပ်ပေးလိုက်ပါ။ပြီးရင် Use ကိုပဲ"+anywheresoftware.b4a.keywords.Common.CRLF+"နောက်တစ်ခါထပ်နှိပ်ပေးလိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒါဆိုကိုယ့်ဖောင့်ကို Custom Font ဆိုတဲ့"+anywheresoftware.b4a.keywords.Common.CRLF+"App အမည်နဲ့ Install လုပ်ခိုင်းပါလိမ့်မယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"လုပ်ပေးလိုက်ပါ။ ပြီးသွားရင်တော့"+anywheresoftware.b4a.keywords.Common.CRLF+"Done ကိုသာနှိပ်လိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ပြီးရင် Use ကိုပဲနောက်ထပ်တစ်ခါထပ်နှိပ်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒီတခါဖောင့်ရွေးဖို့ကျလာပါပြီ :)"+anywheresoftware.b4a.keywords.Common.CRLF+"အဲ့ဒီထဲကကိုယ်ခုလေးတင် Install လုပ်"+anywheresoftware.b4a.keywords.Common.CRLF+"လိုက်တဲ့ဖောင့်ကိုရှာပြီး ရွေးပေးလိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"HiFont နဲ့လုပ်ထားတဲ့အတွက်"+anywheresoftware.b4a.keywords.Common.CRLF+"ဖောင့်အမည်နောက်ဆုံးမှာ"+anywheresoftware.b4a.keywords.Common.CRLF+"(myFont) လို့ပြထားပါလိမ့်မယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"ရွေးပြီး Yes or No မေးလာရင်"+anywheresoftware.b4a.keywords.Common.CRLF+"Yes သာပေးလိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"Done ဒါဆိုအဆင်ပြေသွားပါပြီ :)"+anywheresoftware.b4a.keywords.Common.CRLF+"အဆင်မပြေရင် မလုပ်တတ်သေးရင်"+anywheresoftware.b4a.keywords.Common.CRLF+"အောက်က Go to Full Tutorial ကို"+anywheresoftware.b4a.keywords.Common.CRLF+"နှိပ်ပြီးအသေးစိတ်ပုံနဲ့တကွ"+anywheresoftware.b4a.keywords.Common.CRLF+"ရှင်းပြပေးထားတာကိုသွားကြည့်နိုင်ပါတယ်။"));
 //BA.debugLineNum = 80;BA.debugLine="txt6.TextColor = Colors.Black";
mostCurrent._txt6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 81;BA.debugLine="txt6.TextSize = 14";
mostCurrent._txt6.setTextSize((float) (14));
 //BA.debugLineNum = 82;BA.debugLine="txt6.Typeface = my";
mostCurrent._txt6.setTypeface((android.graphics.Typeface)(mostCurrent._my.getObject()));
 //BA.debugLineNum = 83;BA.debugLine="txt6.Gravity = Gravity.CENTER_HORIZONTAL";
mostCurrent._txt6.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 85;BA.debugLine="txt7.Initialize(\"txt7\")";
mostCurrent._txt7.Initialize(mostCurrent.activityBA,"txt7");
 //BA.debugLineNum = 86;BA.debugLine="txt7.Text = \"အခြားဖုန်းတွေအတွက်လည်းကြိုးစားပေးပါဦ";
mostCurrent._txt7.setText((Object)("အခြားဖုန်းတွေအတွက်လည်းကြိုးစားပေးပါဦးမယ်။"+anywheresoftware.b4a.keywords.Common.CRLF+"Play Store မှာလည်း Rate ပေးပြီး"+anywheresoftware.b4a.keywords.Common.CRLF+"Review ပေးကြပါဦး :)"+anywheresoftware.b4a.keywords.Common.CRLF+"ဒီ App နဲ့ပတ်သက်ပြီး"+anywheresoftware.b4a.keywords.Common.CRLF+"ဝေဖန်အကြံပြုလိုတာပဲဖြစ်ဖြစ်"+anywheresoftware.b4a.keywords.Common.CRLF+"အခက်အခဲတစုံတရာရှိလာရင်လည်း"+anywheresoftware.b4a.keywords.Common.CRLF+"Feedback ကိုနှိပ်ပြီး"+anywheresoftware.b4a.keywords.Common.CRLF+"ကျွန်တော်ဆီတို့ဆီတိုက်ရိုက်အကြံပြုနိုင်ပါတယ် :)"));
 //BA.debugLineNum = 87;BA.debugLine="txt7.TextSize = 14";
mostCurrent._txt7.setTextSize((float) (14));
 //BA.debugLineNum = 88;BA.debugLine="txt7.Typeface = my";
mostCurrent._txt7.setTypeface((android.graphics.Typeface)(mostCurrent._my.getObject()));
 //BA.debugLineNum = 89;BA.debugLine="txt7.TextColor = Colors.Black";
mostCurrent._txt7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 90;BA.debugLine="txt7.Gravity = Gravity.CENTER_HORIZONTAL";
mostCurrent._txt7.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 92;BA.debugLine="Activity.AddView(scl,0%x,0%y,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._scl.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 94;BA.debugLine="scl.Panel.AddView(txt,0%x,5%y,100%x,68%y)";
mostCurrent._scl.getPanel().AddView((android.view.View)(mostCurrent._txt.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (68),mostCurrent.activityBA));
 //BA.debugLineNum = 95;BA.debugLine="scl.Panel.AddView(txt1,0%x,(txt.Top+txt.Height),1";
mostCurrent._scl.getPanel().AddView((android.view.View)(mostCurrent._txt1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),(int) ((mostCurrent._txt.getTop()+mostCurrent._txt.getHeight())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (45),mostCurrent.activityBA));
 //BA.debugLineNum = 96;BA.debugLine="scl.Panel.AddView(txt2,0%x,(txt1.Top+txt1.Height)";
mostCurrent._scl.getPanel().AddView((android.view.View)(mostCurrent._txt2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),(int) ((mostCurrent._txt1.getTop()+mostCurrent._txt1.getHeight())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (47),mostCurrent.activityBA));
 //BA.debugLineNum = 97;BA.debugLine="scl.Panel.AddView(txt3,0%x,(txt2.Top+txt2.Height)";
mostCurrent._scl.getPanel().AddView((android.view.View)(mostCurrent._txt3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),(int) ((mostCurrent._txt2.getTop()+mostCurrent._txt2.getHeight())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (23),mostCurrent.activityBA));
 //BA.debugLineNum = 98;BA.debugLine="scl.Panel.AddView(btni,20%x,(txt3.Top+txt3.Height";
mostCurrent._scl.getPanel().AddView((android.view.View)(mostCurrent._btni.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),(int) ((mostCurrent._txt3.getTop()+mostCurrent._txt3.getHeight())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 99;BA.debugLine="scl.Panel.AddView(txt4,0%x,(btni.Top+btni.Height)";
mostCurrent._scl.getPanel().AddView((android.view.View)(mostCurrent._txt4.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),(int) ((mostCurrent._btni.getTop()+mostCurrent._btni.getHeight())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (2),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (120),mostCurrent.activityBA));
 //BA.debugLineNum = 100;BA.debugLine="scl.Panel.AddView(txt5,0%x,(txt4.Top+txt4.Height)";
mostCurrent._scl.getPanel().AddView((android.view.View)(mostCurrent._txt5.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),(int) ((mostCurrent._txt4.getTop()+mostCurrent._txt4.getHeight())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (33),mostCurrent.activityBA));
 //BA.debugLineNum = 101;BA.debugLine="scl.Panel.AddView(btnhi,20%x,(txt5.Top+txt5.Heigh";
mostCurrent._scl.getPanel().AddView((android.view.View)(mostCurrent._btnhi.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),(int) ((mostCurrent._txt5.getTop()+mostCurrent._txt5.getHeight())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 102;BA.debugLine="scl.Panel.AddView(txt6,0%x,(btnhi.Top+btnhi.Heigh";
mostCurrent._scl.getPanel().AddView((android.view.View)(mostCurrent._txt6.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),(int) ((mostCurrent._btnhi.getTop()+mostCurrent._btnhi.getHeight())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (2),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (200),mostCurrent.activityBA));
 //BA.debugLineNum = 103;BA.debugLine="scl.Panel.AddView(btntuto,20%x,(txt6.Top+txt6.Hei";
mostCurrent._scl.getPanel().AddView((android.view.View)(mostCurrent._btntuto.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),(int) ((mostCurrent._txt6.getTop()+mostCurrent._txt6.getHeight())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 104;BA.debugLine="scl.Panel.AddView(txt7,0%x,(btntuto.Top+btntuto.H";
mostCurrent._scl.getPanel().AddView((android.view.View)(mostCurrent._txt7.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),(int) ((mostCurrent._btntuto.getTop()+mostCurrent._btntuto.getHeight())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 105;BA.debugLine="scl.Panel.AddView(AdView3,0%x,(txt7.Top+txt7.Heig";
mostCurrent._scl.getPanel().AddView((android.view.View)(mostCurrent._adview3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),(int) ((mostCurrent._txt7.getTop()+mostCurrent._txt7.getHeight())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),mostCurrent.activityBA));
 //BA.debugLineNum = 106;BA.debugLine="scl.Panel.Height = 620%y";
mostCurrent._scl.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (620),mostCurrent.activityBA));
 //BA.debugLineNum = 108;BA.debugLine="Activity.AddMenuItem(\"Help\",\"help\")";
mostCurrent._activity.AddMenuItem("Help","help");
 //BA.debugLineNum = 109;BA.debugLine="Activity.AddMenuItem(\"About\",\"about\")";
mostCurrent._activity.AddMenuItem("About","about");
 //BA.debugLineNum = 110;BA.debugLine="Activity.AddMenuItem(\"Feedback\",\"feedback\")";
mostCurrent._activity.AddMenuItem("Feedback","feedback");
 //BA.debugLineNum = 111;BA.debugLine="Activity.AddMenuItem(\"Rete App\",\"rate\")";
mostCurrent._activity.AddMenuItem("Rete App","rate");
 //BA.debugLineNum = 112;BA.debugLine="Activity.AddMenuItem(\"More App\",\"more\")";
mostCurrent._activity.AddMenuItem("More App","more");
 //BA.debugLineNum = 113;BA.debugLine="Activity.AddMenuItem(\"Share App\",\"share\")";
mostCurrent._activity.AddMenuItem("Share App","share");
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 184;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 186;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 180;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return "";
}
public static String  _ad2_adscreendismissed() throws Exception{
 //BA.debugLineNum = 176;BA.debugLine="Sub Ad2_AdScreenDismissed";
 //BA.debugLineNum = 177;BA.debugLine="Log(\"screen dismissed\")";
anywheresoftware.b4a.keywords.Common.Log("screen dismissed");
 //BA.debugLineNum = 178;BA.debugLine="End Sub";
return "";
}
public static String  _ad2_failedtoreceivead(String _errorcode) throws Exception{
 //BA.debugLineNum = 170;BA.debugLine="Sub Ad2_FailedToReceiveAd (ErrorCode As String)";
 //BA.debugLineNum = 171;BA.debugLine="Log(\"failed: \" & ErrorCode)";
anywheresoftware.b4a.keywords.Common.Log("failed: "+_errorcode);
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
public static String  _ad2_receivead() throws Exception{
 //BA.debugLineNum = 173;BA.debugLine="Sub Ad2_ReceiveAd";
 //BA.debugLineNum = 174;BA.debugLine="Log(\"received\")";
anywheresoftware.b4a.keywords.Common.Log("received");
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public static String  _btnhi_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 160;BA.debugLine="Sub btnhi_Click";
 //BA.debugLineNum = 161;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 162;BA.debugLine="StartActivity(p.OpenBrowser(\"http://paohityouth.b";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("http://paohityouth.blogspot.com/2016/09/HiFont-6-3-9-App.html")));
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public static String  _btni_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 155;BA.debugLine="Sub btni_Click";
 //BA.debugLineNum = 156;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 157;BA.debugLine="StartActivity(p.OpenBrowser(\"http://paohityouth.b";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("http://paohityouth.blogspot.com/2016/09/iFont-Donate-5-7-7-App.html")));
 //BA.debugLineNum = 158;BA.debugLine="End Sub";
return "";
}
public static String  _btntuto_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 165;BA.debugLine="Sub btntuto_Click";
 //BA.debugLineNum = 166;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 167;BA.debugLine="StartActivity(p.OpenBrowser(\"http://paohityouth.b";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("http://paohityouth.blogspot.com/2016/09/Fixed-Font-Not-Supported.html")));
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return "";
}
public static String  _feedback_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 131;BA.debugLine="Sub feedback_Click";
 //BA.debugLineNum = 132;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 133;BA.debugLine="StartActivity(p.OpenBrowser(\"https://www.facebook";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("https://www.facebook.com/Khun.Htetz.Naing")));
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim scl As ScrollView";
mostCurrent._scl = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim txt,txt1,txt2,txt3,txt4,txt5,txt6,txt7 As Lab";
mostCurrent._txt = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._txt1 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._txt2 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._txt3 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._txt4 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._txt5 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._txt6 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._txt7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Dim btni,btnhi,btntuto As Button";
mostCurrent._btni = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnhi = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btntuto = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private my As Typeface : my = Typeface.LoadFromAs";
mostCurrent._my = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private my As Typeface : my = Typeface.LoadFromAs";
mostCurrent._my.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("Hsi-Hseng.ttf")));
 //BA.debugLineNum = 17;BA.debugLine="Dim AdView3 As AdView";
mostCurrent._adview3 = new anywheresoftware.b4a.admobwrapper.AdViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _help_click() throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Sub help_Click";
 //BA.debugLineNum = 117;BA.debugLine="File.Delete(File.DirRootExternal,\"HsiHseng.apk\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"HsiHseng.apk");
 //BA.debugLineNum = 118;BA.debugLine="StartActivity(help)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._help.getObject()));
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return "";
}
public static String  _more_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 140;BA.debugLine="Sub more_Click";
 //BA.debugLineNum = 141;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 142;BA.debugLine="StartActivity(p.OpenBrowser(\"https://play.google";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("https://play.google.com/store/apps/developer?id=PaOh%20IT%20Youth&hl=es")));
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _rate_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 136;BA.debugLine="Sub rate_Click";
 //BA.debugLineNum = 137;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 138;BA.debugLine="StartActivity(p.OpenBrowser(\"https://play.google";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("https://play.google.com/store/apps/details?id=com.htetznaing.hsihsengfont&hl=es")));
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static String  _share_click() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _shareit = null;
 //BA.debugLineNum = 145;BA.debugLine="Sub share_Click";
 //BA.debugLineNum = 146;BA.debugLine="Dim ShareIt As Intent";
_shareit = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 147;BA.debugLine="ShareIt.Initialize (ShareIt.ACTION_SEND,\"\")";
_shareit.Initialize(_shareit.ACTION_SEND,"");
 //BA.debugLineNum = 148;BA.debugLine="ShareIt.SetType (\"text/plain\")";
_shareit.SetType("text/plain");
 //BA.debugLineNum = 149;BA.debugLine="ShareIt.PutExtra (\"android.intent.extra.TEXT\",";
_shareit.PutExtra("android.intent.extra.TEXT",(Object)("'Hsi Hseng' Beautiful Myanmar Font Style Download at Google Play Store: https://play.google.com/store/apps/details?id=com.htetznaing.hsihsengfont&hl=es"));
 //BA.debugLineNum = 150;BA.debugLine="ShareIt.PutExtra (\"android.intent.extra.SUBJEC";
_shareit.PutExtra("android.intent.extra.SUBJECT",(Object)("Get Free!!"));
 //BA.debugLineNum = 151;BA.debugLine="ShareIt.WrapAsIntentChooser(\"Share App Via...\"";
_shareit.WrapAsIntentChooser("Share App Via...");
 //BA.debugLineNum = 152;BA.debugLine="StartActivity (ShareIt)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_shareit.getObject()));
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
}
