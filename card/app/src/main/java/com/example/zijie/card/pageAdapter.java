package com.example.zijie.card;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class pageAdapter extends PagerAdapter {
    private Context mContent ;
    private List<String> page_TitleList;
    private List<String> page_ContentList;
    private  List<Integer> visible ;

    public pageAdapter(Context context, List<String> title_list,List<String> content_list,List<Integer> vis)
    {
        mContent = context;
        page_TitleList = title_list;
        page_ContentList = content_list;
        visible = vis;


    }



    @Override
    public int getCount() {
        return page_TitleList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(mContent,R.layout.page_item,null);
        TextView textView1 = view.findViewById(R.id.content_text);
        textView1.setText(page_ContentList.get(position));
        TextView textView2 = view.findViewById(R.id.title_text);
        textView2.setText(page_TitleList.get(position));
        Button button = view.findViewById(R.id.enter);
        button.setVisibility(visible.get(position));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContent,"123",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContent,loginActivity.class);
                mContent.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View)object);
    }


}
