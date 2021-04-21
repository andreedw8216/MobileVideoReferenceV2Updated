package sdk.livechat.testapp;

import android.app.Activity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class TestAppLogger extends sdk.livechat.logger.LvLogger
{
    public boolean LogModeFile = false;

    Activity activity;

    public TestAppLogger( Activity  activity )
    {
        this.activity = activity;
    }

    @Override
    public void i(String tag, String sid, String msg, String time)
    {
        String str = "\n" + time + " INFO " + tag + ": " + msg;

        if( LogModeFile ) setfile( str );
        else setui( str );

        super.i(tag, sid, msg, time);
    }

    @Override
    public void d(String tag, String sid, String msg, String time )
    {
        String str = "\n" + time + " DEBUG " + tag + ": " + msg;

        if( LogModeFile ) setfile( str );
        else setui( str );

        super.d( tag, sid, msg, time );
    }

    @Override
    public void e(String tag, String sid, String msg, String time )
    {
        String str = "\n" + time + " ERROR " + tag + ": " + msg;

        if( LogModeFile ) setfile( str );
        else setui( str );

        super.e( tag, sid, msg, time );
    }

    @Override
    public void w(String tag, String sid, String msg, String time )
    {
        String str = "\n" + time + " WARN " + tag + ": " + msg;

        if( LogModeFile ) setfile( str );
        else setui( str );

        super.w( tag, sid, msg, time );
    }

    private void setui( final String str )
    {
        //activity.LogToUI( str );
    }

    private void setfile( final String str )
    {
        File logFile = new File("sdcard/testapp.txt");
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(str);
            buf.newLine();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
