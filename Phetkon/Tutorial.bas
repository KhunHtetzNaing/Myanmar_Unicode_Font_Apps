Type=Activity
Version=6
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	Dim scl As ScrollView
	Dim txt,txt1,txt2,txt3,txt4,txt5,txt6,txt7 As Label
	Dim btni,btnhi,btntuto As Button
	Private my As Typeface : my = Typeface.LoadFromAssets("Phetkon.ttf")
	Dim AdView3 As AdView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	AdView3.Initialize("AdView3","ca-app-pub-4173348573252986/3702794153")
	AdView3.LoadAd
	
	btni.Initialize("btni")
	btni.Text = "Download iFont"
	btni.TextSize = 14
	
	btnhi.Initialize("btnhi")
	btnhi.Text = "Download HiFont"
	btnhi.TextSize = 14
	
	btntuto.Initialize("btntuto")
	btntuto.Text = "Go to Full Tutorial"
	btntuto.TextSize = 14
	scl.Initialize(1000dip)
	txt.Initialize("txt")
	txt.Text = "လောလောဆယ်မှာတော့ ဒီ Font ဟာ "&CRLF&"Font Style ပါတဲ့ဖုန်းတွေမှာပဲ"&CRLF&"အသုံးပြုလို့ရနိုင်မှာဖြစ်ပါတယ်။"&CRLF&"အခြားဖုန်းတွေအတွက်"&CRLF&"နောက်ဗားရှင်းတွေမှာ"&CRLF&"ထည့်သွင်းပေးသွားပါမယ်။"&CRLF&"နောက်ပြီး Samsung ဖုန်း"&CRLF&"Android 6.0အထက်တွေမှာလည်း"&CRLF&"အသုံးပြုလို့အဆင်ပြေမည်မဟုတ်ပါ။"&CRLF&"အဲ့ဒီအတွက်နည်းလမ်းရှာဖွေနေဆဲ"&CRLF&"ဖြစ်ပါတယ်။Samsung 6.0"&CRLF&"အောက်တွေမှာတော့"&CRLF&"အားလုံးအဆင်ပြေနိုင်ပါတယ်။"&CRLF&"အချို့ Android 5.0 အလုံးတွေမှာလည်း"&CRLF&"Font သွားချိန်းတဲ့အခါ"&CRLF&"Font not supported လို့"&CRLF&"ပြလာတတ်ပါတယ်။အဲ့ဒီအချိန်မှာ"&CRLF&"ဘယ်လိုလုပ်ရမလဲဆိုတာ"&CRLF&"အောက်မှာရှင်းပြပေးထားပါတယ်။"&CRLF&"အရမ်းလွယ်ကူလွန်းလှတဲ့အတွက်"&CRLF&"ပုံနဲ့ရှင်းမပြတော့ပါ။"
	txt.TextColor = Colors.Black
	txt.TextSize = 14
	txt.Typeface = my
	txt.Gravity = Gravity.CENTER_HORIZONTAL
	
	txt1.Initialize("txt1")
	txt1.Text = "အသုံးပြုနည်းပထမဦးဆုံး Install Font ဆိုတဲ့"&CRLF&"ခလုတ်ကိုနှိပ်ပြီး Install လုပ်ပေးလိုက်ပါ။"&CRLF&"ပြီးရင် Change Font ခလုတ်ကိုနှိပ်လိုက်ပါ။"&CRLF&"Font သို့မဟုတ် Font Style ကိုရှာပြီးဆက်ဝင်ပါ။"&CRLF&"အဲ့ဒီထဲမှာကိုယ် Install လုပ်လိုက်တဲ့"&CRLF&"ဖောင့်ကိုရှာပြီးရွေးပေးလိုက်ပါ။"&CRLF&"ဒါဆိုအဆင်ပြေသွားပါပြီ :)"&CRLF&"အကယ်၍ Font ချိန်းလိုက်တဲ့အခါ"&CRLF&"Font not supported လို့"&CRLF&"ပြောလာရင်အောက်ပါအတိုင်း"&CRLF&"ဖြေရှင်းလို့ရနိုင်ပါတယ် :)"
	txt1.TextColor= Colors.Black
	txt1.TextSize = 14
	txt1.Typeface = my
	txt1.Gravity = Gravity.CENTER_HORIZONTAL
	
	txt2.Initialize("txt2")
	txt2.Text = "Font not supported ပြဿနာဖြေရှင်းနည်းများ"&CRLF&CRLF&"Samsung 5.0 Lollipop အချို့အလုံးတွေမှာ"&CRLF&"ဒီပြဿနာဖြစ်ပေါ်နိုင်ပါတယ်။"&CRLF&"တကယ်လို့ဒီလိုဖြစ်လာရင်"&CRLF&"iFont Donate သို့မဟုတ် Hifont နဲ့"&CRLF&"ပြန်သွင်းပေးရင်အဆင်ပြေနိုင်ပါတယ်။"&CRLF&"ပထမဦးဆုံးကျွန်တော် iFont Donate နဲ့"&CRLF&"အရင်စမ်းကြည့်ဖို့အကြံပြုလိုပါတယ်။"&CRLF&"iFont Donate နဲ့မရတော့မှ"&CRLF&"HiFont နဲ့ထပ်လုပ်ကြည့်ပါ။"
	txt2.TextColor = Colors.Black
	txt2.TextSize = 14
	txt2.Typeface = my
	txt2.Gravity = Gravity.CENTER_HORIZONTAL
	
	txt3.Initialize("txt3")
	txt3.Text = "iFont ကိုအသုံးပြုပြီး"&CRLF&"Font not supported ပြဿနာဖြေရှင်းနည်း"&CRLF&CRLF&"ပထမဦးဆုံး iFont Donate ကို"&CRLF&"မိမိတို့ဖုန်းမှာ Install လုပ်ထားပါ။ "&CRLF&"မရှိသေးရင်အောက်က Download iFont ခလုတ်ကို"&CRLF&"နှိပ်ပြီး သွားဒေါင်းလိုက်ပါ။"
	txt3.TextColor = Colors.Black
	txt3.TextSize = 14
	txt3.Typeface = my
	txt3.Gravity = Gravity.CENTER_HORIZONTAL
	
	txt4.Initialize("txt4")
	txt4.Text = "ပြီးရင် iFont Donate ကိုဖုန်းမှာ"&CRLF&"Install လုပ်ပြီး ဖွင့်လိုက်ပါ။"&CRLF&"ညာဘက်အပေါ်နားက My ကိုနှိပ်ပါ။"&CRLF&"ပြီးရင် My Install ဆိုတာကိုထပ်နှိပ်ပါ။"&CRLF&"ဒါဆိုကိုယ် Install လုပ်ထားတဲ့"&CRLF&"ဖောင့်တွေအကုန်လုံးမြင်တွေ့ရပါလိမ့်မယ်။"&CRLF&"အဲ့ဒီထဲကကိုယ် Font ချိန်းတုန်းက"&CRLF&"Font not supported လို့"&CRLF&"ပြတဲ့ဖောင့်ကိုရှာပါ။ပြီးရင်နှိပ်လိုက်ပါ။"&CRLF&"ပြီးရင် Set ဆိုတာကိုနှိပ်ပေးလိုက်ပါ။"&CRLF&"ဒီနေရာမှာတကယ်လို့သင့်ဖုန်းဟာ"&CRLF&"iFont ကိုတခါမှအသုံးမပြုဖူးသေးရင်"&CRLF&"အသုံးပြုနည်းကြည့်မလားလို့မေးလာပါလိမ့်မယ်။"&CRLF&"Skip ကိုသာနှိပ်လိုက်ပါ။ပြီးရင်နောက်ထပ်"&CRLF&"Message တစ်ခုတက်လာပါလိမ့်မယ်"&CRLF&"Ok သာပေးလိုက်ပါ။"&CRLF&"ပြီးရင် Font App လေးတစ်ခုအနေနဲ့ "&CRLF&"Install လုပ်ခိုင်းပါလိမ့်မယ်။"&CRLF&"Install လုပ်ပေးလိုက်ပါ။"&CRLF&"Install လုပ်ပြီးတာနဲ့ "&CRLF&"Display Settng ထဲကိုတခါတည်း"&CRLF&"Auto ရောက်သွားပါလိမ့်မယ်။"&CRLF&"အဲ့ဒီထဲက Font Style သို့မဟုတ်"&CRLF&"Font ကိုရှာပြီး ဝင်လိုက်ပါ။"&CRLF&"အဲ့ဒီထဲက ကိုယ်ခုလေးတင်"&CRLF&"Install လုပ်လိုက်တဲ့ဖောင့်ကို"&CRLF&"ရှာပြီးရွေးပေးလိုက်ပါ။"&CRLF&"ဒါဆိုအဆင်ပြေသွားပါပြီ :)"&CRLF&"အဆင်မပြေသေးရင်"&CRLF&"HiFont နဲ့ထပ်လုပ်ကြတာပေါ့ :3"
	txt4.TextColor = Colors.Black
	txt4.TextSize = 14
	txt4.Typeface = my
	txt4.Gravity = Gravity.CENTER_HORIZONTAL
	
	txt5.Initialize("txt5")
	txt5.Text = "HiFont အသုံးပြုပြီး"&CRLF&"Font not supported ပြဿနာဖြေရှင်းနည်း"&CRLF&CRLF&"ပထမဦးဆုံးမိမိတို့ဖုန်းမှာ HiFont ကို"&CRLF&"Install လုပ်ထားပေးဖို့လိုအပ်ပါတယ်။"&CRLF&"မရှိသေးရင် Play Store ကနေဒေါင်းလို့ရပါတယ်။"&CRLF&"အောက်က Download HiFont ခလုတ်ကို"&CRLF&"နှိပ်ပြီးတော့လည်းသွားဒေါင်းလို့ရပါတယ်။"
	txt5.TextColor = Colors.Black
	txt5.TextSize = 14
	txt5.Typeface = my
	txt5.Gravity = Gravity.CENTER_HORIZONTAL
	
	txt6.Initialize("txt6")
	txt6.Text = "ဒေါင်းပြီးမိမိတို့ဖုန်းမှာ Install လုပ်လိုက်ပါ :)"&CRLF&"ပြီးရင်ဖွင့်လိုက်ပါ။ဘယ်ဘက်အပေါ်ဒေါင့်က"&CRLF&"Menu ခလုတ်ကိုနှိပ်လိုက်ပါ။"&CRLF&"ဒါမှမဟုတ်ဖုန်းက Menu ခေါ်တဲ့"&CRLF&"ခလုတ်ကိုနှိပ်လိုက်ရင်လည်းရပါတယ်။"&CRLF&"Menu ထဲက My Font ကိုနှိပ်လိုက်ပါ။"&CRLF&"ပြီးရင် Custom Font ကိုထပ်နှိပ်ပေးပါ။"&CRLF&"အဲ့ဒီမှာအခုဒီ App က"&CRLF&"Font လေးတွေ့ရပါလိမ့်မယ်"&CRLF&"ဒါကတကယ်တမ်းကျတော့"&CRLF&"ဖုန်း Stroage ထဲက HiFont ဆိုတဲ့"&CRLF&"ဖိုဒါထဲကဖောင့်ကိုတွေဒီမှာလာပြနေတာပါ။"&CRLF&"ကိုယ်ချိန်းချင်တဲ့ဖောင့်တွေကို ttf ဖိုင်နဲ့"&CRLF&"အဲ့ဒီ HiFont ဆိုတဲ့ဖိုဒါထဲကို"&CRLF&"အရင်သွားထည့်ပေးရပါတယ်။"&CRLF&"ဒါမှဒီနေရာမှာကိုယ်ချိန်းချင်တဲ့ဖောင့်"&CRLF&"ရောက်နေမှာပါ။အခုဒီ App ကဖောင့်ကိုတော့"&CRLF&"App ထဲမှာတင်ကျွန်တော်တခါတည်း"&CRLF&"သွားထည့်ပေးဖို့လုပ်ထားလိုက်လို့ပါ။"&CRLF&"ဒါကြောင့်အဲ့ဒီဖိုဒါထဲ ဒီဖောင့်ရောက်နေတာပါ။"&CRLF&"အခြားဖောင့်တွေကိုချိန်းချင်ရင်"&CRLF&"ကိုယ်ချိန်းလိုတဲ့ဖောင့်ရဲ့ ttf ဖိုင်ကို"&CRLF&"HiFont ဖိုဒါထဲကိုအရင်သွားထည့်ပေးရမှာပါ။"&CRLF&"Custom font ထဲက ကိုယ့်ဖောင့်ကိုနှိပ်လိုက်ပါ။"&CRLF&"ပြီးရင် Use ကိုနှိပ်လိုက်ပါ။"&CRLF&"ဒီနေရာမှာ ကိုယ့်ဖုန်းကခုမှ HiFont ကို"&CRLF&"အသုံးပြုတာဆိုရင်Custom ဖောင့်တွေ"&CRLF&"အသုံးပြုဖို့ Custom Font plugin ကို"&CRLF&"ဒေါင်းခိုင်းပါလိမ့်မယ်။Ok သာနှိပ်ပေးလိုက်ပါ။"&CRLF&"ဒါဆို Custom font plugin ကို"&CRLF&"ဒေါင်းချပေးပါလိမ့်မယ်။ပြီးသွားရင်"&CRLF&"Install လုပ်ခိုင်းပါလိမ့်မယ်။"&CRLF&"Install လုပ်ပေးလိုက်ပါ။"&CRLF&"Install လုပ်လို့ပြီးသွားရင် Done ကိုပဲ"&CRLF&"နှိပ်ပေးလိုက်ပါ။ပြီးရင် Use ကိုပဲ"&CRLF&"နောက်တစ်ခါထပ်နှိပ်ပေးလိုက်ပါ။"&CRLF&"ဒါဆိုကိုယ့်ဖောင့်ကို Custom Font ဆိုတဲ့"&CRLF&"App အမည်နဲ့ Install လုပ်ခိုင်းပါလိမ့်မယ်။"&CRLF&"လုပ်ပေးလိုက်ပါ။ ပြီးသွားရင်တော့"&CRLF&"Done ကိုသာနှိပ်လိုက်ပါ။"&CRLF&"ပြီးရင် Use ကိုပဲနောက်ထပ်တစ်ခါထပ်နှိပ်ပါ။"&CRLF&"ဒီတခါဖောင့်ရွေးဖို့ကျလာပါပြီ :)"&CRLF&"အဲ့ဒီထဲကကိုယ်ခုလေးတင် Install လုပ်"&CRLF&"လိုက်တဲ့ဖောင့်ကိုရှာပြီး ရွေးပေးလိုက်ပါ။"&CRLF&"HiFont နဲ့လုပ်ထားတဲ့အတွက်"&CRLF&"ဖောင့်အမည်နောက်ဆုံးမှာ"&CRLF&"(myFont) လို့ပြထားပါလိမ့်မယ်။"&CRLF&"ရွေးပြီး Yes or No မေးလာရင်"&CRLF&"Yes သာပေးလိုက်ပါ။"&CRLF&"Done ဒါဆိုအဆင်ပြေသွားပါပြီ :)"&CRLF&"အဆင်မပြေရင် မလုပ်တတ်သေးရင်"&CRLF&"အောက်က Go to Full Tutorial ကို"&CRLF&"နှိပ်ပြီးအသေးစိတ်ပုံနဲ့တကွ" &CRLF&"ရှင်းပြပေးထားတာကိုသွားကြည့်နိုင်ပါတယ်။"
	txt6.TextColor = Colors.Black
	txt6.TextSize = 14
	txt6.Typeface = my
	txt6.Gravity = Gravity.CENTER_HORIZONTAL
	
	txt7.Initialize("txt7")
	txt7.Text = "အခြားဖုန်းတွေအတွက်လည်းကြိုးစားပေးပါဦးမယ်။"&CRLF&"Play Store မှာလည်း Rate ပေးပြီး"&CRLF&"Review ပေးကြပါဦး :)"&CRLF&"ဒီ App နဲ့ပတ်သက်ပြီး"&CRLF&"ဝေဖန်အကြံပြုလိုတာပဲဖြစ်ဖြစ်"&CRLF&"အခက်အခဲတစုံတရာရှိလာရင်လည်း"&CRLF&"Feedback ကိုနှိပ်ပြီး"&CRLF&"ကျွန်တော်ဆီတို့ဆီတိုက်ရိုက်အကြံပြုနိုင်ပါတယ် :)"
	txt7.TextSize = 14
	txt7.Typeface = my
	txt7.TextColor = Colors.Black
	txt7.Gravity = Gravity.CENTER_HORIZONTAL
		
	Activity.AddView(scl,0%x,0%y,100%x,100%y)
	
	scl.Panel.AddView(txt,0%x,5%y,100%x,80%y)
	scl.Panel.AddView(txt1,0%x,(txt.Top+txt.Height),100%x,45%y)
	scl.Panel.AddView(txt2,0%x,(txt1.Top+txt1.Height),100%x,47%y)
	scl.Panel.AddView(txt3,0%x,(txt2.Top+txt2.Height),100%x,27%y)
	scl.Panel.AddView(btni,20%x,(txt3.Top+txt3.Height),60%x,10%y)
	scl.Panel.AddView(txt4,0%x,(btni.Top+btni.Height)+2%y,100%x,115%y)
	scl.Panel.AddView(txt5,0%x,(txt4.Top+txt4.Height),100%x,33%y)
	scl.Panel.AddView(btnhi,20%x,(txt5.Top+txt5.Height),60%x,10%y)
	scl.Panel.AddView(txt6,0%x,(btnhi.Top+btnhi.Height)+2%y,100%x,210%y)
	scl.Panel.AddView(btntuto,20%x,(txt6.Top+txt6.Height),60%x,10%y)
	scl.Panel.AddView(txt7,0%x,(btntuto.Top+btntuto.Height),100%x,32%y)
	scl.Panel.AddView(AdView3,0%x,(txt7.Top+txt7.Height),100%x,15%y)
	scl.Panel.Height = 650%y
	
	Activity.AddMenuItem("Help","help")
	Activity.AddMenuItem("About","about")
	Activity.AddMenuItem("Feedback","feedback")
	Activity.AddMenuItem("Rate App","rate")
	Activity.AddMenuItem("More App","more")
	Activity.AddMenuItem("Share App","share")
	
	End Sub
