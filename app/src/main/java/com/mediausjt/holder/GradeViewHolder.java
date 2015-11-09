package com.mediausjt.holder;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.mediausjt.R;
import com.mediausjt.adapter.GradeAdapter;
import com.mediausjt.database.DBHelper;
import com.mediausjt.grade.Grade;
import com.mediausjt.util.FragmentUtil;
import com.mediausjt.util.MediaConfig;
import com.mediausjt.util.MediaDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GradeViewHolder extends RecyclerView.ViewHolder {

    final DBHelper meudb = new DBHelper(MediaConfig.getActivity());

    private List<Grade> gradeList;

    @Bind(R.id.tv_matter_name)
    TextView tvMatterName;
    @Bind(R.id.tv_required_grade)
    TextView tvRequiredGrade;

    @Bind(R.id.iv_arrow)
    ImageView ivArrow;
    private GradeAdapter gradeAdapter;

    public GradeViewHolder(GradeAdapter gradeAdapter, View itemView, List<Grade> gradeList) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.gradeAdapter = gradeAdapter;
        this.gradeList = gradeList;

        MediaConfig.addSecondFontTo(tvRequiredGrade);

        SwipeLayout swipeLayout = (SwipeLayout) itemView.findViewById(R.id.lt_swipe);

        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.setLeftSwipeEnabled(false);

        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, itemView.findViewById(R.id.bottom_wrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                ivArrow.setRotation(0);
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                ivArrow.setRotation(leftOffset / 3);
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                ivArrow.setRotation(180f);
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

    }

    public void setColor() {
        double gradeValue = Double.parseDouble(tvRequiredGrade.getText().toString());
        int media = MediaConfig.getPreference("media", 6);

        if (gradeValue < media) {
            tvRequiredGrade.setTextColor(MediaConfig.green());
        } else {
            tvRequiredGrade.setTextColor(MediaConfig.red());
        }
    }

    @OnClick(R.id.bt_edit_matter)
    public void editMatter(View view) {
        Grade grade = gradeList.get(getAdapterPosition());

        MediaConfig.savePreference("notaMinima", tvMatterName.getText().toString());
        MediaConfig.savePreference("gradeColor", tvRequiredGrade.getCurrentTextColor());
        MediaConfig.savePreference("idToUpdate", String.valueOf(grade.getId()));

        FragmentUtil.isEditGrade = true;
        MediaConfig.goToNewGrade();
    }

    @OnClick(R.id.bt_remove_matter)
    public void removeMatter(View view) {
        removeAt(getAdapterPosition());

        MediaDialog.showSnack(view, R.string.removed_grade, Snackbar.LENGTH_SHORT);
    }

    private void removeAt(int position) {
        int gradePosition = gradeList.get(position).getId();

        meudb.deletarNota(gradePosition);

        gradeList.remove(position);
        gradeAdapter.notifyItemRemoved(position);
    }

    public TextView getTvMatterName() {
        return tvMatterName;
    }

    public TextView getTvRequiredGrade() {
        return tvRequiredGrade;
    }
}
