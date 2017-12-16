package codes.carl.sudoku.UI.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import codes.carl.sudoku.R;

/**
 * Represents a Sudoku puzzle.
 *
 * @author Carl Poole
 */
public class SudokuGridView extends GridView {
    public SudokuGridView(Context context) {
        super(context);
    }

    public SudokuGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SudokuGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SudokuGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        final int count = getChildCount();

        float density = getResources().getDisplayMetrics().density;

        long thicknessOffset = Math.round(0.5 * density);

        Paint gridPaint = new Paint();
        gridPaint.setColor(ContextCompat.getColor(getContext(), R.color.grid_divider));
        gridPaint.setStrokeWidth(thicknessOffset);

        Paint quadrantPaint = new Paint();
        quadrantPaint.setColor(ContextCompat.getColor(getContext(), R.color.quadrant_divider));
        quadrantPaint.setStrokeWidth(Math.round(1 * density));

        for (int i = 0; i < count; i++) {

            View child = getChildAt(i);
            int bottom = child.getBottom();
            int top = child.getTop();
            int left = child.getLeft();
            int right = child.getRight();

            // Draw a thicker vertical line on the right of columns 3 and 6
            if ((i + 1) % 3 == 0 && (i + 1) % 9 != 0) {
                canvas.drawLine(right, top, right, bottom, quadrantPaint);
            } else if ((i + 1) % 9 != 0) {
                canvas.drawLine(right, top + thicknessOffset, right, bottom - thicknessOffset, gridPaint);
            }

            // Draw a thicker horizontal line on the bottom of rows 3 and 6
            if (((i + 1) % 27 > 18 || (i + 1) % 27 == 0) && i < 64 && i > 0) {
                canvas.drawLine(left, bottom, right, bottom, quadrantPaint);
            } else if (i < 72) {
                canvas.drawLine(left + thicknessOffset, bottom, right - thicknessOffset, bottom, gridPaint);
            }

        }

        super.dispatchDraw(canvas);

    }

}
