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

public class help extends Activity implements B4AActivity{
	public static help mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.htetznaing.hsihsengfont", "com.htetznaing.hsihsengfont.help");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (help).");
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
		activityBA = new BA(this, layout, processBA, "com.htetznaing.hsihsengfont", "com.htetznaing.hsihsengfont.help");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.htetznaing.hsihsengfont.help", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (help) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (help) Resume **");
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
		return help.class;
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
        BA.LogInfo("** Activity (help) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (help) Resume **");
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
public anywheresoftware.b4a.objects.StringUtils _su = null;
public anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lstone = null;
public anywheresoftware.b4a.admobwrapper.AdViewWrapper _adview2 = null;
public anywheresoftware.b4a.keywords.constants.TypefaceWrapper _mm = null;
public com.drive.license2.australia _australia = null;
public com.htetznaing.hsihsengfont.main _main = null;
public com.htetznaing.hsihsengfont.tutorial _tutorial = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _imvlogo = null;
anywheresoftware.b4a.objects.LabelWrapper _lblname = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _bg = null;
anywheresoftware.b4a.objects.LabelWrapper _lblcredit = null;
 //BA.debugLineNum = 22;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 25;BA.debugLine="Activity.Title = \"About\"";
mostCurrent._activity.setTitle((Object)("About"));
 //BA.debugLineNum = 26;BA.debugLine="Activity.Color = Colors.RGB (235,235,235)";
mostCurrent._activity.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (235),(int) (235),(int) (235)));
 //BA.debugLineNum = 28;BA.debugLine="AdView2.Initialize(\"Ad2\", \"ca-app-pub-4173348573";
mostCurrent._adview2.Initialize(mostCurrent.activityBA,"Ad2","ca-app-pub-4173348573252986/3457605356");
 //BA.debugLineNum = 29;BA.debugLine="Activity.AddView(AdView2, 0%x, 70%y, 100%x,30";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._adview2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 30;BA.debugLine="AdView2.LoadAd";
mostCurrent._adview2.LoadAd();
 //BA.debugLineNum = 32;BA.debugLine="Dim imvLogo As ImageView";
_imvlogo = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="imvLogo.Initialize (\"\")";
_imvlogo.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 34;BA.debugLine="imvLogo.Bitmap = LoadBitmap(File.DirAssets , \"ico";
_imvlogo.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"icon.png").getObject()));
 //BA.debugLineNum = 35;BA.debugLine="imvLogo.Gravity = Gravity.FILL";
