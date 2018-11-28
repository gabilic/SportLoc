package hr.foi.air.sportloc.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hr.foi.air.sportloc.R;

public class EventMembersFragment extends Fragment {

    @BindView(R.id.sv_event_members)
    ScrollView scrollView;

    private Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_members, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
