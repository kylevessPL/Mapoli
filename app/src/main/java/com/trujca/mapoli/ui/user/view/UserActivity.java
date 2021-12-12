package com.trujca.mapoli.ui.user.view;

import static android.view.MotionEvent.ACTION_DOWN;
import static java.util.Objects.requireNonNull;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.ActivityUserBinding;
import com.trujca.mapoli.ui.base.BaseActivity;
import com.trujca.mapoli.ui.user.viewmodel.UserViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserActivity extends BaseActivity<ActivityUserBinding, UserViewModel> {

    @Override
    public Class<UserViewModel> getViewModelClass() {
        return UserViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_user;
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        View currentFocus = getCurrentFocus();
        if (ev.getAction() != ACTION_DOWN || !(currentFocus instanceof EditText)) {
            return super.dispatchTouchEvent(ev);
        }
        Rect outRect = new Rect();
        currentFocus.getGlobalVisibleRect(outRect);
        if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
            currentFocus.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void setup() {
        setupActionBar();
        setupNavController();
    }

    private void setupActionBar() {
        setSupportActionBar(binding.toolbar);
        setTitle(R.string.my_account);
        requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void setupNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.contentUser.navHostUser.getId());
        NavGraph graph = requireNonNull(navHostFragment).getNavController().getGraph();
        if (viewModel.getCurrentUser().getValue() == null) {
            graph.setStartDestination(R.id.nav_login);
        } else {
            graph.setStartDestination(R.id.nav_account);
        }
        requireNonNull(navHostFragment).getNavController().setGraph(graph);
    }
}
