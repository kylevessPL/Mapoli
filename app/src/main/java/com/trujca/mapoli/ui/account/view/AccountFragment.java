package com.trujca.mapoli.ui.account.view;

import static android.widget.Toast.LENGTH_LONG;

import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.FragmentAccountBinding;
import com.trujca.mapoli.ui.account.viewmodel.AccountViewModel;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.user.viewmodel.UserViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AccountFragment extends BaseFragment<FragmentAccountBinding, AccountViewModel> {

    private UserViewModel activityViewModel;

    @Override
    public Class<AccountViewModel> getViewModelClass() {
        return AccountViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_account;
    }

    @Override
    protected void setup() {
        activityViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    protected void updateUI() {
        activityViewModel.getCurrentUser().observe(getViewLifecycleOwner(), this::navigateToLoginFragment);
        viewModel.getSignOutAction().observe(getViewLifecycleOwner(), this::displaySignOutConfirmationDialog);
        viewModel.getSignOutResult().observe(getViewLifecycleOwner(), this::displaySignOutResult);
    }

    private void navigateToLoginFragment(final Boolean result) {
        if (result == null) {
            NavDirections action = AccountFragmentDirections.actionAccountFragmentToLoginFragment();
            Navigation.findNavController(requireView()).navigate(action);
        }
    }

    private void displaySignOutConfirmationDialog(final Boolean result) {
        if (result != null) {
            new SignOutConfirmationDialogFragment().show(
                    getChildFragmentManager(),
                    SignOutConfirmationDialogFragment.TAG
            );
        }
    }

    private void displaySignOutResult(final Boolean result) {
        if (result != null) {
            Toast.makeText(getContext(), getString(R.string.general_error_message), LENGTH_LONG)
                    .show();
        }
    }
}