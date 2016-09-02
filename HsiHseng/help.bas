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
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim su As StringUtils 
	Dim p As PhoneIntents 
	Dim lstOne As ListView 
    Dim AdView2 As AdView
	Dim mm As Typeface : mm = Typeface.LoadFromAssets("Hsi-Hseng.ttf")
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.Title = "About"
	Activity.Color = Colors.RGB (235,235,235)
	
	 AdView2.Initialize("Ad2", "ca-app-pub-4173348573252986/3457605356")
     Activity.AddView(AdView2, 0%x, 70%y, 100%x,30%y)
     AdView2.LoadAd
	
	Dim imvLogo As ImageView 
	imvLogo.Initialize ("")
	imvLogo.Bitmap = LoadBitmap(File.DirAssets , "icon.png")
	imvLogo.Gravity = Gravity.FILL 
	Activity.AddView ( imvLogo , 50%x - 50dip  , 20dip ,  100dip  ,  100dip )
	
	Dim lblName As  Label 
	Dim bg As ColorDrawable 
	bg.Initialize (Colors.DarkGray , 10dip)
	lblName.Initialize ("")
	lblName.Background = bg
	lblName.Gravity = Gravity.CENTER 
	lblName.Text = "အောက်ပါနေရာများတွင် အကူအညီတောင်းယူနိုင်ပါသည်။"
	lblName.Typeface = mm
	lblName.TextSize = 13
	lblName.TextColor = Colors.White 
	Activity.AddView (lblName , 10%x, 130dip , 80%x , 50dip)
	lblName.Height = su.MeasureMultilineTextHeight (lblName, lblName.Text ) + 5dip
	
	
	Dim c As ColorDrawable 
	c.Initialize (Colors.White , 10dip )
	lstOne.Initialize ("lstOnes")
	lstOne.Background = c
	lstOne.SingleLineLayout .Label.TextSize = 12
	lstOne.SingleLineLayout .Label .TextColor = Colors.DarkGray 
	lstOne.SingleLineLayout .Label .Gravity = Gravity.CENTER 
	lstOne.SingleLineLayout .ItemHeight = 40dip
	lstOne.AddSingleLine2 ("Myanmar Unicode Area"&CRLF&"https://www.facebook.com/groups/mmUnicode/", 1)
	lstOne.AddSingleLine2 ("Myanmar PaOh Unicode Area"&CRLF&"https://www.facebook.com/groups/PaOhUnicode/",2)
	lstOne.AddSingleLine2 ("ပအိုဝ်း IT လူငယ်များ"&CRLF&"https://www.facebook.com/PaOhITYouth", 3)
	Activity.AddView ( lstOne,3%x, (lblName.Top+lblName.Height)+2%y , 94%x,35%y)
	
	Dim lblCredit As Label 
	lblCredit.Initialize ("lblCredit")
	lblCredit.TextColor = Colors.RGB (48,154,6)
	lblCredit.TextSize = 13
	lblCredit.Gravity = Gravity.CENTER 
	lblCredit.Text = "Special Thanks Khon Soe Zaw Thu"
	Activity.AddView (lblCredit, 10dip, 310dip, 100%x - 20dip, 50dip)
	lblCredit.Height = su.MeasureMultilineTextHeight (lblCredit, lblCredit.Text )
		
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

Sub lblCredit_Click
	StartActivity(p.OpenBrowser ("https://www.facebook.com/khonsoezawthu"))
End Sub
Sub Activity_Resume
     
End Sub

Sub Activity_Pause (UserClosed As Boolean)
     
End Sub

Sub lstOnes_ItemClick (Position As Int, Value As Object)
     Select Value
	 	Case 1
			StartActivity(p.OpenBrowser("https://www.facebook.com/groups/mmUnicode/"))
			Case 2
				StartActivity(p.OpenBrowser("https://www.facebook.com/groups/PaOhUnicode/"))
	 			Case 3
				   StartActivity(p.OpenBrowser ("https://www.facebook.com/PaOhITYouth"))
	 End Select
End Sub


