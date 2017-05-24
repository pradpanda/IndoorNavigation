package com.example.piyush.indoornevigateion.view;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.piyush.indoornevigateion.R;

import controller.Algorithm;
import controller.Point;
import controller.WifiFinder;

import static java.lang.Thread.sleep;

/**
 * Created by Piyush on 25-02-2015.
 */
public class MapView extends ActionBarActivity implements View.OnClickListener {
    TextView text;
    static final long WIFI_SCAN_DELAY_MILLIS = 2000;
    ImageView img, dot;
    LinearLayout linear;
    Point[] point;
    String source, destination;
    PointF topRight,topLeft,bottomRight,bottomLeft;
    private Button submit;
    Canvas canvas;
    int position;
    int width,height;
    String pos="A";
    WifiFinder wifi;
    public static WifiManager wifiManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);


        Bundle b= getIntent().getExtras();
        source= b.getString("source");
        destination= b.getString("destination");

        linear = (LinearLayout)findViewById(R.id.linear);
        text= (TextView)findViewById(R.id.text);
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        Drawable drawableFore = getResources().getDrawable(R.drawable.dot);
        // drawableFore.setBounds(200,300,0,0);
        Drawable drawableBack = getResources().getDrawable(R.drawable.ground_floor);

        Bitmap bitmapFore = ((BitmapDrawable) drawableFore).getBitmap();
        Bitmap bitmapBack = ((BitmapDrawable) drawableBack).getBitmap();



        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        Bitmap scaledBitmapFore = Bitmap.createScaledBitmap(bitmapFore, 10, 10, true);
        // scaledBitmapFore.setPixel(200,300,0);
        Bitmap scaledBitmapBack = Bitmap.createScaledBitmap(bitmapBack, width, height, true);

        Algorithm algorithm = new Algorithm();
        point= algorithm.algo(source, destination);
        Bitmap combineImages = overlay(scaledBitmapBack, scaledBitmapFore);
        image.setImageBitmap(combineImages);


        //to sleep;
//        Thread timer= new Thread(){
//            public void run(){
//                try{
//                    sleep(2000);
//                    int x=566,y=1315;
//                    changeColor(canvas, x, y, R.drawable.reddot);
//                }catch(InterruptedException e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        timer.start();

//        for(int i=0;i<11;i++){
            int i=0;

                 i++;
                Log.e("hello","i m start scan   "+i);
            //TextView view= (TextView)findViewById(R.id.test);
            //text.setText(pos + i);
//            try {
//                sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        Thread t = new Thread(separateThread);
        t.start();
        }
//    }


    private void updateUI() {
        for(int i=0;i<12;i++) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wifi = new WifiFinder();
            wifiManager = (WifiManager) getSystemService(Service.WIFI_SERVICE);
            String pos = wifi.getPosition(this);
            Message msg = new Message();
            Bundle b = new Bundle();
            b.putString("pos", pos);
            b.putInt("i", i);
            msg.setData(b);
            handler.sendMessage(msg);
        }
//        long endTime = System.currentTimeMillis() + 5 * 1000;
//        while (System.currentTimeMillis() < endTime) {
//            String time = getTime();
//            Message msg = new Message();
//            Bundle b = new Bundle();
//            b.putString("timeKey", time);
//            msg.setData(b);
//            handler.sendMessage(msg);
//            Log.d(TAG, "time " + time);
//
//        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            String key = b.getString("pos");
            
            changeColor(canvas,x,y,redDot);
            int i = b.getInt("i");
            text.setText(key+i);
        }
    };

    private Runnable separateThread = new Runnable() {
        @Override
        public void run() {

            updateUI();
        }

    };





    private void changeColor(Canvas canvas, int x, int y, int redDot) {
        Paint paint = new Paint();
        paint.setTextSize(40);
        paint.setColor(Color.RED);
        Drawable drawableFore = getResources().getDrawable(redDot);
        Bitmap bitmapFore = ((BitmapDrawable) drawableFore).getBitmap();
        Bitmap scaledBitmapFore = Bitmap.createScaledBitmap(bitmapFore, 10, 10, true);
        int x1 = (int) (((float) x / 1040) * width);
        int y1 = (int) (((float)y / 3196) * height);
        canvas.drawBitmap(scaledBitmapFore, x1, y1, null);

    }


    public Bitmap overlay(Bitmap bmp1,Bitmap bmp2)
    {
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        try
        {

            Bitmap bmOverlay = Bitmap.createBitmap(width, height,  bmp1.getConfig());
            canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bmp1, new Matrix(), null);
            int l= point.length;
            Log.e("Map view",point[l-1].p);
            int i=l-1;
            if (point[l-1].z==0) {

//            int x= point[0].x;
//            int y= point[0].y;   1040,3196
                int x1 = (int) (((float) point[l-1].x / 1040) * width);
                int y1 = (int) (((float) point[l-1].y / 3196) * height);
                int x2, y2;
//              Log.e("Map View ","hello")
                Paint paint = new Paint();
                paint.setStrokeWidth((float) 4.0);
                paint.setColor(Color.BLACK + 10);
                canvas.drawBitmap(bmp2, x1, y1, null);
                canvas.drawText("Source",x1,y1-10,paint);
                System.out.println(point[0].p + "   " + x1 + "  " + y1);
                Log.e("Map View", point[1].p + "  " + point[1].z);
                for (i = l-2; i >=0 && point[i].z==0; i--) {

                    x2 = (int) (((float) point[i].x / 1040) * width);
                    y2 = (int) (((float) point[i].y / 3196) * height);
                    System.out.println(point[i].p + "  " + point[i].x + "  " + point[i].y);
                    canvas.drawBitmap(bmp2, x2, y2, null);
                    paint.setColor(Color.BLUE + 10);
                    canvas.drawLine(x1, y1, x2, y2, paint);
                    x1 = x2;
                    y1 = y2;

                }
            }

            if(i!=0){
                position = i;
            }
            return bmOverlay;
        } catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        if(position !=0){
            Log.e("MapView",point[position].p);
            Intent i = new Intent(this, NextFloors.class);
            i.putExtra("floorNum",1);
            i.putExtra("position",position);
            for(int j=position;j>=0;j--) {
                i.putExtra("Editing"+j, point[j]);
            }
            startActivity(i);
        }
    }



}
