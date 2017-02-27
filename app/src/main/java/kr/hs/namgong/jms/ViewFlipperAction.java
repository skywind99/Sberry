package kr.hs.namgong.jms;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * Created by lsh on 2017. 1. 1..
 */

public class ViewFlipperAction implements View.OnTouchListener {

    Context context;

    //전체화면개수
    int countIndexes;
    //현재화면인덱스
    int currentIndex;
    //터치시작 x좌표
    float downX;
    //터치끝 x좌표
    float upX;
    ViewFlipper flipper;

    //
    ViewFlipperCallback indexCallback;


    //인터페이스 - 탭 클릭시 이미지 변경하기 위한 인터페이스
    //여러 액티비티가 fragment를 호출하여도 동일한 인터페이스를 구현하도록 한다
    public static interface ViewFlipperCallback {
        public void onFlipperActionCallback(int position);
    }

    public ViewFlipperAction(Context context, ViewFlipper flipper) {
        this.context = context;
        this.flipper = flipper;

        if (context instanceof ViewFlipperCallback) {
            indexCallback = (ViewFlipperCallback) context;
        }

        currentIndex = 0;
        downX = 0;
        upX = 0;
        countIndexes = flipper.getChildCount();

        //인덱스 업데이트
        indexCallback.onFlipperActionCallback(currentIndex);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //터치시작
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = event.getX();
        }
        //터치종료
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            upX = event.getX();

            //왼쪽 -> 오른쪽
            if (upX < downX) {
                //애니메이션
                flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.appear_from_right));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.disappear_to_left));

                //인덱스체크 - 마지막화면이면 동작없음
                if (currentIndex < (countIndexes - 1)) {
                    flipper.showNext();

                    currentIndex++;
                    //인덱스 업데이트
                    indexCallback.onFlipperActionCallback(currentIndex);
                    Toast.makeText(context, currentIndex + "", Toast.LENGTH_SHORT).show();

                } else {
                    flipper.showNext();
                    currentIndex = 0;

                    indexCallback.onFlipperActionCallback(currentIndex);
                    Toast.makeText(context, currentIndex + "", Toast.LENGTH_SHORT).show();


                }
            }
            //오른쪽 -> 왼쪽
            else if (upX > downX) {
                //애니메이션 설정
                flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.appear_from_left));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.disappear_to_right));

                //인덱스체크 - 첫번째화면이면 동작없음
                if (currentIndex > 0) {
                    flipper.showPrevious();
                    currentIndex--;
                    //인덱스 업데이트
                    indexCallback.onFlipperActionCallback(currentIndex);

                    Toast.makeText(context, currentIndex + "", Toast.LENGTH_SHORT).show();

                } else {

                    flipper.showPrevious();
                    currentIndex = countIndexes-1;
                    indexCallback.onFlipperActionCallback(currentIndex);
                    Toast.makeText(context, currentIndex + "", Toast.LENGTH_SHORT).show();


                }
            }
        }

        return true;
    }
}