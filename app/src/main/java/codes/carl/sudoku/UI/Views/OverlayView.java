package codes.carl.sudoku.UI.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import codes.carl.sudoku.R;

/**
 * Overlay View
 * <p>
 * A view that contains a semitransparent overlay to place over a camera preview window
 * to aid in the capturing of Sudoku puzzle images.
 *
 * @author Carl Poole
 */
public class OverlayView extends View {

    Bitmap bitmap;
    Canvas auxCanvas;
    RectF rect = new RectF();
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    PorterDuffXfermode clearMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

    public OverlayView(Context context) {
        super(context);
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int outerFillColor = ContextCompat.getColor(getContext(), R.color.colorAccent2);

        int w = canvas.getWidth();
        int h = canvas.getHeight();

        int left = 100;
        int right = w - 100;
        int top = (h / 2) - (right - (w / 2));
        int bottom = (h / 2) + (right - (w / 2));

        rect.set(left, top, right, bottom);
        float radius = 10.0f;

        // Make sure bitmap and canvas are initialized
        if (bitmap == null || auxCanvas == null) {
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            auxCanvas = new Canvas(bitmap);
        }

        // Fill the bitmap with the desired outside color
        paint.setColor(outerFillColor);
        paint.setStyle(Paint.Style.FILL);
        auxCanvas.drawPaint(paint);

        // Put a transparent hole in the shape of a square in the middle of the view
        paint.setXfermode(clearMode);
        auxCanvas.drawRoundRect(rect, radius, radius, paint);

        // Draw a white rect border
        paint.setXfermode(null);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        auxCanvas.drawRoundRect(rect, radius, radius, paint);

        // Draw the whole thing to the original canvas
        canvas.drawBitmap(bitmap, 0, 0, paint);

    }
}
