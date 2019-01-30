package hr.foi.air.sportloc.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.core.EventModel;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.service.model.ActiveUserModel;
import hr.foi.air.sportloc.service.model.ModelEnum;
import hr.foi.air.sportloc.service.model.ParticipantModel;
import hr.foi.air.sportloc.view.util.Constants;

import static hr.foi.air.sportloc.view.util.Constants.EVENT_PARTICIPANTS;

public class EventMembersFragment extends android.support.v4.app.Fragment {
    @BindView(R.id.sv_event_members)
    ScrollView scrollView;

    @BindView(R.id.lvApproved)
    ListView lvApproved;

    @BindView(R.id.lvPending)
    ListView lvPending;

    @BindView(R.id.lvBlocked)
    ListView lvBlocked;

    @BindView(R.id.tvBlocked)
    TextView tvBlocked;

    @BindView(R.id.tvPending)
    TextView tvPending;

    private EventModel event;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        event = getArguments().getParcelable(ModelEnum.EventModel.name());
        View view = inflater.inflate(R.layout.fragment_event_members, container, false);
        ButterKnife.bind(this, view);
        if (event != null) {
            showParticipants();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void showParticipants() {
        List<ParticipantModel> list = getArguments().getParcelableArrayList(EVENT_PARTICIPANTS);
        List<ParticipantModel> approvedUsers = new ArrayList<>();
        List<ParticipantModel> pendingUsers = new ArrayList<>();
        List<ParticipantModel> blockedUsers = new ArrayList<>();

        for (ParticipantModel participant : list) {
            switch (participant.getStatus()) {
                case Constants.APPROVED:
                    approvedUsers.add(participant);
                    break;
                case Constants.PENDING:
                    pendingUsers.add(participant);
                    break;
                case Constants.BLOCKED:
                    blockedUsers.add(participant);
                    break;
            }
        }

        //move this logic to databinding
        if (event.getUsername().equals(ActiveUserModel.getInstance().getActiveUser().getUsername())) {
            tvBlocked.setVisibility(View.VISIBLE);
            tvPending.setVisibility(View.VISIBLE);
            lvPending.setVisibility(View.VISIBLE);
            lvBlocked.setVisibility(View.VISIBLE);

            ArrayAdapter arrayAdapterApproved = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, approvedUsers);
            lvApproved.setAdapter(arrayAdapterApproved);

            ArrayAdapter arrayAdapterPending = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, pendingUsers);
            lvPending.setAdapter(arrayAdapterPending);

            ArrayAdapter arrayAdapterBlocked = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, blockedUsers);
            lvBlocked.setAdapter(arrayAdapterBlocked);
        } else {

            ArrayAdapter arrayAdapterApproved = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, approvedUsers);
            lvApproved.setAdapter(arrayAdapterApproved);

            tvBlocked.setVisibility(View.GONE);
            tvPending.setVisibility(View.GONE);
            lvPending.setVisibility(View.GONE);
            lvBlocked.setVisibility(View.GONE);
        }
    }

}