_imvlogo.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 36;BA.debugLine="Activity.AddView ( imvLogo , 50%x - 50dip  , 20di";
mostCurrent._activity.AddView((android.view.View)(_imvlogo.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 38;BA.debugLine="Dim lblName As  Label";
_lblname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim bg As ColorDrawable";
_bg = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 40;BA.debugLine="bg.Initialize (Colors.DarkGray , 10dip)";
_bg.Initialize(anywheresoftware.b4a.keywords.Common.Colors.DarkGray,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 41;BA.debugLine="lblName.Initialize (\"\")";
_lblname.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 42;BA.debugLine="lblName.Background = bg";
_lblname.setBackground((android.graphics.drawable.Drawable)(_bg.getObject()));
 //BA.debugLineNum = 43;BA.debugLine="lblName.Gravity = Gravity.CENTER";
_lblname.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 44;BA.debugLine="lblName.Text = \"အောက်ပါနေရာများတွင်\" &CRLF& \"အကူအ";
_lblname.setText((Object)("အောက်ပါနေရာများတွင်"+anywheresoftware.b4a.keywords.Common.CRLF+"အကူအညီတောင်းယူနိုင်ပါသည်။"));
 //BA.debugLineNum = 45;BA.debugLine="lblName.Typeface = mm";
_lblname.setTypeface((android.graphics.Typeface)(mostCurrent._mm.getObject()));
 //BA.debugLineNum = 46;BA.debugLine="lblName.TextSize = 13";
_lblname.setTextSize((float) (13));
 //BA.debugLineNum = 47;BA.debugLine="lblName.TextColor = Colors.White";
_lblname.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 48;BA.debugLine="Activity.AddView (lblName , 10%x, 130dip , 80%x ,";
mostCurrent._activity.AddView((android.view.View)(_lblname.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 49;BA.debugLine="lblName.Height = su.MeasureMultilineTextHeight (l";
_lblname.setHeight((int) (mostCurrent._su.MeasureMultilineTextHeight((android.widget.TextView)(_lblname.getObject()),_lblname.getText())+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 52;BA.debugLine="lstOne.Initialize (\"lstOnes\")";
mostCurrent._lstone.Initialize(mostCurrent.activityBA,"lstOnes");
 //BA.debugLineNum = 53;BA.debugLine="lstOne.TwoLinesAndBitmap .Label .TextColor = Colo";
mostCurrent._lstone.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 54;BA.debugLine="lstOne.AddTwoLinesAndBitmap(\"Myanmar Unicode Area";
mostCurrent._lstone.AddTwoLinesAndBitmap("Myanmar Unicode Area","www.fb.com/groups/mmUnicode",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mua.png").getObject()));
 //BA.debugLineNum = 55;BA.debugLine="lstOne.AddTwoLinesAndBitmap(\"Myanmar PaOh Unicode";
mostCurrent._lstone.AddTwoLinesAndBitmap("Myanmar PaOh Unicode Area","www.fb.com/groups/PaOhUnicode",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mpua.jpeg").getObject()));
 //BA.debugLineNum = 56;BA.debugLine="lstOne.AddTwoLinesAndBitmap(\"ပအိုဝ်း IT လူငယ်များ";
mostCurrent._lstone.AddTwoLinesAndBitmap("ပအိုဝ်း IT လူငယ်များ","www.fb.com/PaOhITYouth",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"paohityouth.jpg").getObject()));
 //BA.debugLineNum = 57;BA.debugLine="Activity.AddView( lstOne,3%x, (lblName.Top+lblNam";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._lstone.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (3),mostCurrent.activityBA),(int) ((_lblname.getTop()+_lblname.getHeight())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (94),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),mostCurrent.activityBA));
 //BA.debugLineNum = 59;BA.debugLine="Dim lblCredit As Label";
_lblcredit = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="lblCredit.Initialize (\"lblCredit\")";
_lblcredit.Initialize(mostCurrent.activityBA,"lblCredit");
 //BA.debugLineNum = 61;BA.debugLine="lblCredit.TextColor = Colors.RGB (48,154,6)";
_lblcredit.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (48),(int) (154),(int) (6)));
 //BA.debugLineNum = 62;BA.debugLine="lblCredit.TextSize = 15";
_lblcredit.setTextSize((float) (15));
 //BA.debugLineNum = 63;BA.debugLine="lblCredit.Gravity = Gravity.CENTER";
_lblcredit.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 64;BA.debugLine="lblCredit.Text = \"Special Thanks Khon Soe Zaw Thu";
_lblcredit.setText((Object)("Special Thanks Khon Soe Zaw Thu"));
 //BA.debugLineNum = 65;BA.debugLine="Activity.AddView(lblCredit, 10dip,(lstOne.Top+lst";
mostCurrent._activity.AddView((android.view.View)(_lblcredit.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) ((mostCurrent._lstone.getTop()+mostCurrent._lstone.getHeight())+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 66;BA.debugLine="lblCredit.Height = su.MeasureMultilineTextHeight";
_lblcredit.setHeight(mostCurrent._su.MeasureMultilineTextHeight((android.widget.TextView)(_lblcredit.getObject()),_lblcredit.getText()));
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 83;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static String  _ad2_adscreendismissed() throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Sub Ad2_AdScreenDismissed";
 //BA.debugLineNum = 77;BA.debugLine="Log(\"screen dismissed\")";
anywheresoftware.b4a.keywords.Common.Log("screen dismissed");
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
public static String  _ad2_failedtoreceivead(String _errorcode) throws Exception{
 //BA.debugLineNum = 70;BA.debugLine="Sub Ad2_FailedToReceiveAd (ErrorCode As String)";
 //BA.debugLineNum = 71;BA.debugLine="Log(\"failed: \" & ErrorCode)";
anywheresoftware.b4a.keywords.Common.Log("failed: "+_errorcode);
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _ad2_receivead() throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Sub Ad2_ReceiveAd";
 //BA.debugLineNum = 74;BA.debugLine="Log(\"received\")";
anywheresoftware.b4a.keywords.Common.Log("received");
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim su As StringUtils";
mostCurrent._su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 16;BA.debugLine="Dim p As PhoneIntents";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 17;BA.debugLine="Dim lstOne As ListView";
mostCurrent._lstone = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim AdView2 As AdView";
mostCurrent._adview2 = new anywheresoftware.b4a.admobwrapper.AdViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private mm As Typeface : mm = Typeface.LoadFromAs";
mostCurrent._mm = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private mm As Typeface : mm = Typeface.LoadFromAs";
mostCurrent._mm.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("Hsi-Hseng.ttf")));
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _lblcredit_click() throws Exception{
 //BA.debugLineNum = 80;BA.debugLine="Sub lblCredit_Click";
 //BA.debugLineNum = 81;BA.debugLine="StartActivity(p.OpenBrowser (\"https://www.faceboo";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._p.OpenBrowser("https://www.facebook.com/khonsoezawthu")));
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
public static String  _lstones_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Sub lstOnes_ItemClick (Position As Int, Value As O";
 //BA.debugLineNum = 92;BA.debugLine="If Value = \"Myanmar Unicode Area\" Then";
if ((_value).equals((Object)("Myanmar Unicode Area"))) { 
 //BA.debugLineNum = 93;BA.debugLine="Dim p As PhoneIntents";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 94;BA.debugLine="StartActivity(p.OpenBrowser(\"https://www.faceboo";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._p.OpenBrowser("https://www.facebook.com/groups/mmUnicode")));
 };
 //BA.debugLineNum = 96;BA.debugLine="If Value = \"Myanmar PaOh Unicode Area\" Then";
if ((_value).equals((Object)("Myanmar PaOh Unicode Area"))) { 
 //BA.debugLineNum = 97;BA.debugLine="Dim p As PhoneIntents";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 98;BA.debugLine="StartActivity(p.OpenBrowser(\"https://www.faceboo";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._p.OpenBrowser("https://www.facebook.com/groups/PaOhUnicode")));
 };
 //BA.debugLineNum = 100;BA.debugLine="If Value = \"ပအိုဝ်း IT လူငယ်များ\" Then";
if ((_value).equals((Object)("ပအိုဝ်း IT လူငယ်များ"))) { 
 //BA.debugLineNum = 101;BA.debugLine="Dim p As PhoneIntents";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 102;BA.debugLine="StartActivity(p.OpenBrowser(\"https://www.faceboo";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._p.OpenBrowser("https://www.facebook.com/PaOhITYouth")));
 };
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
