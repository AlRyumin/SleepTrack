package com.study.alryumin.sleeptrack.view.sleep_time.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.study.alryumin.sleeptrack.Constants;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.view.sleep_time.view.adapter.SleepTimeRecyclerViewAdapter;

public class SleepTimeTouchHelper extends ItemTouchHelper.Callback {
    private Context context;

    private SleepTimeRecyclerViewAdapter adapter;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private final String TAG = "TOUCH_HELPER";

    private Paint mClearPaint;
    private ColorDrawable mBackground;
    private int backgroundColorLeft, backgroundColorRight;
    private Drawable deleteDrawable, editDrawable;
    private int intrinsWidthDelete, intrinsHeightDelete, intrinsWidthEdit, intrinsHeightEdit;

    public SleepTimeTouchHelper(Context context){
        this.context = context;

        mBackground = new ColorDrawable();
        backgroundColorLeft = Color.parseColor("#b71c1c");
        backgroundColorRight = Color.parseColor("#33691e");
        mClearPaint = new Paint();
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        deleteDrawable = ContextCompat.getDrawable(context, R.drawable.ic_delete);
        intrinsWidthDelete = deleteDrawable.getIntrinsicWidth();
        intrinsHeightDelete = deleteDrawable.getIntrinsicHeight();

        editDrawable = ContextCompat.getDrawable(context, R.drawable.ic_edit);
        intrinsWidthEdit = editDrawable.getIntrinsicWidth();
        intrinsHeightEdit = editDrawable.getIntrinsicHeight();
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        final SleepTime item = adapter.getValues().get(position);

        if(direction == ItemTouchHelper.LEFT){
            removeItem(position, item);
        }

        if(direction == ItemTouchHelper.RIGHT){
            editItem(position, item);
        }

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        boolean right = true;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX > 0) {
            right = true;
        } else if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX < 0){
            right = false;
        }

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getHeight();

        itemView.setBackgroundColor(recyclerView.getResources().getColor(R.color.colorEndBackground));

        boolean isCancelled = dX == 0 && !isCurrentlyActive;

        if (isCancelled) {
            clearCanvas(c, itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            itemView.setBackgroundColor(recyclerView.getResources().getColor(R.color.colorBackground));

            return;
        }

        if(right){
            mBackground.setColor(backgroundColorRight);
            mBackground.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
            mBackground.draw(c);

            int editIconTop = itemView.getTop() + (itemHeight - intrinsHeightEdit) / 2;
            int editIconMargin = (itemHeight - intrinsHeightEdit) / 2;
            int editIconLeft = itemView.getLeft() + editIconMargin;
            int editIconRight = editIconLeft + intrinsWidthEdit;
            int editIconBottom = editIconTop + intrinsHeightEdit;

            editDrawable.setBounds(editIconLeft, editIconTop, editIconRight, editIconBottom);
            editDrawable.draw(c);
        } else {
            mBackground.setColor(backgroundColorLeft);
            mBackground.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
            mBackground.draw(c);

            int deleteIconTop = itemView.getTop() + (itemHeight - intrinsHeightDelete) / 2;
            int deleteIconMargin = (itemHeight - intrinsHeightDelete) / 2;
            int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsWidthDelete;
            int deleteIconRight = itemView.getRight() - deleteIconMargin;
            int deleteIconBottom = deleteIconTop + intrinsHeightDelete;


            deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
            deleteDrawable.draw(c);
        }



        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);


    }

    private void removeItem(int position, SleepTime item){
        adapter.removeItem(position);

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.restoreItem(position, item);
                recyclerView.scrollToPosition(position);
            }
        });

        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void editItem(int position, SleepTime item){
        Intent intent = new Intent(context, EditView.class);
        intent.putExtra(Constants.SLEEP_TIME_ID, item.getId());
        context.startActivity(intent);
//        adapter.changeItem(position, item, context);
    }

    private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom) {
        c.drawRect(left, top, right, bottom, mClearPaint);

    }

    public void setAdapter(SleepTimeRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    public void setCoordinatorLayout(CoordinatorLayout coordinatorLayout) { this.coordinatorLayout = coordinatorLayout; }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
