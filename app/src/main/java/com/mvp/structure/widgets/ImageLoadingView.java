package com.mvp.structure.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mvp.structure.R;

import butterknife.Unbinder;
import io.supercharge.shimmerlayout.ShimmerLayout;

/**
 * Created by thai.cao on 8/1/2019.
 */
public class ImageLoadingView extends FrameLayout {
    private Unbinder unbinder;

    CardView cardView;
    ImageView loadedIv;
    View progressView;
    private int mBorderWidth;
    View progressBar2;
    ShimmerLayout shimmerImagePlaceHolder;

    public ImageLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ImageLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public ImageLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.layout_common_image, this);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseImageView);
        try {
            mBorderWidth = a.getDimensionPixelSize(R.styleable.BaseImageView_border_radius, getContext().getResources().getDimensionPixelOffset(R.dimen.common_radius));
        } finally {
            a.recycle();
        }


        loadedIv = findViewById(R.id.ivLoaded);
        progressView = findViewById(R.id.progressLoading);
        progressView.setVisibility(GONE);
        progressBar2 = findViewById(R.id.progressBar2);
        cardView = findViewById(R.id.cardView);
        shimmerImagePlaceHolder = findViewById(R.id.shimmerImagePlaceHolder);
    }

    public void activeNormalProgressBarSize() {
        int normalSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                46, getResources().getDisplayMetrics());
        progressBar2.getLayoutParams().width = normalSize;
        progressBar2.getLayoutParams().height = normalSize;
    }

    public ShimmerLayout getShimmerLayout() {
        return findViewById(R.id.shimmerImagePlaceHolder);
    }

    public ImageView getImageView() {
        return findViewById(R.id.ivLoaded);
    }

    public void loadImage(Context c, String url) {
        loadImage(c, url, false);
    }

    public void loadImage(Context c, String url, boolean isThumbnail) {
        cardView.setRadius(mBorderWidth);

        if (shimmerImagePlaceHolder != null) {
            shimmerImagePlaceHolder.setVisibility(VISIBLE);
            shimmerImagePlaceHolder.startShimmerAnimation();
        }

        if (loadedIv != null && progressView != null && !TextUtils.isEmpty(url)) {

            RequestBuilder requestBuilder = Glide.with(c)
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if (progressView != null)
                                progressView.setVisibility(GONE);

                            if (shimmerImagePlaceHolder != null) {
                                shimmerImagePlaceHolder.setVisibility(GONE);
                                shimmerImagePlaceHolder.stopShimmerAnimation();
                            }
                            setImageEmpty(R.drawable.ic_loading);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (progressView != null)
                                progressView.setVisibility(GONE);

                            if (shimmerImagePlaceHolder != null) {
                                shimmerImagePlaceHolder.setVisibility(GONE);
                                shimmerImagePlaceHolder.stopShimmerAnimation();
                            }
                            return false;
                        }

                    })
                    .apply(new RequestOptions()
                            .skipMemoryCache(false)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .dontTransform()
                            .dontAnimate()
                            .error(R.drawable.ic_loading));
            if(isThumbnail){
                requestBuilder.thumbnail(0.1f);
            }
            requestBuilder.into(loadedIv);
        } else {
            if (progressView != null)
                progressView.setVisibility(GONE);

            if (shimmerImagePlaceHolder != null) {
                shimmerImagePlaceHolder.setVisibility(GONE);
                shimmerImagePlaceHolder.stopShimmerAnimation();
            }
            setImageEmpty(R.drawable.ic_loading);
        }
    }
    public void loadImage(Context c, String url, RequestListener requestListener) {
        cardView.setRadius(mBorderWidth);
        //if (progressView!=null) progressView.setVisibility(View.VISIBLE);

        if (shimmerImagePlaceHolder != null) {
            shimmerImagePlaceHolder.setVisibility(VISIBLE);
            shimmerImagePlaceHolder.startShimmerAnimation();
        }

        if (loadedIv != null && progressView != null && !TextUtils.isEmpty(url)) {
            Glide.with(c)
                    .load(url)
                    .listener(requestListener)
                    .apply(new RequestOptions()
                            .skipMemoryCache(false)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .dontTransform()
                            .dontAnimate()
                            .error(R.drawable.ic_loading))
                    .into(loadedIv);
        } else {
            setImageEmpty(R.drawable.ic_loading);
        }
    }


    public void setImageEmpty(int resource) {
        loadedIv.setImageResource(resource);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onDetachedFromWindow() {
        // View is now detached, and about to be destroyed
        if (shimmerImagePlaceHolder != null)
            shimmerImagePlaceHolder.stopShimmerAnimation();
        super.onDetachedFromWindow();
    }
}
