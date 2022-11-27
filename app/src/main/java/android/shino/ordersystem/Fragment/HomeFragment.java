package android.shino.ordersystem.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.shino.ordersystem.Bean.Food;
import android.shino.ordersystem.Adapter.FoodAdapter;
import android.shino.ordersystem.R;

public class HomeFragment extends Fragment {
    private Food[] foods = {new Food("口水鸡", R.drawable.p1, "19.9", "4.7", "味道很好，菜量很足，本地销售冠军"),
            new Food("满杯百香果", R.drawable.p2, "7.0", "4.5", "总计1699人收藏，近30日80人复购"),
            new Food("巴西烤肉披萨", R.drawable.p3, "29.8", "4.7", "精选品牌，热销掌柜，网红店"),
            new Food("龙门花甲", R.drawable.p4, "15.9", "4.6", "套餐很划算，干净又卫生"),
            new Food("美味炸鸡", R.drawable.p5, "21.8", "4.4", "近3小时11人下单"),
            new Food("煎饼果子", R.drawable.p6, "5.9", "4.8", "非常用心地在做美食"),
            new Food("黄焖鸡米饭", R.drawable.p7, "11.9", "4.9", "好吃，分量足，性价比高"),
            new Food("西施烤肉饭", R.drawable.p8, "18.8", "4.5", "爆款烤肉饭，全是回头客"),
            new Food("十三香龙虾", R.drawable.p9, "98.0", "4.9", "本店销量第3名，正宗13香"),
            new Food("招牌辣子鸡", R.drawable.p10, "24.9", "4.7", "辣子鸡中的高人气店铺"),
            new Food("招牌无骨炸鸡", R.drawable.p11, "21.9", "4.9", "本店回头客第一名，炸鸡品类优质商品"),
            new Food("红烧排骨饭", R.drawable.p12, "28.9", "4.6", "本地拌饭套餐热销第2名")
    };

    private List<Food> foodList = new ArrayList<>();
    private FoodAdapter foodAdapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // 创建数据源
        initFoods();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        // 创建适配器，同时加载数据源
        foodAdapter = new FoodAdapter(foodList);
        // 设置适配器
        recyclerView.setAdapter(foodAdapter);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.design_default_color_primary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFoods();
            }
        });
        return view;
    }

    private void initFoods() {
        foodList.clear();
        Random random = new Random();
        int index = random.nextInt(foods.length);
        for (int i = index; i < foods.length; i++) {
            foodList.add(foods[i]);
        }
        for (int i = 0; i < index; i++) {
            foodList.add(foods[i]);
        }
    }

    private void refreshFoods() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFoods();
                        foodAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

}
