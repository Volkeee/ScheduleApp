package com.ifntuog.volkeee.schedule.tools.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by volkeee on 10/24/17.
 */

public class AnimatedImageView extends android.support.v7.widget.AppCompatImageView {
    private int fadeInDuration = 500;
    private int timeBetween = 2500;
    private int fadeOutDuration = 1000;
    private int images[];
    private int imageIndex = 0;
    private boolean animateForever = true;

    public AnimatedImageView(Context context, final int images[], final int imageIndex) {
        super(context);
        this.images = images;
    }

    public int getFadeInDuration() {
        return fadeInDuration;
    }

    public void setFadeInDuration(int fadeInDuration) {
        this.fadeInDuration = fadeInDuration;
    }

    public int getTimeBetween() {
        return timeBetween;
    }

    public void setTimeBetween(int timeBetween) {
        this.timeBetween = timeBetween;
    }

    public int getFadeOutDuration() {
        return fadeOutDuration;
    }

    public void setFadeOutDuration(int fadeOutDuration) {
        this.fadeOutDuration = fadeOutDuration;
    }

    public int[] getImages() {
        return images;
    }

    public void setImages(int[] images) {
        this.images = images;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public boolean isAnimateForever() {
        return animateForever;
    }

    public void setAnimateForever(boolean animateForever) {
        this.animateForever = animateForever;
    }

    private void performAnimation() {
        this.setVisibility(View.VISIBLE);
        this.setImageResource(this.getImages()[this.imageIndex]);
    }
}
