package com.trujca.mapoli.ui.login.view;

import static android.widget.Toast.LENGTH_LONG;
import static com.trujca.mapoli.data.auth.model.LoginError.INTERNAL_ERROR;

import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.auth.model.LoginError;
import com.trujca.mapoli.databinding.FragmentLoginBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.login.viewmodel.LoginViewModel;
import com.trujca.mapoli.ui.user.viewmodel.UserViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {

    private UserViewModel activityViewModel;

    @Override
    public Class<LoginViewModel> getViewModelClass() {
        return LoginViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    protected void setup() {
        activityViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    protected void updateUI() {
        activityViewModel.getCurrentUser().observe(getViewLifecycleOwner(), this::navigateToAccountFragment);
        viewModel.getSignUpAction().observe(getViewLifecycleOwner(), this::navigateToRegisterFragment);
        viewModel.getSignInError().observe(getViewLifecycleOwner(), this::displaySignInError);
    }

    private void navigateToRegisterFragment(final Boolean result) {
        if (result == null) {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
            Navigation.findNavController(requireView()).navigate(action);
        }
    }

    private void navigateToAccountFragment(final Boolean result) {
        if (result != null) {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToAccountFragment();
            Navigation.findNavController(requireView()).navigate(action);
        }
    }

    private void displaySignInError(final LoginError result) {
        if (result == null) {
            return;
        }
        if (result == INTERNAL_ERROR) {
            Toast.makeText(getContext(), R.string.general_error_message, LENGTH_LONG).show();
            return;
        }
        SignInResultDialogFragment.newInstance(getString(R.string.invalid_credentials)).show(
                getChildFragmentManager(),
                SignInResultDialogFragment.TAG
        );
    }
}