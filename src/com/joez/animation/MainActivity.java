package com.joez.animation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public static class PlaceholderFragment extends Fragment implements OnClickListener{
		private Button mBtnTrans,mBtnRota,mBtnAlph,mBtnScale,mBtnMix;
		private ImageView mIvTarget;
		private int mScreenWidth,mScreenHeight;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			DisplayMetrics metrics=new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
			mScreenWidth=metrics.widthPixels;
			mScreenHeight=metrics.heightPixels;
			mBtnTrans=(Button)rootView.findViewById(R.id.btn_trans);
			mBtnRota=(Button)rootView.findViewById(R.id.btn_rota);
			mBtnAlph=(Button)rootView.findViewById(R.id.btn_alph);
			mBtnScale=(Button)rootView.findViewById(R.id.btn_scale);
			mBtnMix=(Button)rootView.findViewById(R.id.btn_mix);
			
			mIvTarget=(ImageView)rootView.findViewById(R.id.iv_target);
			
			mBtnTrans.setOnClickListener(this);
			mBtnRota.setOnClickListener(this);
			mBtnAlph.setOnClickListener(this);
			mBtnScale.setOnClickListener(this);
			mBtnMix.setOnClickListener(this);
			return rootView;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_trans:
				transLeftToRight(mIvTarget);
				break;
			case R.id.btn_rota:
				rotation(mIvTarget);
				break;
			case R.id.btn_alph:
				alphChange(mIvTarget);
				break;
			case R.id.btn_scale:
				scaleChange(mIvTarget);
				break;
			case R.id.btn_mix:
				mixChange(mIvTarget);
				break;
			default:
				break;
			}
			
		}
		
		private void mixChange(View view){
			int left=view.getLeft();
			PropertyValuesHolder scaleXHolder=PropertyValuesHolder.ofFloat("translationX", left,mScreenWidth-view.getRight()-view.getWidth(),left);
//			PropertyValuesHolder scaleYHolder=PropertyValuesHolder.ofFloat("scaleY", 1,2,1);
			
			ObjectAnimator.ofPropertyValuesHolder(view, scaleXHolder).setDuration(5000).start();
		}
		
		private void transLeftToRight(View view){
			int left=view.getLeft();
			ObjectAnimator animator=ObjectAnimator.ofFloat(view, "translationX", left,mScreenWidth-view.getRight()-view.getWidth());
			animator.setInterpolator(new CycleInterpolator(2.0f));
			animator.setDuration(3000).start();
		}
		private void rotation(View view){
			ObjectAnimator animator=ObjectAnimator.ofFloat(view, "rotationX", 0,360);
			animator.setInterpolator(new CycleInterpolator(100));
			animator.setDuration(30000).start();;
		}
		
		private void alphChange(View view){
			ObjectAnimator animator=ObjectAnimator.ofFloat(view, "alpha", 1,0,1);
			animator.setInterpolator(new LinearInterpolator());
			animator.setDuration(10000).start();
		}
		
		private void scaleChange(final View view){
			ObjectAnimator animator=ObjectAnimator.ofFloat(view, "xxx", 2);
			animator.setInterpolator(new LinearInterpolator());
			animator.setDuration(3000);
			animator.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float value=(Float) animation.getAnimatedValue();
					view.setScaleX(value);
					view.setScaleY(value);
				}
			});
			animator.start();
		}
		
	}
}
