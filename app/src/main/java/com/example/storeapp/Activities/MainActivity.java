package com.example.storeapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.storeapp.Fragments.CustomerFragment;
import com.example.storeapp.Fragments.HomePageFragment;
import com.example.storeapp.Fragments.OrderFragment;
import com.example.storeapp.Models.Customer;
import com.example.storeapp.Models.Item;
import com.example.storeapp.Models.ItemsResponse;
import com.example.storeapp.Models.OnCalledApi;
import com.example.storeapp.Models.OnCreateOrderListener;
import com.example.storeapp.Models.Order;
import com.example.storeapp.Models.ShoppingCart;
import com.example.storeapp.Models.api.ApiService;
import com.example.storeapp.Models.api.RetrofitHelper;
import com.example.storeapp.R;
import com.example.storeapp.ViewModels.MainAdapter;
import com.example.storeapp.databinding.MainLayoutBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements CustomerFragment.OnSelectCustomerListener, OrderFragment.OnOrderClickListener {

    public static final int CHOOSE_ITEM = 10000;
    public static final int ITEM_DETAIL = 10001;
    public static final int SHOPPING_CART = 10002;
    public static final String ITEM_CHOOSING = "item choosing";
    public static final String SEND_ITEM = "send item";
    public static final String SEND_QUANTITY = "send quantity";
    public static final String SEND_CUSTOMER = "send customer";
    public static final String SEND_SHOPPING_CART = "send shopping cart";

    public static final String SEND_CREATE_ORDER = "send created order";
    public static final String SEND_ORDER_LIST = "send order list";
    public static final int HOME_PAGE_ID = 0;
    public static final int ORDER_PAGE_ID = 1;
    public static final int CUSTOMER_PAGE_ID = 2;
    private MainLayoutBinding binding = null;
    private final String[] titles = {
            "Trang chủ", "Đơn hàng", "Khách hàng"
    };
    private boolean isChooseItem = false;
    private final int[] icon_pager = {
            R.drawable.ic_home_page,
            R.drawable.ic_menu,
            R.drawable.ic_customer
    };

    private Customer currentCustomer = null;
    private ShoppingCart shoppingCart = null;
    private ArrayList<Order> orderList = null;
    private OnCreateOrderListener createOrderListener = null;
    private OnCalledApi calledApi = null;
    private Retrofit retrofitHelper = null;
    private ApiService apiService = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 1. Action Bar setting
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 2. create list -  get order list from api
        retrofitHelper = RetrofitHelper.getInstance();
        apiService = retrofitHelper.create(ApiService.class);
        orderList = new ArrayList<>();
        // 3. Create page view Adapter
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), getLifecycle());
        // 3.1. update adapter to viewpager
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setTitle(titles[position]);
            }
        });
        // 3.2 update icon and content to tab layout
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                ((tab, position) -> {
                    tab.setText(titles[position]);
                    tab.setIcon(icon_pager[position]);
                })).attach();
    }

    public ViewPager2 getViewPager(){
        return binding.viewPager;
    }

    public Bundle sendShoppingCartList(){
        Bundle mb = new Bundle();
        mb.putParcelable(MainActivity.SEND_SHOPPING_CART, shoppingCart);
        mb.putBoolean(MainActivity.ITEM_CHOOSING, isChooseItem);
        return mb;
    }

    public void setOnCalledApi(OnCalledApi onCalledApi){
        this.calledApi = onCalledApi;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.order_cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // 1. log out and back to login activity
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
                return true;
            case R.id.shopping_cart:
                // 2. go to see shopping cart list
                Intent intentShopping = new Intent(this, ShoppingCartActivity.class);
                Bundle shoppingCartBundle = new Bundle();
                shoppingCartBundle.putParcelable(SEND_SHOPPING_CART, shoppingCart);
                shoppingCartBundle.putBoolean(ITEM_CHOOSING, isChooseItem);
                intentShopping.putExtras(shoppingCartBundle);
                startActivityForResult(intentShopping, CHOOSE_ITEM);
                return true;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_ITEM){
            // 1. from shopping cart activity
            // 1.1 go to home page
            binding.viewPager.setCurrentItem(HOME_PAGE_ID, false);
            if(resultCode == SHOPPING_CART){
                if(data != null) {
                    // 1.2 update data
                    Bundle bundle = data.getExtras();
                    isChooseItem = bundle.getBoolean(ITEM_CHOOSING);
                    shoppingCart = bundle.getParcelable(SEND_SHOPPING_CART);
                    Order newOrder = bundle.getParcelable(SEND_CREATE_ORDER);
                    if(newOrder != null){
                        // 1.3 add new order to order list
                        orderList.add(newOrder);
                        if(createOrderListener != null)
                            // 1.4 update from order fragment
                            createOrderListener.onOrderCreated(orderList);

                        Log.d("AAA", "from main: new order "+ newOrder.toString());
                    }
                    Log.d("AAA", "from main: size order list: " + Integer.toString(orderList.size()));
                }
            }
        }
        else if(requestCode == ITEM_DETAIL){
            // 2. from item detail activity
            // 2.0 back to home to continue choosing item
            binding.viewPager.setCurrentItem(HOME_PAGE_ID, false);
            if(resultCode == ItemDetailActivity.CREATE_ORDER){
                //2.1.1 get item and create new order with selected item
                Bundle itemBundle = data.getExtras();
                Item addItem = itemBundle.getParcelable(SEND_ITEM);
                int quantity = itemBundle.getInt(SEND_QUANTITY);
                //2.1.2 add item to shopping cart
                ShoppingCart newShoppingCart = new ShoppingCart(currentCustomer);
                newShoppingCart.addItem(addItem, quantity);
                //2.1.3 create new order with this item
                Order newOrder = new Order(newShoppingCart);
                if(newOrder != null){
                    orderList.add(newOrder);
                    if(createOrderListener != null)
                        createOrderListener.onOrderCreated(orderList);

                    Log.d("AAA", "from main: new order "+ newOrder.toString());
                }
                Log.d("AAA", "from main: size order list: " + Integer.toString(orderList.size()));
                Toast.makeText(this, "CREATE NEW ORDER", Toast.LENGTH_SHORT).show();
            }
            if(resultCode == ItemDetailActivity.ADD_TO_SHOPPING_CART){
                // 2.2.1 get item and quantity will be added to shopping cart
                if(data != null){
                    Bundle itemBundle = data.getExtras();
                    Item addItem = itemBundle.getParcelable(SEND_ITEM);
                    int quantity = itemBundle.getInt(SEND_QUANTITY);
                    // 2.2.2 add to shopping cart list
                    shoppingCart.addItem(addItem, quantity);
                    // 2.2.3 set choosing state is true
                    isChooseItem = true;
                    Toast.makeText(this, "ADD TO SHOPPING CART", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onSendCurrentCustomer(Customer currCustomer) {
        // call from customer fragment
        // 1. update current customer
        // 2. update shopping cart
        currentCustomer = currCustomer;
        shoppingCart = currentCustomer.getShoppingCart();
        // 3. back to home list to select item
        binding.viewPager.setCurrentItem(MainActivity.HOME_PAGE_ID, false);
        ((HomePageFragment) getSupportFragmentManager().getFragments().get(0)).showItemList();
        Log.d("AAA", "select customer: customer name " + this.shoppingCart.getCustomer().getName() );

//        apiGetItems();
        apiGetOrdersAndItems();
    }

    @Override
    public void onRemoveOrder(int position) {
        orderList.remove(position);
    }

    @Override
    public void onOrderFragmentCreated(OnCreateOrderListener onCreateOrderListener) {
        Log.d("AAA", "hello main from order");
        this.createOrderListener = onCreateOrderListener;
    }

    void apiGetItems(){
        apiService.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()){
                    List<Item> ls = response.body();
                    if(calledApi != null)
                        calledApi.onGetItemsSuccess(ls);
                    Log.d("AAA",ls.get(0).toString());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.d("AAA", "from main: call api " + t.getMessage());
            }
        });
    }

    void apiGetOrdersAndItems(){
        apiService.getOrdersAndItems().enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                if(response.isSuccessful()){
                    ItemsResponse itRp = response.body();
                    orderList.addAll(itRp.orderList);
                    if(calledApi != null){
                        calledApi.onGetItemsSuccess(itRp.itemList);
                    }
                    if(createOrderListener != null)
                        createOrderListener.onOrderCreated(orderList);
                }
            }

            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {
                Log.d("AAA", "from main: call orders + items " + t.getMessage());
            }
        });
    }
}
