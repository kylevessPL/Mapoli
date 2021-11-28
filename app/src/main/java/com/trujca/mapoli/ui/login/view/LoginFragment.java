package com.trujca.mapoli.ui.login.view;

import static android.widget.Toast.LENGTH_LONG;
import static com.trujca.mapoli.data.auth.model.LoginError.INTERNAL_ERROR;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.trujca.mapoli.R;
import com.trujca.mapoli.data.auth.model.LoginError;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.databinding.FragmentLoginBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.login.viewmodel.LoginViewModel;
import com.trujca.mapoli.ui.user.viewmodel.UserViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {

    @Inject
    protected GoogleSignInClient client;
    ActivityResultLauncher<Intent> googleLoginLauncher = registerForActivityResult(new StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                viewModel.signInWithGoogle(account.getIdToken());
            } catch (ApiException ignored) {
            }
        }
    });
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
    protected int getTitle() {
        return R.string.signin;
    }

    @Override
    protected void setup() {
        activityViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    protected void updateUI() {
        activityViewModel.getCurrentUser().observe(getViewLifecycleOwner(), this::navigateToAccountFragment);
        viewModel.getSignInAction().observe(getViewLifecycleOwner(), this::launchGoogleLoginLauncher);
        viewModel.getSignUpAction().observe(getViewLifecycleOwner(), this::navigateToRegisterFragment);
        viewModel.getSignInError().observe(getViewLifecycleOwner(), this::displaySignInError);
    }

    private void launchGoogleLoginLauncher(final Boolean result) {
        if (result != null) {
            googleLoginLauncher.launch(client.getSignInIntent());
        }
    }

    private void navigateToRegisterFragment(final Boolean result) {
        if (result != null) {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
            Navigation.findNavController(requireView()).navigate(action);
        }
    }

    private void navigateToAccountFragment(final UserDetails userDetails) {
        if (userDetails != null) {
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