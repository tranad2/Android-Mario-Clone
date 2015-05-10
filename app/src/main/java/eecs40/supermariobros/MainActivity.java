package eecs40.supermariobros;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( new MarioSurfaceView ( getBaseContext() ) );
    }

}