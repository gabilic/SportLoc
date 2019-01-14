package hr.foi.air.sportloc.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import hr.foi.air.sportloc.R;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import hr.foi.air.sportloc.databinding.FragmentProfileViewBinding;
import hr.foi.air.sportloc.databinding.FragmentProfileEditBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.service.model.ModelEnum;
import hr.foi.air.sportloc.service.model.UserModel;


public class ProfileEditFragment extends Fragment {
    @BindView(R.id.et_password)
    EditText etPassword;

    private Unbinder unbinder;
    private ProfileEditListener listener;
    private boolean passwordVisible=false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentProfileEditBinding viewBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_profile_edit, container, false);
        View view=viewBinding.getRoot();
        unbinder=ButterKnife.bind(this,view);

        UserModel user = (UserModel) getArguments().get(ModelEnum.UserModel.name());
        viewBinding.setUser(user);

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
    public void cancelSettingsChange(View view){
            if(listener!=null){
                listener.onOpenProfileView();
            }
    }

    @OnClick(R.id.btn_save)
    public void saveSettingsChange(View view){
        if(listener!=null){
            listener.onOpenProfileView();
        }
    }

    public interface ProfileEditListener{
        void onOpenProfileView();
    }

    public void setListener(ProfileEditListener listener){
        this.listener=listener;
    }



    @OnClick(R.id.sw_show_password)
    public void showPassword(){
        if(passwordVisible==false){
            etPassword.setTransformationMethod(null);
            passwordVisible=true;
        }
        else{
            etPassword.setTransformationMethod(new PasswordTransformationMethod());
            passwordVisible=false;
        }
    }
}
