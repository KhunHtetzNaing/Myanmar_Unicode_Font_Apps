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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.htetznaing.hsihsengfont", "com.htetznaing.hsihsengfont.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "com.htetznaing.hsihsengfont", "com.htetznaing.hsihsengfont.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.htetznaing.hsihsengfont.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main) Resume **");
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
public anywheresoftware.b4a.objects.ButtonWrapper _b = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lb = null;
public anywheresoftware.b4a.objects.LabelWrapper _slmn = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _bg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfooter = null;
public anywheresoftware.b4a.admobwrapper.AdViewWrapper _adview1 = null;
public anywheresoftware.b4a.keywords.constants.TypefaceWrapper _myanmar = null;
public com.drive.license2.australia _australia = null;
public com.htetznaing.hsihsengfont.help _help = null;
public com.htetznaing.hsihsengfont.tutorial _tutorial = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (help.mostCurrent != null);
vis = vis | (tutorial.mostCurrent != null);
vis = vis | (com.drive.license2.australia.mostCurrent != null);
return vis;}
public static String  _about_click() throws Exception{
int _i = 0;
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 85;BA.debugLine="Sub about_Click";
 //BA.debugLineNum = 86;BA.debugLine="File.Delete(File.DirRootExternal,\"HsiHseng.apk\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"HsiHseng.apk");
 //BA.debugLineNum = 87;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 88;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 89;BA.debugLine="i = Msgbox2(\"App Name: Hsi Hseng Font\" &CRLF&CRLF";
_i = anywheresoftware.b4a.keywords.Common.Msgbox2("App Name: Hsi Hseng Font"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Version: 1.0"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Developed By: Khun Htetz Naing","About App","Rate App","","Ok",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 90;BA.debugLine="If i = DialogResponse.POSITIVE Then";
if (_i==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 91;BA.debugLine="StartActivity(p.OpenBrowser(\"https://play.google";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("https://play.google.com/store/apps/details?id=com.htetznaing.hsihsengfont")));
 };
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 34;BA.debugLine="File.MakeDir(File.DirRootExternal,\"HiFont\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"HiFont");
 //BA.debugLineNum = 35;BA.debugLine="File.Copy(File.DirAssets,\"Hsi-Hseng.ttf\",File.Dir";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Hsi-Hseng.ttf",anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/HiFont","HsiHseng.ttf");
 //BA.debugLineNum = 37;BA.debugLine="AdView1.Initialize(\"Ad1\", \"ca-app-pub-41733485732";
mostCurrent._adview1.Initialize(mostCurrent.activityBA,"Ad1","ca-app-pub-4173348573252986/9504138953");
 //BA.debugLineNum = 38;BA.debugLine="Activity.AddView(AdView1, 0%x, 70%y, 100%x,30%";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._adview1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 39;BA.debugLine="AdView1.LoadAd";
mostCurrent._adview1.LoadAd();
 //BA.debugLineNum = 41;BA.debugLine="bg.Initialize(0xFF7AFF99,1)";
mostCurrent._bg.Initialize((int) (0xff7aff99),(int) (1));
 //BA.debugLineNum = 42;BA.debugLine="Activity.Title = \"Hsi Hseng Font App\"";
mostCurrent._activity.setTitle((Object)("Hsi Hseng Font App"));
 //BA.debugLineNum = 44;BA.debugLine="lb.Initialize(\"lb\")";
mostCurrent._lb.Initialize(mostCurrent.activityBA,"lb");
 //BA.debugLineNum = 45;BA.debugLine="lb.Text = \"ယူနီကုဒ်စနစ်မှန်ကန်စွာအသုံးပြုနေသော\" &";
