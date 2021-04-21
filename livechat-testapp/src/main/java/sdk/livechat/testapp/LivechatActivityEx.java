package sdk.livechat.testapp;

import android.app.Activity;

public class LivechatActivityEx extends Activity
{
    /*


    Livechat lvSession;
    ViewGroup lvchat, logs, vactive;
    EditText m_LogsView;
    TextView m_LvchatVer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        lvchat = (ViewGroup)getLayoutInflater().inflate( R.layout.activity_livechat, null );
        logs = (ViewGroup)getLayoutInflater().inflate( R.layout.activity_logs, null );
        switchView( lvchat );
        addContentView( logs, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ) );
        logs.setVisibility( View.GONE );
        m_LogsView = (EditText)findViewById( R.id.txtLogs );
        m_LvchatVer = ( TextView )findViewById( R.id.lvhatversion );
        m_LvchatVer.setText( Version.getVersionStr() );

        initApp();
    }


    private void switchView(ViewGroup v)
    {
        if( vactive != null ) {
            vactive.setVisibility(View.GONE);
        }
        setContentView( v );
        vactive = v;
        v.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed()
    {
        if( vactive == logs )
        {
            switchView( lvchat );
            return;
        }

        super.onBackPressed();
    }


    public void AddLogMessage( String msg )
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    m_LogsView.append(msg + "\n");
            }
        });
    }


    public void onClickClear(View v)
    {
        m_LogsView.setText( "" );
    }

    public void onClickLogs( View v )
    {
        switchView( logs );
    }


    public void onClickTest( View v )
    {

    }


    // Application Logic Start Here

    private void initApp()
    {
        Configs.Instance.sigDetail = "https://sit.tetherfi.com:16081/videoapi/mwebview_x.html";
        Configs.Instance.mediaConnectionMode = Configs.MediaConnMode.MediaConnModeAll;

        Configs.Instance.sigType = "tetherfi.livechat.WebviewSigSessionEx";
        lvSession = new Livechat( this.getApplicationContext() );
        lvSession.SetAvEventsHandler( avhandlers );
        lvSession.SetSessionEventsHandler( handlers );
        lvSession.SetLogger( logger );

        Configs.Instance.addAvProxy( "turn:demo.tetherfi.com:3579", "tetherfi", "nuwan" );
        Configs.Instance.addAvProxy( "turn:sit.tetherfi.com:3585", "tetherfi", "nuwan" );
    }

    public void onClickOpen( View v )
    {
        lvSession.Open();
    }


    public void onClickStart( View v )
    {
        String custDetails = "{\"pChannel\":\"\",\"pIntent\":\"livechat\",\"pNationality\":\"\",\"pLang\":\"\",\"pChatBotSid\":\"\",\"pAuthType\":\"\",\"pVerificationType\":\"\",\"pUserId\":\"\",\"pCIF\":\"\",\"pName\":\"Nu\",\"pGender\":\"Male\",\"pDOB\":\"\",\"pMobileNumber\":\"\",\"pCustomerTag\":\"\",\"pPIBissueDate\":\"\",\"pOTPPref\":\"\",\"pTokenSerialNumber\":\"\",\"pInvalidLogonAttempt\":\"\",\"pCustIdStatus\":\"\",\"pTokenActivated\":\"\",\"pTokenStatus\":\"\",\"pFailedTwoFACount\":\"\",\"pFirstLogiDate\":\"\",\"pfirstLoginTime\":\"\",\"plastLoginDate\":\"\",\"plastLoginTime\":\"\",\"pLeadID\":\"\",\"pApplicationID\":\"\",\"pOnboardingStatus\":\"\"}";

        try
        {
            Livechat.SessionDetails sessDetails = new Livechat.SessionDetails( custDetails );
            lvSession.Start( sessDetails, "[]" );
        }
        catch ( JSONException e )
        {
            Log.e( "Invalid JSON format" );
        }

    }


    public void onClickEnd( View v )
    {
        lvSession.End( "User clicked end" );
    }


    public void onClickReconnect( View v )
    {
        lvSession.TryReconnect();
    }

    private Livechat.SessionEventsHandler handlers = new Livechat.SessionEventsHandler() {

        @Override
        public void OnStartSuccess(String sid, String tchatid, String param) {

        }

        @Override
        public void OnMessageReceived(String agent, String msg) {

        }

        @Override
        public void OnAppMessageReceived(String agent, String msg)
        {

        }

        @Override
        public void OnStartFailed(String errorCode) {

        }

        @Override
        public void OnOpenSuccess(String sessionDesc) {

        }

        @Override
        public void OnOpenFailed(String errorCode)
        {

        }

        @Override
        public void OnEnd(String initiator, String reason) {

        }

        @Override
        public void OnRemoteUserConnected(String name, String agentId, String sessionId) {

        }

        @Override
        public void OnCallQueued(String position, String extra) {

        }

        @Override
        public void OnStatusNotification(String notification) {

        }

        @Override
        public void OnSignallingStateChanged(String state) {

        }

        @Override
        public void OnConferenceUserLeft(String agent, String msg) {

        }

        @Override
        public void OnTypingStateChanged(String agent, int state) {

        }

        @Override
        public void OnCallbackRequestStatus(boolean isCustomReq, boolean status) {

        }
    };


    private Livechat.AvCallEventsHandler avhandlers = new Livechat.AvCallEventsHandler()
    {

        @Override
        public void OnRequestAvCall(String avmode)
        {
            lvSession.AvResponse( avmode );

            if( avmode.equals( "video" ) )
                lvSession.StartVideoCall();
            else if( avmode.equals( "audio" ) )
                lvSession.StartAudioCall();
            else if( avmode.equals( "false" ) )
                ; // nothing to do
            else
                Log.e( "Unexpected avmode(1) : " + avmode );
        }

        @Override
        public void OnRespondAvCall(String avmode) {

            if( avmode.equals( "video" ) )
                lvSession.StartVideoCall();
            else if( avmode.equals( "audio" ) )
                lvSession.StartAudioCall();
            else if( avmode.equals( "false" ) )
                ; // nothing to do
            else
                Log.e( "Unexpected avmode(2) : " + avmode );

        }

        @Override
        public void OnLocalVideo(SurfaceView localVideoView)
        {
            lvchat.addView( localVideoView );
        }

        @Override
        public void OnRemoteVideo(SurfaceView remoteVideoView)
        {
            lvchat.addView( remoteVideoView );
        }

        @Override
        public void OnAvCallStartSuccess()
        {

        }

        @Override
        public void OnAvCallStartFailure(Exception if_any) {

        }

        @Override
        public void OnMediaDisconnect() {

        }

        @Override
        public void OnMediaReconnect() {

        }

        @Override
        public void OnMediaReconnectableEnd() {

        }

        @Override
        public void OnAvCallEnd(RTCSession.RTCEventsHandler.EndType type, String reasonDesc) {

        }
    };

    // End of Application Logic


    LvLogger logger  = new LvLogger() {
        @Override
        public void i(String tag, String sid, String msg, String time) {
            AddLogMessage(time + " INFO {" + sid + "} " + tag + ": " + msg);
            super.i(tag, sid, msg, time);
        }

        @Override
        public void d(String tag, String sid, String msg, String time) {
            AddLogMessage(time + " DEBUG {" + sid + "} " + tag + ": " + msg);
        }

        @Override
        public void e(String tag, String sid, String msg, String time) {
            AddLogMessage(time + " ERROR {" + sid + "} " + tag + ": " + msg);
        }

        @Override
        public void w(String tag, String sid, String msg, String time) {
            AddLogMessage(time + " WARN {" + sid + "} " + tag + ": " + msg);
        }

    };


     */

}

