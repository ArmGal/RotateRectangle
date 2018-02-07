package com.example.user.drawrotaterectangle;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

    private ImageView imageview;
    private TextView txtAngle;
    private Spinner spinnerAngle;
    private ProgressBar progressBarAngle;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;

    private int ShapePadding = 80;
    private int degreesAngle;
    private int angle;
    private String operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        createBitmap();
        createCanvas();
        createPaint();

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.rotate(
                degreesAngle,
                canvas.getWidth() / 2,
                canvas.getHeight() / 2
        );

        txtAngle.setText("0");

        createRectangleShape();

        imageview.setImageBitmap(bitmap);
        bitmap = null;

        spinnerAngle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                angle = Integer.parseInt(spinnerAngle.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                float x = event.getX();

                if (x < getScreenWidth() / 2) {
                    operation = "-";
                } else {
                    operation = "+";
                }
                rotate();
                break;
        }
        return true;
    }

    private void findViews() {
        imageview = findViewById(R.id.imageView);
        txtAngle = findViewById(R.id.txt_angle);
        spinnerAngle = findViewById(R.id.spinner_angle);
        progressBarAngle = findViewById(R.id.progress_bar_angle);
    }

    private void rotate() {
        createBitmap();
        createCanvas();
        createPaint();

        if (operation.equals("+")) {
            degreesAngle += angle;
        } else if (operation.equals("-")) {
            degreesAngle -= angle;
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.rotate(
                degreesAngle,
                canvas.getWidth() / 2,
                canvas.getHeight() / 2
        );

        if (degreesAngle > 360) {
            degreesAngle = degreesAngle - 360;
        } else if (degreesAngle < 0) {
            degreesAngle = degreesAngle + 360;
        }

        txtAngle.setText(getString(R.string.text_angle) + " : " + String.valueOf(degreesAngle));
        progressBarAngle.setProgress(degreesAngle);

        createRectangleShape();

        imageview.setImageBitmap(bitmap);
        bitmap = null;
    }

    public void createBitmap() {
        bitmap = Bitmap.createBitmap(
                400,
                300,
                Bitmap.Config.RGB_565
        );
    }

    public void createCanvas() {
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
    }

    public void createPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
    }

    public void createRectangleShape() {
        canvas.drawRect(ShapePadding, ShapePadding,
                canvas.getWidth() - ShapePadding,
                canvas.getHeight() - ShapePadding, paint);
        canvas.restore();
    }

    private int getScreenWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.x;
    }
}

