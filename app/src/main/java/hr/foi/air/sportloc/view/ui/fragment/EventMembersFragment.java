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
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.service.model.ActiveUserModel;
import hr.foi.air.sportloc.service.model.EventModel;
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
        showParticipants();
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
        ParticipantModel participant;
        for (int i = 0; i < list.size(); i++) {
            participant = list.get(i);
            if (!participant.getUsername().equals(ActiveUserModel.getInstance().getActiveUser().getUsername())) {
                if (participant.getStatus().equals(Constants.APPROVED)) {
                    approvedUsers.add(participant);
                }
                if (participant.getStatus().equals(Constants.PENDING)) {
                    pendingUsers.add(participant);
                }
                if (participant.getStatus().equals(Constants.BLOCKED)) {
                    blockedUsers.add(participant);
                }
            }
        }

        if (event.getUsername().equals(ActiveUserModel.getInstance().getActiveUser().getUsername())) {
            tvBlocked.setVisibility(View.VISIBLE);
            tvPending.setVisibility(View.VISIBLE);
            lvPending.setVisibility(View.VISIBLE);
            lvBlocked.setVisibility(View.VISIBLE);

            String approvedArray[] = new String[approvedUsers.size()];
            for (int i = 0; i < approvedUsers.size(); i++) {
                ParticipantModel approved;
                approved = approvedUsers.get(i);
                approvedArray[i] = approved.getUsername();
            }
            ArrayAdapter arrayAdapterApproved = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, approvedArray);
            lvApproved.setAdapter(arrayAdapterApproved);

            String pendingArray[] = new String[pendingUsers.size()];
            for (int i = 0; i < pendingUsers.size(); i++) {
                ParticipantModel pending;
                pending = pendingUsers.get(i);
                pendingArray[i] = pending.getUsername();
            }
            ArrayAdapter arrayAdapterPending = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, pendingArray);
            lvPending.setAdapter(arrayAdapterPending);

            String blockedArray[] = new String[blockedUsers.size()];
            for (int i = 0; i < blockedUsers.size(); i++) {
                ParticipantModel blocked;
                blocked = blockedUsers.get(i);
                blockedArray[i] = blocked.getUsername();
            }
            ArrayAdapter arrayAdapterBlocked = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, blockedArray);
            lvBlocked.setAdapter(arrayAdapterBlocked);
        }
        else {
            String approvedArray[] = new String[approvedUsers.size()];
            for (int i = 0; i < approvedUsers.size(); i++) {
                ParticipantModel approved;
                approved = approvedUsers.get(i);
                approvedArray[i] = approved.getUsername();
            }

            ArrayAdapter arrayAdapterApproved = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, approvedArray);
            lvApproved.setAdapter(arrayAdapterApproved);

            tvBlocked.setVisibility(View.GONE);
            tvPending.setVisibility(View.GONE);
            lvPending.setVisibility(View.GONE);
            lvBlocked.setVisibility(View.GONE);
        }
    }

}

