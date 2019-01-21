package hr.foi.air.sportloc.view.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.databinding.FragmentProfileEditBinding;
import hr.foi.air.sportloc.service.model.ActiveUserModel;
import hr.foi.air.sportloc.service.model.ModelEnum;
import hr.foi.air.sportloc.service.model.UserModel;
import hr.foi.air.sportloc.view.util.Constants;
import hr.foi.air.sportloc.view.util.MessageSender;
import hr.foi.air.sportloc.viewmodel.ProfileViewModel;


public class ProfileEditFragment extends Fragment {

    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_surname)
    EditText etSurname;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_personal_information)
    EditText etPersonalInformation;

    private Unbinder unbinder;
    private ProfileEditListener listener;
    private boolean passwordVisible = false;
    int userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentProfileEditBinding viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_edit, container, false);
        View view = viewBinding.getRoot();
        unbinder = ButterKnife.bind(this, view);

        UserModel user = (UserModel) getArguments().get(ModelEnum.UserModel.name());
        viewBinding.setUser(user);

        etName.setText(user.getName());
        etSurname.setText(user.getSurname());
        etEmail.setText(user.getEmail());
        etPersonalInformation.setText(user.getDescription());
        userID = user.getUserId();

        String username=ActiveUserModel.getInstance().getActiveUser().getUsername();
        if (!isReloaded()) {
            ProfileViewModel profileViewModel = new ProfileViewModel();
            profileViewModel.getProfile(username);
            profileViewModel.getProfileObservable().observe(getActivity(), result -> {
                reloadFragment(result);
            });
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_cancel)
    public void cancelSettingsChange(View view) {
        if (listener != null) {
            listener.onOpenProfileView();
        }
    }

    @OnClick(R.id.btn_save)
    public void saveSettingsChange(View view) {
        UserModel user = new UserModel();
        user.setName(etName.getText().toString());
        user.setSurname(etSurname.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setDescription(etPersonalInformation.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setUserId(userID);
        observeProfileUpdate(user);
    }

    private void observeProfileUpdate(UserModel user) {
        boolean success=true;
        String passwordLength= etPassword.getText().toString();
        String nameLength=etName.getText().toString();
        String surnameLength=etSurname.getText().toString();
        int passwordLengthNumber = passwordLength.length();
        int nameLengthNumber=nameLength.length();
        int surnameLengthNumber=surnameLength.length();

        if (passwordLengthNumber < 6) {
            MessageSender.sendError(getContext(), getResources().getString(R.string.profile_password_count));
            success=false;
        }
        if(nameLengthNumber==0){
            MessageSender.sendError(getContext(), getResources().getString(R.string.profile_name_empty));
            success=false;
        }
        if(surnameLengthNumber==0){
            MessageSender.sendError(getContext(), getResources().getString(R.string.profile_surname_empty));
            success=false;
        }
        if(success==true){
            ProfileViewModel profileViewModel = new ProfileViewModel();
            profileViewModel.updateProfile(user);
            profileViewModel.getProfileUpdateObservable().observe(this, result -> {
                if (result != null) {
                    if (result) {
                        MessageSender.sendMessage(getContext(), getResources().getString(R.string.profile_edit_complete));

                                if(listener!=null){
                                   listener.onOpenProfileView(); }

                    } else {
                        MessageSender.sendError(getContext(), getResources().getString(R.string.profile_edit_failure));
                    }
                } else {
                    MessageSender.sendError(getContext(), getResources().getString(R.string.profile_edit_error));
                }
            });
        }
    }

    public interface ProfileEditListener {
        void onOpenProfileView();
    }

    public void setListener(ProfileEditListener listener) {
        this.listener = listener;
    }


    @OnClick(R.id.sw_show_password)
    public void showPassword() {
        if (passwordVisible == false) {
            etPassword.setTransformationMethod(null);
            passwordVisible = true;
        } else {
            etPassword.setTransformationMethod(new PasswordTransformationMethod());
            passwordVisible = false;
        }
    }
    public void reloadFragment(UserModel user) {

        getArguments().clear();
        getArguments().putBoolean(Constants.RELOADED, true);
        getArguments().putSerializable(ModelEnum.UserModel.name(), user);
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();


    }
    private boolean isReloaded() {
        return getArguments().getBoolean(Constants.RELOADED);
    }
}
