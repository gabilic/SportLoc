package hr.foi.air.sportloc.view.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.view.ui.fragment.ProfileViewFragment;
import hr.foi.air.sportloc.view.ui.fragment.ProfileEditFragment;

public class ProfileActivity extends AppCompatActivity implements ProfileViewFragment.ProfileViewListener, ProfileEditFragment.ProfileEditListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ovo ispod je za proslijedivanje iz activita u fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ProfileViewFragment profileViewFragment = new ProfileViewFragment();
        ProfileEditFragment profileEditFragment=new ProfileEditFragment();
        profileViewFragment.setArguments(getIntent().getExtras());
        profileEditFragment.setArguments(getIntent().getExtras());


        //getSupportFragmentManager().beginTransaction().add(R.id.profile_fragment, profileEditFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.profile_fragment, profileViewFragment).commit();
        //getSupportFragmentManager().beginTransaction().replace(R.id.profile_fragment, profileViewFragment).commit();

        //FragmentManager fm=getSupportFragmentManager();
        //fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).show(new ProfileViewFragment()).commit();
        //fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).show(new ProfileEditFragment()).commit();

        //-----------------------------------
    }

    @Override
    public void onOpenProfileEditor() {
        ProfileEditFragment profileEditFragment=new ProfileEditFragment();
        profileEditFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().replace(R.id.profile_fragment, profileEditFragment).commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof ProfileViewFragment) {
            ProfileViewFragment profileFragment = (ProfileViewFragment) fragment;
            profileFragment.setListener(this::onOpenProfileEditor);
        }

        if (fragment instanceof ProfileEditFragment){
            ProfileEditFragment profileEditFragment = (ProfileEditFragment) fragment;
            profileEditFragment.setListener(this::onOpenProfileView);
        }
    }
    //NOVO-----------------------------------------
    @Override
    public void onOpenProfileView(){
        ProfileViewFragment profileViewFragment=new ProfileViewFragment();
        profileViewFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.profile_fragment, profileViewFragment).commit();
    }

}
