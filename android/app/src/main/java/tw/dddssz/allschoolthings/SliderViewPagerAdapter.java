package tw.dddssz.allschoolthings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SliderViewPagerAdapter extends PagerAdapter
{
    private Context context;
    private LayoutInflater layoutInflater;
    private  Integer [] images = {R.drawable.slider001, R.drawable.slider002, R.drawable.slider003,R.drawable.slider004,R.drawable.slider005};  //600X500


    public SliderViewPagerAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount(){
    return images.length;
}
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.bulletin_custom_layout,null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        ImageView imageView = (ImageView) view.findViewById(R.id.m0000_img01);
        imageView.setImageResource(images[position]);
        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);
        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