Sub help_Click
	File.Delete(File.DirRootExternal,"Phetkon.apk")
	StartActivity(help)
End Sub

Sub about_Click
	File.Delete(File.DirRootExternal,"Phetkon.apk")
	Dim i As Int
	Dim p As PhoneIntents
	i = Msgbox2("App Name: Phetkon Font" &CRLF&CRLF& "Version: 1.0" &CRLF&CRLF&"Font Dev: Khon Soe Zaw Thu"&CRLF&CRLF& "Developed By: Khun Htetz Naing","About App","Rate App","","Ok",Null)
 If i = DialogResponse.POSITIVE Then
  StartActivity(p.OpenBrowser("https://play.google.com/store/apps/details?id=com.htetznaing.Phetkonfont"))
 End If
End Sub

Sub feedback_Click
	Dim p As PhoneIntents
	StartActivity(p.OpenBrowser("https://www.facebook.com/Khun.Htetz.Naing"))
End Sub

Sub rate_Click
		Dim p As PhoneIntents
		StartActivity(p.OpenBrowser("https://play.google.com/store/apps/details?id=com.htetznaing.Phetkonfont&hl=es"))
End Sub
Sub more_Click
		Dim p As PhoneIntents
		StartActivity(p.OpenBrowser("https://play.google.com/store/apps/developer?id=PaOh%20IT%20Youth&hl=es"))
