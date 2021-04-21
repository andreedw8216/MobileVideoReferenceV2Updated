package sdk.livechat.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

//import tetherfi.livechat.AvBridge;
import sdk.livechat.CallSession;
import sdk.livechat.Configs;
import sdk.livechat.Livechat2Api;
import sdk.livechat.Log;
import sdk.livechat.MessageType;
import sdk.livechat.SysAddr;
import sdk.livechat.Version;
import sdk.livechat.logger.LvLogger;
//import tetherfi.livechat.signature.SignatureView;

import static sdk.livechat.CallSession.EventIndex.MEDIA_REQ_RECEIVED;

public class Livechat2ApiActivity extends Activity
{

    ViewGroup lvchat, logs, video, vactive;
    EditText m_LogsView;
    TextView m_LvchatVer;
    EditText m_txtUrl, m_txtMsg;
    LinearLayout m_video_layout;
    ViewGroup m_ViewArea;


    Livechat2Api livechat2Api;
    CallSession callSession;

    public static final String AMNUWAN = "2c92809b7212e9f201722238df7a0002";
    public static final String   NUWAN = "2c92809d71ca1b840171cae021000002";
    public static final String     AMY = "2c92809671c9caf00171c9e964f40002";
    public static final String   COCKE = "2c92809c71d5213c0171deeb5dce0006";

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);

        lvchat = (ViewGroup)getLayoutInflater().inflate( R.layout.activity_livechat, null );
        logs = (ViewGroup)getLayoutInflater().inflate( R.layout.activity_logs, null );
        video = (ViewGroup)getLayoutInflater().inflate( R.layout.activity_video, null );


        switchView( lvchat );
        addContentView( logs, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ) );
        addContentView( video, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ) );
        logs.setVisibility( View.GONE );
        m_LogsView = (EditText)findViewById( R.id.txtLogs );
        m_LvchatVer = ( TextView )findViewById( R.id.lvchatversion);
        m_LvchatVer.setText( Version.getVersionStr() );
        m_txtUrl = findViewById( R.id.txtUrl );
        m_txtMsg = findViewById( R.id.txtMsg );
        m_video_layout = findViewById( R.id.vid_layout );
        m_ViewArea = (ViewGroup) findViewById( R.id.view_Area );


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
        Log.d( "Test start" );

        //AvBridge.debug_Instance.debug_Test();

        /*
        SignatureView sig = new SignatureView(this);
        sig.setBackgroundColor(Color.BLUE );
        m_ViewArea.addView( sig );
         */

        /*

        boolean result =  CallSession.PermissionCheck( this.getApplicationContext() );

        new AlertDialog.Builder(this)
                .setTitle("Permission" )
                .setMessage( result ?  "Permission granted" : "Permission denied" )

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

         */
    }


    public void onClickSend( View v )
    {

        livechat2Api.sendLv2Message(
                MessageType.USER_MESSAGE,
                m_txtMsg.getText().toString(),
                new SysAddr("user", COCKE),
                null
        );
        /*
        lvSession.SendUserMessage(m_txtMsg.getText().toString(), "*", new SignallingApi.ISendCallback() {
            @Override
            public void callback(boolean status, String reason) {
                Log.d( "Message " + ( status ? "sent" : "could not send" ) );
            }
        });
         */
    }


    public void onClickVideo( View view )
    {
        switchView( video );
    }

    public void onClickAV( View view )
    {
        String rid = AMNUWAN;
        String rname = "xx";

        //{"auth":"mBzHeFN+mt0Olz8a0pq1wx0v5VREEpWFFmRRtPhBM+dCuFWh8pyYk8rsEO1AdFz4chHlz+1ME8bZwCfFgg0ed9dVeCgbIc9zmeZIig/9zKkQB9JQIap/aHtra47XY1SLHfmfjJH7DMJ+Y8tFwPrPzXPKd/jQV5QrChxlt2kfsLJgoDForqnR7zmCdpBpEOCLM94dcejJsb+hhUakNM7YzdG/e8+5LzS+MK0ZURXivDTxN1Kml3w8j8sJABeJu0YJhqt9jDssAmkmRP9LamyF/QEKHWnz8pz9p+1Q74vXYHSk0NejuoF9cOZ3k7kwGXO4RTgeCy+YAeA69X0/QIPyVQ==","from":"WJDt6sV4f5M0e8QAvaoOjS9VxWf1y0nEN3WAXP2OpT91JSgK5HScDJSXn7aryMWj","type":"call-signal","content":"{\"type\":\"requestav\",\"param\":\"video\",\"owner\":\"user:WJDt6sV4f5M0e8QAvaoOjS9VxWf1y0nEN3WAXP2OpT91JSgK5HScDJSXn7aryMWj\",\"version\":2,\"__localId\":\"user:WJDt6sV4f5M0e8QAvaoOjS9VxWf1y0nEN3WAXP2OpT91JSgK5HScDJSXn7aryMWj\",\"isvideo\":true,\"avlsequence\":1,\"avrsequence\":0}","to":"user:1344","attributes":{},"org":null}

        callSession.StartAv(
                "video",
                "{ \"id\":\"" + rid + "\", \"title\":\"" + rname + "\", \"remoteAddr\":\"user:" + rid + "\" }",
                false );
    }

    private Livechat2Api.IMessageListener onUserMessage = new Livechat2Api.IMessageListener() {
        @Override
        public void onMessage(MessageType type, String content, String from)
        {
            Log.i( "User message : " + content  );
            Log.i( "From : "  + from );
        }
    };

    private CallSession.IEventHandler onAvRequest = new CallSession.IEventHandler() {
        @Override
        public void handle(CallSession.EventIndex event, String param1, String param2)
        {
            Log.i( "OnAVRequest" );
        }
    };

    private CallSession.IEventHandler onConnecting = new CallSession.IEventHandler() {
        @Override
        public void handle(CallSession.EventIndex event, String param1, String param2)
        {
            Log.i( "onConnecting" );
        }
    };

    private CallSession.IEventHandler onConnected = new CallSession.IEventHandler() {
        @Override
        public void handle(CallSession.EventIndex event, String param1, String param2) {
            Log.i( "onConnected" );
        }
    };

    private CallSession.IEventHandler onReconnecting = new CallSession.IEventHandler() {
        @Override
        public void handle(CallSession.EventIndex event, String param1, String param2) {
            Log.i( "onReconnecting" );
        }
    };

    private CallSession.IEventHandler onEnded = new CallSession.IEventHandler() {
        @Override
        public void handle(CallSession.EventIndex event, String param1, String param2) {
            Log.i( "onEnded" );
        }
    };

    private CallSession.IAvListener onVideoEvents = new CallSession.IAvListener() {
        @Override
        public void OnVideoViewAdded(SurfaceView view, String type, String id)
        {
            Log.i( "Video received, type=" + type + ", id=" + id + ", hash=" + id.hashCode() );
            if( type.equals("local") )
            {
                Log.i( "local video ignored" );
                return;
            }

            view.setId( id.hashCode() );
            switchView( video );
            m_video_layout.addView( view );
            Log.i( "Id of the view is " + view.getId() );
        }

        @Override
        public void OnVideoViewRemoved(String id) {

        }
    };

    // Application Logic Start Here

    private void initWebviewSig()
    {
        Configs.Instance.sigDetail = "https://sit.tetherfi.com:16081/videoapi/mwebview_x.html";
        //Configs.Instance.sigDetail = "http://sit.tetherfi.com:8082/videoapi/mwebview_x.html";
        //Configs.Instance.sigDetail = "http://sit.tetherfi.com:8082/videoapi/test.html";
        Configs.Instance.sigType = "tetherfi.livechat.WebviewSigSession";
        Configs.Instance.webViewIgnoreSslError = true;
    }

    //String token = "WMEd8B3NVl8FQWC96l5qAcIwS4DL5grRtLtZKuQqbkUk7bJpu++ChgjqBV4pUn3z5W5T6fp5manuyqu2AAK8vk1h47nGXJHBO4OtyXkBHcQxIWyX0zdustQciiyzYS5CzRhzbXzqFQ88WLfSfCt/0dk5JiIV6WSZfAJoHVvBk/KxCdM+JaSM4ugkJ4kTdt5eAtqiv7vzSnWKTU1fKsZ3neVCFjtAuc96L52qTv54ci1BRzsnCYIBnFwKEY+gIVOQObQzuvUvNTPTuZpJbf6iFxT9G+TCfz/X98lDN97KCZrmpodMpEPREYR5Ys6qSrVLyIdvu26pWCfD/0Bjj32CZw==";//"mBzHeFN+mt0Olz8a0pq1wx0v5VREEpWFFmRRtPhBM+dCuFWh8pyYk8rsEO1AdFz4chHlz+1ME8bZwCfFgg0ed9dVeCgbIc9zmeZIig/9zKkQB9JQIap/aHtra47XY1SLHfmfjJH7DMJ+Y8tFwPrPzXPKd/jQV5QrChxlt2kfsLJgoDForqnR7zmCdpBpEOCLM94dcejJsb+hhUakNM7YzdG/e8+5LzS+MK0ZURXivDTxN1Kml3w8j8sJABeJu0YJhqt9jDssAmkmRP9LamyF/QEKHWnz8pz9p+1Q74vXYHSk0NejuoF9cOZ3k7kwGXO4RTgeCy+YAeA69X0/QIPyVQ==";
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjJjOTI4MDlkNzFjYTFiODQwMTcxY2FlMDIxMDAwMDAyIiwibmFtZSI6Im51d2FuQHRldGhlcmZpLmNvbSIsIm9yZ2FuaXphdGlvbiI6IlByb2R1Y3RRQSIsImlhdCI6MTU5NDg0MzMxNiwiZXhwIjoxNTk1MDE2MTE2LCJhdWQiOiJudXdhbkB0ZXRoZXJmaS5jb20iLCJpc3MiOiJndWFyZGlhbi9vcmdhbml6YXRpb24iLCJzdWIiOiJudXdhbkB0ZXRoZXJmaS5jb20iLCJqdGkiOiJjMjlmYjVkYi02YWU4LTRmOGEtYTk5NC00YmVhZWI0YThiNWEifQ._JXbHbdJxYp-tfj63nH9PjsLwn9vF0qyBnkkyFomIkY";
    String sig = "wss://139.0.19.14:9443/ws-chat-proxy/ws/chat?token" + token;
    //String sig = "wss://proxy.qa.tetherfi.cloud/ws-chat-proxy/ws/chat?token=" + token;
    //wss://10.243.134.10/ws-chat-proxy/ws/chat?token=null&sessionId=3a600342-a7a3-4c66-bbd3-f67de5d7096f&authType=cimb

    private void initNativeSig()
    {
        String url = m_txtUrl.getText().toString();
        livechat2Api = new Livechat2Api( sig );
        callSession = new CallSession( this );

        livechat2Api.registerListener( MessageType.USER_MESSAGE, "app", onUserMessage );
        callSession.AddCallEventHandler( MEDIA_REQ_RECEIVED, "app", onAvRequest );
        callSession.AddCallEventHandler(CallSession.EventIndex.CONNECTING_MEDIA, "app", onConnecting );
        callSession.AddCallEventHandler(CallSession.EventIndex.INCALL_MEDIA, "app", onConnected );
        callSession.AddCallEventHandler(CallSession.EventIndex.RECONNECTING_MEDIA, "app", onReconnecting );
        callSession.AddCallEventHandler(CallSession.EventIndex.ENDED, "app", onEnded );
        callSession.SetVideoEventHandler( onVideoEvents );

    }

    private void initApp()
    {
        Log.SetLogger( logger );

        initNativeSig();
        Configs.Instance.mediaConnectionMode = Configs.MediaConnMode.MediaConnModeAll;
        Configs.Instance.addAvProxy( "turn:10.243.134.10:3579", "avaya", "root01" );
        Configs.Instance.addAvProxy( "turn:139.0.19.14:3579", "avaya", "root01" );
    }


    public void onClickOpen( View v )
    {
        // lvSession.Open();
    }


    public void onClickStart( View v )
    {
        //String custDetails = "{\"pChannel\":\"\",\"pIntent\":\"demoappchat\",\"pNationality\":\"\",\"pLang\":\"\",\"pChatBotSid\":\"\",\"pAuthType\":\"\",\"pVerificationType\":\"\",\"pUserId\":\"\",\"pCIF\":\"\",\"pName\":\"Nu\",\"pGender\":\"Male\",\"pDOB\":\"\",\"pMobileNumber\":\"\",\"pCustomerTag\":\"\",\"pPIBissueDate\":\"\",\"pOTPPref\":\"\",\"pTokenSerialNumber\":\"\",\"pInvalidLogonAttempt\":\"\",\"pCustIdStatus\":\"\",\"pTokenActivated\":\"\",\"pTokenStatus\":\"\",\"pFailedTwoFACount\":\"\",\"pFirstLogiDate\":\"\",\"pfirstLoginTime\":\"\",\"plastLoginDate\":\"\",\"plastLoginTime\":\"\",\"pLeadID\":\"\",\"pApplicationID\":\"\",\"pOnboardingStatus\":\"\"}";
        String custDetails = "{\"eCustDetails\":{\"referenceNo\":\"GMXVE82AB\",\"userBrowserInfo\":{\"browserVersion\":\"-\",\"browserName\":\"-\",\"type\":\"Android\",\"isRunningOnMobile\":\"true\"}}}";
        livechat2Api.setUserAuth( NUWAN, token );

        livechat2Api.start( custDetails, true );

        callSession.Initialize( NUWAN );

        /*
        Instance.sigDetail = m_txtUrl.getText().toString();
        Livechat.SessionDetails sessDetails = new Livechat.SessionDetails( custDetails );
        lvSession.SetUserAuth( "Nuwan", "xx" );
        Instance.avCallContext = this;
        lvSession.Start( sessDetails, "[]" );
         */

    }


    public void onClickEnd( View v )
    {
        String r = "User clicked end";
        callSession.EndAv( r );
        livechat2Api.end( r );
    }


    public void onClickReconnect( View v )
    {
        //lvSession.TryReconnect();
    }

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
            super.d(tag, sid, msg, time);
        }

        @Override
        public void e(String tag, String sid, String msg, String time) {
            AddLogMessage(time + " ERROR {" + sid + "} " + tag + ": " + msg);
            super.e(tag, sid, msg, time);
        }

        @Override
        public void w(String tag, String sid, String msg, String time) {
            AddLogMessage(time + " WARN {" + sid + "} " + tag + ": " + msg);
            super.w(tag, sid, msg, time);
        }
    };

}
