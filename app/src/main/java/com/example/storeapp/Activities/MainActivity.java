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
import com.example.storeapp.Models.Order;
import com.example.storeapp.Models.ShoppingCart;
import com.example.storeapp.R;
import com.example.storeapp.ViewModels.MainAdapter;
import com.example.storeapp.databinding.MainLayoutBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements CustomerFragment.OnSelectCustomerListener, OrderFragment.OnOrderClickListener {

    public static final int CHOOSE_ITEM = 10000;
    public static final int ITEM_DETAIL = 10001;
    public static final int SHOPPING_CART = 10002;
    public static final String SEND_ITEM = "send item";
    public static final String SEND_QUANTITY = "send quantity";
    public static final String SEND_CUSTOMER = "send customer";
    public static final String SEND_CREATE_ORDER = "send created order";
    public static final String SEND_ORDER_LIST = "send order list";
    public static final String SEND_SHOPPING_CART = "send shopping cart";
    public static final int HOME_PAGE_ID = 0;
    public static final int ORDER_PAGE_ID = 1;
    public static final int CUSTOMER_PAGE_ID = 2;
    private MainLayoutBinding binding = null;
    private String[] titles = {
            "Trang chủ", "Đơn hàng", "Khách hàng"
    };
    public static final String ITEM_CHOOSING = "item choosing";
    private boolean IS_CHOOSE_ITEM = false;
    private final int[] icon_pager = {
            R.drawable.ic_home_page,
            R.drawable.ic_menu,
            R.drawable.ic_customer
    };

    private Customer currentCustomer = null;
    private ShoppingCart shoppingCart = null;
    private ArrayList<Order> orderList = null;
    private OnCreateOrderListener createOrderListener = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 0. create list
        orderList = new ArrayList<>();

        // 1. Create page view Adapter
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), getLifecycle());

        // 2. update adapter to viewpager
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setTitle(titles[position]);
            }
        });

        // 3. update icon and content to tab layout
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                ((tab, position) -> {
                    tab.setText(titles[position]);
                    tab.setIcon(icon_pager[position]);
                })).attach();
    }

    public ViewPager2 getViewPager(){
        return binding.viewPager;
    }
    public boolean getIsChooseItem(){
        return this.IS_CHOOSE_ITEM;
    }
    public void setCreateOrderListener(OnCreateOrderListener createOrderListener) {
        Log.d("AAA", "hello createOrderListener " + createOrderListener);
        this.createOrderListener = createOrderListener;
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
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
                return true;
            case R.id.shopping_cart:
                Intent intentShopping = new Intent(this, ShoppingCartActivity.class);
                Bundle shoppingCartBundle = new Bundle();
                shoppingCartBundle.putParcelable(SEND_SHOPPING_CART, shoppingCart);
                shoppingCartBundle.putBoolean(ITEM_CHOOSING, IS_CHOOSE_ITEM);
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
            binding.viewPager.setCurrentItem(HOME_PAGE_ID, false);
            if(resultCode == SHOPPING_CART){
                if(data != null) {
                    Bundle bundle = data.getExtras();
                    IS_CHOOSE_ITEM = bundle.getBoolean(ITEM_CHOOSING);
                    shoppingCart = bundle.getParcelable(SEND_SHOPPING_CART);
                    Order newOrder = bundle.getParcelable(SEND_CREATE_ORDER);
                    if(newOrder != null){
                        orderList.add(newOrder);
                        if(createOrderListener != null)
                            createOrderListener.onOrderCreated(orderList);

                        Log.d("AAA", "from main: new order "+ newOrder.toString());
                    }
                    Log.d("AAA", "size order list: " + Integer.toString(orderList.size()));
                }
            }
        }
        else if(requestCode == ITEM_DETAIL){
            if(resultCode == ItemDetailActivity.CREATE_ORDER){
                // 1. back to home to continue choosing item
                binding.viewPager.setCurrentItem(HOME_PAGE_ID, false);
                Toast.makeText(this, "CREATE", Toast.LENGTH_SHORT).show();
            }
            if(resultCode == ItemDetailActivity.ADD_TO_SHOPPING_CART){
                // 2.1 get item and quantity will be added to shopping cart
                if(data != null){
                    Bundle itemBundle = data.getExtras();
                    Item addItemm = itemBundle.getParcelable(SEND_ITEM);
                    int quantity = itemBundle.getInt(SEND_QUANTITY);
                    // 2.2 add to shopping cart list
                    Toast.makeText(this, addItemm.getName(), Toast.LENGTH_SHORT).show();
                    shoppingCart.addItem(addItemm, quantity);
                    // 2.3 set choosing state is true
                    IS_CHOOSE_ITEM = true;
                    // 2.4 back to home continue choos item
                    binding.viewPager.setCurrentItem(HOME_PAGE_ID, false);
                    Toast.makeText(this, "ADD", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onSendCurrentCustomer(Customer currCustomer) {
        currentCustomer = currCustomer;
        shoppingCart = currentCustomer.getShoppingCart();
        binding.viewPager.setCurrentItem(MainActivity.HOME_PAGE_ID, false);
        ((HomePageFragment) getSupportFragmentManager().getFragments().get(0)).showItemList();
        Log.d("AAA", "customer name " + this.shoppingCart.getCustomer().getName() );
    }

    @Override
    public void onRemoveOrder(int position) {
        orderList.remove(position);
    }
}
