package com.example.xihad.pixlups;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SliderAdapter extends PagerAdapter {

    private  int [] img = {R.drawable.car,R.drawable.car3,R.drawable.car4,R.drawable.car3};
    private LayoutInflater inflater;
    private Context context;

    public SliderAdapter(Context context){
        this.context=context;
    }



    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(LinearLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

       inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
       View v = inflater.inflate(R.layout.sliderr,container,false);

        ImageView imageView = v.findViewById(R.id.sliderimg);
        imageView.setImageResource(img[position]);

        container.addView(v);
        return  v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.invalidate();
    }

}
