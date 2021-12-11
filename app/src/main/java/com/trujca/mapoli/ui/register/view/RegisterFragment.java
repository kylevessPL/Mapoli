package com.trujca.mapoli.ui.register.view;

import static android.widget.Toast.LENGTH_LONG;
import static com.trujca.mapoli.data.auth.model.RegisterError.INTERNAL_ERROR;

import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.auth.model.RegisterError;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.databinding.FragmentRegisterBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.register.viewmodel.RegisterViewModel;
import com.trujca.mapoli.ui.user.viewmodel.UserViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterViewModel> {

    private UserViewModel activityViewModel;

    @Override
    public Class<RegisterViewModel> getViewModelClass() {
        return RegisterViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_register;
    }

    @Override
    protected int getTitle() {
        return R.string.signup;
    }

    @Override
    protected void setup() {
        activityViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    protected void updateUI() {
        activityViewModel.getCurrentUser().observe(getViewLifecycleOwner(), this::navigateToAccountFragment);
        viewModel.getSignInAction().observe(getViewLifecycleOwner(), this::navigateToLoginFragment);
        viewModel.getSignUpError().observe(getViewLifecycleOwner(), this::displaySignUpError);
        viewModel.getSignUpSuccess().observe(getViewLifecycleOwner(), this::displaySignUpSuccess);
    }

    private void navigateToLoginFragment(final Boolean result) {
        if (result != null) {
            NavDirections action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment();
            Navigation.findNavController(requireView()).navigate(action);
        }
    }

    private void navigateToAccountFragment(final UserDetails userDetails) {
        if (userDetails != null) {
            NavDirections action = RegisterFragmentDirections.actionRegisterFragmentToAccountFragment();
            Navigation.findNavController(requireView()).navigate(action);
        }
    }

    private void displaySignUpError(final RegisterError result) {
        if (result == null) {
            return;
        }
        if (result == INTERNAL_ERROR) {
            Toast.makeText(getContext(), R.string.general_error_message, LENGTH_LONG).show();
            return;
        }
        SignUpResultDialogFragment.newInstance(getString(R.string.email_in_use), false).show(
                getChildFragmentManager(),
                SignUpResultDialogFragment.TAG
        );
    }

    private void displaySignUpSuccess(final UserDetails result) {
        if (result != null) {
            SignUpResultDialogFragment.newInstance(getString(R.string.signup_success), true)
                    .show(getChildFragmentManager(), SignUpResultDialogFragment.TAG);
        }
    }
}