mostCurrent._lb.setText((Object)("ယူနီကုဒ်စနစ်မှန်ကန်စွာအသုံးပြုနေသော"+anywheresoftware.b4a.keywords.Common.CRLF+"Font Style ပါရှိသောဖုန်းများအတွက်သာ..."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"အသုံးပြုနည်း"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"1. Install Hsi Hseng Font ကိုနှိပ်ပြီး"+anywheresoftware.b4a.keywords.Common.CRLF+"Hsi Hseng ဖောင့် App ကို Install လုပ်ပေးပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"2. Install ပြုလုပ်ခြင်းပြီးဆုံးလျှင်"+anywheresoftware.b4a.keywords.Common.CRLF+"Change Font ကိုနှိပ်ပြီး Font Style မှာ"+anywheresoftware.b4a.keywords.Common.CRLF+"Hsi Hseng(ifont) ကိုရွေးချယ်ပေးလိုက်ပါ။"+anywheresoftware.b4a.keywords.Common.CRLF+"အဆင်မပြေမှုများရှိခဲ့လျှင် Help ကိုနှိပ်ပြီး"+anywheresoftware.b4a.keywords.Common.CRLF+"အကူညီတောင်းခံနိုင်ပါသည်။"));
 //BA.debugLineNum = 46;BA.debugLine="lb.Gravity = Gravity.CENTER";
mostCurrent._lb.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 47;BA.debugLine="Activity.AddView(lb,2%x,2%y,96%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._lb.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (2),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (2),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (96),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 48;BA.debugLine="lb.TextSize = 12";
mostCurrent._lb.setTextSize((float) (12));
 //BA.debugLineNum = 49;BA.debugLine="lb.TextColor = Colors.Black";
mostCurrent._lb.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 50;BA.debugLine="lb.Typeface = Myanmar";
mostCurrent._lb.setTypeface((android.graphics.Typeface)(mostCurrent._myanmar.getObject()));
 //BA.debugLineNum = 52;BA.debugLine="b.Initialize(\"b\")";
mostCurrent._b.Initialize(mostCurrent.activityBA,"b");
 //BA.debugLineNum = 53;BA.debugLine="b.Text = \"Install Hsi Hseng Font\"";
mostCurrent._b.setText((Object)("Install Hsi Hseng Font"));
 //BA.debugLineNum = 54;BA.debugLine="Activity.AddView(b,10%x,45%y,80%x,10%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._b.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (45),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 56;BA.debugLine="b2.Initialize(\"b2\")";
mostCurrent._b2.Initialize(mostCurrent.activityBA,"b2");
 //BA.debugLineNum = 57;BA.debugLine="b2.Text = \"Change Font\"";
mostCurrent._b2.setText((Object)("Change Font"));
 //BA.debugLineNum = 58;BA.debugLine="Activity.AddView(b2,10%x,(b.Top+b.Height)+1%y,80%";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._b2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),(int) ((mostCurrent._b.getTop()+mostCurrent._b.getHeight())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 60;BA.debugLine="b3.Initialize(\"b3\")";
mostCurrent._b3.Initialize(mostCurrent.activityBA,"b3");
 //BA.debugLineNum = 61;BA.debugLine="b3.Text = \"Tutorial\"";
mostCurrent._b3.setText((Object)("Tutorial"));
 //BA.debugLineNum = 62;BA.debugLine="Activity.AddView(b3,10%x,(b2.Top+b2.Height)+1%y,8";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._b3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),(int) ((mostCurrent._b2.getTop()+mostCurrent._b2.getHeight())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 64;BA.debugLine="Activity.AddMenuItem(\"Help\",\"help\")";
mostCurrent._activity.AddMenuItem("Help","help");
 //BA.debugLineNum = 65;BA.debugLine="Activity.AddMenuItem(\"About\",\"about\")";
mostCurrent._activity.AddMenuItem("About","about");
 //BA.debugLineNum = 66;BA.debugLine="Activity.AddMenuItem(\"Feedback\",\"feedback\")";
mostCurrent._activity.AddMenuItem("Feedback","feedback");
 //BA.debugLineNum = 67;BA.debugLine="Activity.AddMenuItem(\"Rete App\",\"rate\")";
mostCurrent._activity.AddMenuItem("Rete App","rate");
 //BA.debugLineNum = 68;BA.debugLine="Activity.AddMenuItem(\"More App\",\"more\")";
mostCurrent._activity.AddMenuItem("More App","more");
 //BA.debugLineNum = 69;BA.debugLine="Activity.AddMenuItem(\"Share App\",\"share\")";
mostCurrent._activity.AddMenuItem("Share App","share");
 //BA.debugLineNum = 71;BA.debugLine="lblfooter.Initialize(\"lblfooter\")";
mostCurrent._lblfooter.Initialize(mostCurrent.activityBA,"lblfooter");
 //BA.debugLineNum = 72;BA.debugLine="lblfooter.Text = \"Developed By Khun Htetz Naing\"";
mostCurrent._lblfooter.setText((Object)("Developed By Khun Htetz Naing"));
 //BA.debugLineNum = 73;BA.debugLine="lblfooter.TextColor = Colors.Blue";
mostCurrent._lblfooter.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 74;BA.debugLine="lblfooter.Gravity = Gravity.CENTER";
mostCurrent._lblfooter.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 75;BA.debugLine="Activity.AddView(lblfooter,10dip, 100%y - 35dip ,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._lblfooter.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 77;BA.debugLine="slmn.Initialize(\"slmu\")";
mostCurrent._slmn.Initialize(mostCurrent.activityBA,"slmu");
 //BA.debugLineNum = 78;BA.debugLine="Activity.AddView(slmn,0%x,0%y,10%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._slmn.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 132;BA.debugLine="Sub Activity_KeyPress(KeyCode As Int) As Boolean";
 //BA.debugLineNum = 134;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 136;BA.debugLine="File.Delete(File.DirRootExternal,\"HsiHseng.";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"HsiHseng.apk");
 };
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 168;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 164;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 166;BA.debugLine="End Sub";
return "";
}
public static String  _ad1_adscreendismissed() throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Sub Ad1_AdScreenDismissed";
 //BA.debugLineNum = 129;BA.debugLine="Log(\"screen dismissed\")";
anywheresoftware.b4a.keywords.Common.Log("screen dismissed");
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _ad1_failedtoreceivead(String _errorcode) throws Exception{
 //BA.debugLineNum = 122;BA.debugLine="Sub Ad1_FailedToReceiveAd (ErrorCode As String)";
 //BA.debugLineNum = 123;BA.debugLine="Log(\"failed: \" & ErrorCode)";
anywheresoftware.b4a.keywords.Common.Log("failed: "+_errorcode);
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public static String  _ad1_receivead() throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Sub Ad1_ReceiveAd";
 //BA.debugLineNum = 126;BA.debugLine="Log(\"received\")";
anywheresoftware.b4a.keywords.Common.Log("received");
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _b_click() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 94;BA.debugLine="Sub b_Click";
 //BA.debugLineNum = 95;BA.debugLine="File.Copy(File.DirAssets,\"Hsi Hseng(iFont).apk\",F";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Hsi Hseng(iFont).apk",anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"HsiHseng.apk");
 //BA.debugLineNum = 97;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 98;BA.debugLine="i.Initialize(i.ACTION_VIEW,\"file:///\"&File.Dir";
_i.Initialize(_i.ACTION_VIEW,"file:///"+anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/HsiHseng.apk");
 //BA.debugLineNum = 99;BA.debugLine="i.SetType(\"application/vnd.android.package-archiv";
_i.SetType("application/vnd.android.package-archive");
 //BA.debugLineNum = 100;BA.debugLine="Log(File.DirRootExternal)";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal());
 //BA.debugLineNum = 101;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_i.getObject()));
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _b2_click() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _abc = null;
 //BA.debugLineNum = 104;BA.debugLine="Sub b2_Click";
 //BA.debugLineNum = 105;BA.debugLine="ToastMessageShow(\"1. Click Font Style Or Font > \"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("1. Click Font Style Or Font > "+anywheresoftware.b4a.keywords.Common.CRLF+"2. Choose Hsi Hseng(iFont)",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 106;BA.debugLine="Dim abc As Intent";
_abc = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 107;BA.debugLine="abc.Initialize(\"android.settings.DISPLAY_SETTINGS";
_abc.Initialize("android.settings.DISPLAY_SETTINGS","");
 //BA.debugLineNum = 108;BA.debugLine="StartActivity(abc)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_abc.getObject()));
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return "";
}
public static String  _b3_click() throws Exception{
 //BA.debugLineNum = 111;BA.debugLine="Sub b3_Click";
 //BA.debugLineNum = 112;BA.debugLine="ToastMessageShow(\"Please wait.. load tutorial..\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please wait.. load tutorial..",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 113;BA.debugLine="StartActivity(Tutorial)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._tutorial.getObject()));
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _feedback_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 141;BA.debugLine="Sub feedback_Click";
 //BA.debugLineNum = 142;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 143;BA.debugLine="StartActivity(p.OpenBrowser(\"https://www.facebook";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("https://www.facebook.com/Khun.Htetz.Naing")));
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 24;BA.debugLine="Dim b,b2,b3 As Button";
mostCurrent._b = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b2 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim lb,slmn As Label";
mostCurrent._lb = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._slmn = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim bg As ColorDrawable";
mostCurrent._bg = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 27;BA.debugLine="Dim lblfooter As Label";
mostCurrent._lblfooter = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim AdView1 As AdView";
mostCurrent._adview1 = new anywheresoftware.b4a.admobwrapper.AdViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private Myanmar As Typeface : Myanmar = Typeface.";
mostCurrent._myanmar = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private Myanmar As Typeface : Myanmar = Typeface.";
mostCurrent._myanmar.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("Hsi-Hseng.ttf")));
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _help_click() throws Exception{
 //BA.debugLineNum = 81;BA.debugLine="Sub help_Click";
 //BA.debugLineNum = 82;BA.debugLine="File.Delete(File.DirRootExternal,\"HsiHseng.apk\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"HsiHseng.apk");
 //BA.debugLineNum = 83;BA.debugLine="StartActivity(help)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._help.getObject()));
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
return "";
}
public static String  _lblfooter_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p1 = null;
 //BA.debugLineNum = 116;BA.debugLine="Sub lblfooter_CLick";
 //BA.debugLineNum = 117;BA.debugLine="File.Delete(File.DirRootExternal,\"HsiHseng.apk\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"HsiHseng.apk");
 //BA.debugLineNum = 118;BA.debugLine="Dim p1 As PhoneIntents";
_p1 = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 119;BA.debugLine="StartActivity(p1.OpenBrowser(\"http://www.facebook";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p1.OpenBrowser("http://www.facebook.com/Khun.Htetz.Naing")));
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static String  _more_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 150;BA.debugLine="Sub more_Click";
 //BA.debugLineNum = 151;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 152;BA.debugLine="StartActivity(p.OpenBrowser(\"https://play.google";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("https://play.google.com/store/apps/developer?id=PaOh%20IT%20Youth&hl=es")));
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        com.drive.license2.australia._process_globals();
main._process_globals();
help._process_globals();
tutorial._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _rate_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 146;BA.debugLine="Sub rate_Click";
 //BA.debugLineNum = 147;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 148;BA.debugLine="StartActivity(p.OpenBrowser(\"https://play.google";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("https://play.google.com/store/apps/details?id=com.htetznaing.hsihsengfont&hl=es")));
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static String  _share_click() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _shareit = null;
 //BA.debugLineNum = 154;BA.debugLine="Sub share_Click";
 //BA.debugLineNum = 155;BA.debugLine="Dim ShareIt As Intent";
