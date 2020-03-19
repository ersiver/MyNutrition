package com.breiter.mynutrition.ui.tool;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public abstract class ItemSwipeHelper extends ItemTouchHelper.SimpleCallback {
    private static final int BUTTON_WIDTH = 180;
    private int swipedPos = -1;
    private float swipeThreshold = 0.5f;
    private RecyclerView recyclerView;
    private List<UnderlayButton> buttons;
    private GestureDetector gestureDetector;
    private Map<Integer, List<UnderlayButton>> buttonsBuffer;
    private Queue<Integer> recoverQueue;

    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            for (UnderlayButton button : buttons) {
                if (button.onClick(e.getX(), e.getY())) {
                    swipedPos = -1;
                    break;
                }
            }
            return true;
        }
    };

    @SuppressLint({"ClickableViewAccessibility", "UseSparseArrays"})
    protected ItemSwipeHelper(Context context, RecyclerView recyclerView) {
        super(0, ItemTouchHelper.LEFT);
        this.recyclerView = recyclerView;
        this.buttons = new ArrayList<>();
        this.gestureDetector = new GestureDetector(context, gestureListener);

        View.OnTouchListener onTouchListener = (view, e) -> {
            view.performClick();
            if (swipedPos < 0) return false;
            Point point = new Point((int) e.getRawX(), (int) e.getRawY());
            RecyclerView.ViewHolder swipedViewHolder = recyclerView.findViewHolderForAdapterPosition(swipedPos);
            if (swipedViewHolder != null) {
                View swipedItem = swipedViewHolder.itemView;
                Rect rect = new Rect();
                swipedItem.getGlobalVisibleRect(rect);

                if (e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_MOVE) {
                    if (rect.top < point.y && rect.bottom > point.y)
                        gestureDetector.onTouchEvent(e);
                    else {
                        recoverQueue.add(swipedPos);
                        swipedPos = -1;
                        recoverSwipedItem();
                    }
                }
            }
            return false;
        };
        this.recyclerView.setOnTouchListener(onTouchListener);
        buttonsBuffer = new HashMap<>();
        recoverQueue = new LinkedList<Integer>() {
            @Override
            public boolean add(Integer o) {
                if (contains(o))
                    return false;
                else
                    return super.add(o);
            }
        };
        attachSwipe();
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();

        if (swipedPos != pos)
            recoverQueue.add(swipedPos);
        swipedPos = pos;

        if (buttonsBuffer.containsKey(swipedPos))
            buttons = buttonsBuffer.get(swipedPos);
        else
            buttons.clear();

        buttonsBuffer.clear();
        swipeThreshold = 0.5f * buttons.size() * BUTTON_WIDTH;
        recoverSwipedItem();
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return swipeThreshold;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return 0.1f * defaultValue;
    }

    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        return 5.0f * defaultValue;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        int pos = viewHolder.getAdapterPosition();
        float translationX = dX;
        View itemView = viewHolder.itemView;

        if (pos < 0) {
            swipedPos = pos;
            return;
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                List<UnderlayButton> buffer = new ArrayList<>();
                if (!buttonsBuffer.containsKey(pos)) {
                    initUnderlayButton(viewHolder, buffer);
                    buttonsBuffer.put(pos, buffer);
                } else {
                    buffer = buttonsBuffer.get(pos);
                }
                translationX = dX * buffer.size() * BUTTON_WIDTH / itemView.getWidth();
                drawButtons(c, itemView, buffer, pos, translationX);
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive);
    }

    private void recoverSwipedItem() {
        while (recoverQueue != null && !recoverQueue.isEmpty()) {
            Integer pos = recoverQueue.poll();
            if (pos != null) {
                if (pos > -1) {
                    if (recyclerView.getAdapter() != null)
                        recyclerView.getAdapter().notifyItemChanged(pos);
                }
            }
        }
    }

    private void drawButtons(Canvas c, View itemView, List<UnderlayButton> buffer, int position, float dX) {
        float right = itemView.getRight();
        float dButtonWidth = (-1) * dX / buffer.size();

        for (UnderlayButton button : buffer) {
            float left = right - dButtonWidth;
            button.onDraw(
                    c,
                    new RectF(
                            left,
                            itemView.getTop(),
                            right,
                            itemView.getBottom()
                    ),
                    position
            );

            right = left;
        }
    }

    private void attachSwipe() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public abstract void initUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons);

    public static class UnderlayButton {
        private final static int CORNER_RADIUS = 5;
        private Context context;
        private int imageResId;
        private int color;
        private int pos;
        private RectF clickRegion;
        private UnderlayButtonClickListener clickListener;

        public UnderlayButton(Context context, int imageResId, int color, UnderlayButtonClickListener clickListener) {
            this.context = context;
            this.imageResId = imageResId;
            this.color = color;
            this.clickListener = clickListener;
        }

        boolean onClick(float x, float y) {
            if (clickRegion != null && clickRegion.contains(x, y)) {
                clickListener.onClick(pos);
                return true;
            }
            return false;
        }

        void onDraw(Canvas c, RectF rect, int pos) {
            Paint p = new Paint();

            // Draw background
            p.setColor(color);
            c.drawRoundRect(rect, CORNER_RADIUS, CORNER_RADIUS, p);

            //Draw icon in the middle
            Drawable d = ContextCompat.getDrawable(context, imageResId);
            Bitmap bitmpap = drawableToBitmap(d);
            if (d != null) {
                c.drawBitmap(bitmpap, (rect.left + rect.right) / 2 - (float) d.getIntrinsicWidth() / 2,
                        (rect.top + rect.bottom) / 2 - (float) d.getIntrinsicHeight() / 2, p);
            }

            clickRegion = rect;
            this.pos = pos;
        }

        private Bitmap drawableToBitmap(Drawable d) {
            if (d instanceof BitmapDrawable)
                return ((BitmapDrawable) d).getBitmap();

            Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(),
                    d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            d.draw(canvas);

            return bitmap;
        }
    }

    public interface UnderlayButtonClickListener {
        void onClick(int pos);
    }
}

