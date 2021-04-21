package sdk.livechat.testapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import sdk.livechat.Configs;
import sdk.livechat.Livechat;
import sdk.livechat.Log;
import sdk.livechat.SignallingApi;
import sdk.livechat.Version;
import sdk.livechat.logger.LvLogger;

import static sdk.livechat.Configs.Instance;

public class LivechatActivity extends Activity
{
    Livechat lvSession;
    ViewGroup lvchat, logs, video, vactive;
    EditText m_LogsView;
    TextView m_LvchatVer;
    EditText m_txtUrl, m_txtMsg;

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
        lvSession.SendUserMessage(m_txtMsg.getText().toString(), "*", new SignallingApi.ISendCallback() {
            @Override
            public void callback(boolean status, String reason) {
                Log.d( "Message " + ( status ? "sent" : "could not send" ) );
            }
        });
        /*
        //long id = sdp.testCreate();
        System.out.println( "instance id " + SdpMung.Instance().nativeInstance );
        SdpMung.Instance().finalize();
        System.out.println( "post fin instance id " + SdpMung.Instance().nativeInstance );
        SdpMung.Instance().finalize();
        */
    }


    public void onClickVideo( View view )
    {
        switchView( video );
    }

    // Application Logic Start Here

    private void initWebviewSig()
    {
        Configs.Instance.sigDetail = "https://sit.tetherfi.com:16081/videoapi/mwebview_x.html";
        //Configs.Instance.sigDetail = "http://sit.tetherfi.com:8082/videoapi/mwebview_x.html";
        //Configs.Instance.sigDetail = "http://sit.tetherfi.com:8082/videoapi/test.html";
        Configs.Instance.sigType = "tetherfi.livechat.WebviewSigSession";
        Configs.Instance.webViewIgnoreSslError = true;
    }

    private void initNativeSig()
    {
        Instance.sigType = "tetherfi.livechat.signalling.NativeSig";
        //Instance.sigDetail = "wss://192.168.1.102:8443/ws-chat-proxy/ws/chat";

        Instance.sigDetail = m_txtUrl.getText().toString();

        //livechat.Start( sess, txtContent.getText().toString() );
    }

    private void initApp()
    {
        //initWebviewSig();
        initNativeSig();
        Configs.Instance.mediaConnectionMode = Configs.MediaConnMode.MediaConnModeAll;

        lvSession = new Livechat( );
        lvSession.SetAvEventsHandler( avhandlers );
        lvSession.SetSessionEventsHandler( handlers );
        lvSession.SetLogger( logger );

        Configs.Instance.addAvProxy( "turn:10.243.134.10:3579", "avaya", "root01" );
        Configs.Instance.addAvProxy( "turn:139.0.19.14:3579", "avaya", "root01" );
    }


    public void onClickOpen( View v )
    {
        lvSession.Open();
    }


    public void onClickStart( View v )
    {
        Instance.sigDetail = m_txtUrl.getText().toString();

        String custDetails = "{\"pChannel\":\"\",\"pIntent\":\"demoappchat\",\"pNationality\":\"\",\"pLang\":\"\",\"pChatBotSid\":\"\",\"pAuthType\":\"\",\"pVerificationType\":\"\",\"pUserId\":\"\",\"pCIF\":\"\",\"pName\":\"Nu\",\"pGender\":\"Male\",\"pDOB\":\"\",\"pMobileNumber\":\"\",\"pCustomerTag\":\"\",\"pPIBissueDate\":\"\",\"pOTPPref\":\"\",\"pTokenSerialNumber\":\"\",\"pInvalidLogonAttempt\":\"\",\"pCustIdStatus\":\"\",\"pTokenActivated\":\"\",\"pTokenStatus\":\"\",\"pFailedTwoFACount\":\"\",\"pFirstLogiDate\":\"\",\"pfirstLoginTime\":\"\",\"plastLoginDate\":\"\",\"plastLoginTime\":\"\",\"pLeadID\":\"\",\"pApplicationID\":\"\",\"pOnboardingStatus\":\"\"}";

        Livechat.SessionDetails sessDetails = new Livechat.SessionDetails( custDetails );
        lvSession.SetUserAuth( "Nuwan", "xx" );
        Instance.avCallContext = this;
        lvSession.Start( sessDetails, "[]" );
    }


    public void onClickEnd( View v )
    {
        lvSession.End( "User clicked end" );
    }


    public void onClickReconnect( View v )
    {
        lvSession.TryReconnect();
    }


    private Livechat.SessionEventsHandler handlers = new Livechat.SessionEventsHandler()
    {
        @Override
        public void OnStartSuccess(String sid, String tchatid, String param)
        {
            Log.d( "OnStartSuccess" );
        }

        @Override
        public void OnStartFailed(String errorCode)
        {
            Log.d( "OnStartFailed" );
        }

        @Override
        public void OnUserMessage(JSONObject msg, String fromUser) {
            Log.d( "OnUserMessage" );
        }

        @Override
        public void OnOpenSuccess(String sessionDesc)
        {
            Log.d( "OnOpenSuccess" );
        }

        @Override
        public void OnOpenFailed(String errorCode)
        {
            Log.d( "OnOpenFailed" );
        }

        @Override
        public void OnEnd(String initiator, String reason)
        {
            Log.d( "OnEnd" );
        }

        @Override
        public void OnCSOJoin(String name, String userid, String rsessionId)
        {
            Log.d( "OnCSOJoin" );
        }

        @Override
        public void OnRequestQueued(int position, String reqid)
        {
            Log.d( "OnRequestQueued" );
        }

        @Override
        public void OnQueuePositionUpdated(int position, String reqid)
        {
            Log.d( "OnQueuePositionUpdated" );
        }


        @Override
        public void OnSignallingStateChanged(String state)
        {
            Log.d( "OnSignallingStateChanged" );
        }

        @Override
        public void OnCSOLeft(String name, String userid)
        {
            Log.d( "OnCSOLeft" );
        }

        @Override
        public void OnTypingState(int state, String fromUser)
        {
            Log.d( "OnTypingState" );
        }

        @Override
        public void OnCallbackRequestStatus(boolean isCustomReq, boolean status)
        {
            Log.d( "OnTypingStateChanged" );
        }

        @Override
        public void OnCustomMessage(String type, JSONObject content, String from)
        {
            Log.d( "OnCustomMessage" );
        }
    };


    private Livechat.AvCallEventsHandler avhandlers = new Livechat.AvCallEventsHandler()
    {
        @Override
        public void OnRequestAvCall(String avmode)
        {
            Log.d( "OnRequestAvCall." );
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
        public void OnRespondAvCall(String avmode)
        {
            Log.d( "OnRespondAvCall" );

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
        public void OnLocalVideo(View localVideoView)
        {
            Log.d( "OnLocalVideo" );
            lvchat.addView( localVideoView );
        }


        @Override
        public void OnRemoteVideo(View remoteVideoView)
        {
            Log.d( "OnRemoteVideo" );
            lvchat.addView( remoteVideoView );
        }


        @Override
        public void OnAvCallStartSuccess()
        {
            Log.d( "OnAvCallStartSuccess" );
        }

        @Override
        public void OnAvCallStartFailure(Exception if_any)
        {
            Log.d( "OnAvCallStartFailure" );
        }

        @Override
        public void OnMediaDisconnect()
        {
            Log.d( "OnMediaDisconnect" );
        }

        @Override
        public void OnMediaReconnect()
        {
            Log.d( "OnMediaReconnect" );
        }

        @Override
        public void OnMediaReconnectableEnd()
        {
            Log.d( "OnMediaReconnectableEnd" );
        }

        @Override
        public void OnAvCallEnd(String endType, String reasonDesc) {

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

}

