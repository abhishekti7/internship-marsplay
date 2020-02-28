package internship.marsplay;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private int currentFragment = -1;
    private ConstraintLayout loader_layout;
    private FrameLayout frameLayout;
    private LottieAnimationView error_loader;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static final int JOURNAL_LIST_FRAGMENT= 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.main_framelayout);
        loader_layout = findViewById(R.id.loader_layout);
        swipeRefreshLayout = findViewById(R.id.loader_swiperefresh);
        error_loader = findViewById(R.id.loader);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        checkInternetConnection();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkInternetConnection();
            }
        });

        gotoFragment(new JournalListFragment(), JOURNAL_LIST_FRAGMENT);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
    }

    private void checkInternetConnection(){
        swipeRefreshLayout.setRefreshing(false);
        if(isNetworkAvailable()){
            frameLayout.setVisibility(View.VISIBLE);
            loader_layout.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.GONE);
            setFragment(new JournalListFragment(), JOURNAL_LIST_FRAGMENT);
        }else{
            error_loader.setProgress(0);
            error_loader.addAnimatorListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) {
                    error_loader.setProgress(0);
                }
            });
            frameLayout.setVisibility(View.GONE);
            loader_layout.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    public void gotoFragment(Fragment fragment, int fragment_no){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setLogo(R.drawable.marsplay);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        invalidateOptionsMenu();
        setFragment(fragment, fragment_no);
    }

    private void setFragment(Fragment fragment, int fragmentNo){
        if(fragmentNo!=currentFragment) {
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);

        MenuItem searchItem = menu.findItem(R.id.search_menu);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                JournalListFragment.journalListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