End Sub

Sub share_Click
	Dim ShareIt As Intent
    ShareIt.Initialize (ShareIt.ACTION_SEND,"")
    ShareIt.SetType ("text/plain")
    ShareIt.PutExtra ("android.intent.extra.TEXT","'Phetkon' Beautiful Myanmar Font Style Download at Google Play Store: https://play.google.com/store/apps/details?id=com.htetznaing.Phetkonfont&hl=es")
    ShareIt.PutExtra ("android.intent.extra.SUBJECT","Get Free!!")
    ShareIt.WrapAsIntentChooser("Share App Via...")
StartActivity (ShareIt)
End Sub

Sub btni_Click
	Dim p As PhoneIntents
	StartActivity(p.OpenBrowser("http://paohityouth.blogspot.com/2016/09/iFont-Donate-5-7-7-App.html"))
End Sub

Sub btnhi_Click
	Dim p As PhoneIntents
	StartActivity(p.OpenBrowser("http://paohityouth.blogspot.com/2016/09/HiFont-6-3-9-App.html"))
End Sub

Sub btntuto_Click
	Dim p As PhoneIntents
	StartActivity(p.OpenBrowser("http://paohityouth.blogspot.com/2016/09/Fixed-Font-Not-Supported.html"))
End Sub

Sub Ad2_FailedToReceiveAd (ErrorCode As String)
    Log("failed: " & ErrorCode)
End Sub
Sub Ad2_ReceiveAd
    Log("received")
End Sub
Sub Ad2_AdScreenDismissed
   Log("screen dismissed")
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub
