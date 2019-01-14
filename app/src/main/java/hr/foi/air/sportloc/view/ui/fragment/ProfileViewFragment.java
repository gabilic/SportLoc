package hr.foi.air.sportloc.view.ui.fragment;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hr.foi.air.sportloc.databinding.FragmentProfileViewBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.service.model.ModelEnum;
import hr.foi.air.sportloc.service.model.UserModel;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.view.ui.fragment.ProfileViewFragment;
import hr.foi.air.sportloc.view.ui.fragment.ProfileEditFragment;
import hr.foi.air.sportloc.view.util.Constants;
import hr.foi.air.sportloc.viewmodel.ProfileViewModel;

public class ProfileViewFragment extends Fragment {
    @BindView(R.id.tv_carma_counter)
    TextView tvKarmaCounter;
    @BindView(R.id.btn_change_settings)
    Button btnChangeSettings;
    @BindView(R.id.btn_comment)
    Button btnComment;
    @BindView(R.id.imb_thumbs_up)
    ImageButton btnUpvote;
    @BindView(R.id.imb_thumbs_down)
    ImageButton btnDownvote;

    private Unbinder unbinder;
    //novi kod sa stacka
    private ProfileViewListener listener;
    //-------------
    private UserModel user;
    private boolean upvote = false;
    private boolean downvote = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentProfileViewBinding viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_view, container, false);
        View view = viewBinding.getRoot();
        unbinder = ButterKnife.bind(this, view);

        user = (UserModel) getArguments().get(ModelEnum.UserModel.name());
        //viewBinding povezuje fragment i xml (proslijeduje user)
        viewBinding.setUser(user);

        checkUser();
        setKarmaColor();
        //viewBinding.setUserGender(user);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btn_change_settings)
    public void onEdit(View view) {
        if (listener != null) {
            listener.onOpenProfileEditor();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface ProfileViewListener {
        void onOpenProfileEditor();
    }

    public void setListener(ProfileViewListener listener) {
        this.listener = listener;
    }


    public void setKarmaColor() {
        user = (UserModel) getArguments().get(ModelEnum.UserModel.name());
        int karma = Integer.parseInt(user.getKarma());
        if (karma < 0) {
            //tvKarmaCounter.setBackgroundColor(Color.RED);
            tvKarmaCounter.setBackgroundColor(Color.parseColor("#790000"));
        } else {
            //tvKarmaCounter.setBackgroundResource(R.style.style_karma_positive);
            tvKarmaCounter.setBackgroundColor(Color.parseColor("#006f00"));
        }
    }

    public void checkUser() {
        String username = user.getUsername();
        if (username.equals("stevo(kor)")) { //TU STAVI USERNAME KOJI CES DOBITI OD GABRIJELA
            btnChangeSettings.setVisibility(View.VISIBLE);
            btnComment.setVisibility(View.GONE);
            btnUpvote.setVisibility(View.GONE);
            btnDownvote.setVisibility(View.GONE);
        } else if (!isReloaded()) {
            ProfileViewModel profileViewModel = new ProfileViewModel();
            profileViewModel.getProfile("ibogovic");
            profileViewModel.getProfileObservable().observe(getActivity(), result -> {
                reloadFragment(result);
            });
        } else {
            btnChangeSettings.setVisibility(View.GONE);
            btnComment.setVisibility(View.VISIBLE);
            btnUpvote.setVisibility(View.VISIBLE);
            btnDownvote.setVisibility(View.VISIBLE);
        }
    }

    private boolean isReloaded() {
        return getArguments().getBoolean(Constants.RELOADED);
    }

    @OnClick(R.id.imb_thumbs_up)
    public void ProfileUpvote() {
        upvote = true;
        downvote = false;
    }

    @OnClick(R.id.imb_thumbs_down)
    public void ProfileDownvote() {
        upvote = false;
        downvote = true;
    }

    public void reloadFragment(UserModel user) {

        getArguments().clear();
        getArguments().putBoolean(Constants.RELOADED, true);
        getArguments().putSerializable(ModelEnum.UserModel.name(), user);
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();


    }
}