_shareit = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 156;BA.debugLine="ShareIt.Initialize (ShareIt.ACTION_SEND,\"\")";
_shareit.Initialize(_shareit.ACTION_SEND,"");
 //BA.debugLineNum = 157;BA.debugLine="ShareIt.SetType (\"text/plain\")";
_shareit.SetType("text/plain");
 //BA.debugLineNum = 158;BA.debugLine="ShareIt.PutExtra (\"android.intent.extra.TEXT\",";
_shareit.PutExtra("android.intent.extra.TEXT",(Object)("'Hsi Hseng' Beautiful Myanmar Font Style Download at Google Play Store: https://play.google.com/store/apps/details?id=com.htetznaing.hsihsengfont&hl=es"));
 //BA.debugLineNum = 159;BA.debugLine="ShareIt.PutExtra (\"android.intent.extra.SUBJEC";
_shareit.PutExtra("android.intent.extra.SUBJECT",(Object)("Get Free!!"));
 //BA.debugLineNum = 160;BA.debugLine="ShareIt.WrapAsIntentChooser(\"Share App Via...\"";
_shareit.WrapAsIntentChooser("Share App Via...");
 //BA.debugLineNum = 161;BA.debugLine="StartActivity (ShareIt)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_shareit.getObject()));
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
}
