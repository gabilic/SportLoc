package hr.foi.air.filtersearch;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.core.EventModel;
import hr.foi.air.core.LocationModel;
import hr.foi.air.core.SportModel;
import hr.foi.air.filtersearch.databinding.FragmentSearchBinding;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private DataModel dataModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        dataModel = getArguments().getParcelable(FilterSearchImpl.DATA_MODEL);
        ButterKnife.bind(this,binding.getRoot());
        setSpinnerData();
        return binding.getRoot();
    }

    private void setSpinnerData() {
        binding.spnCity.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, dataModel.getLocations()));
        binding.spnSport.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, dataModel.getSports()));
    }

    @OnClick(R2.id.btn_search)
    public void search() {
        ArrayList<EventModel> result = new ArrayList<>();
        for (EventModel event : dataModel.getEvents()) {
            if (validateModel(event)) {
                result.add(event);
            }
        }

        FilterSearchImpl.returnResult(result.toArray(new EventModel[0]));
    }

    private boolean validateModel(EventModel event) {
        boolean operator = binding.spnOperator.getSelectedItem().toString().equalsIgnoreCase(getResources().getString(R.string.operator_and));
        boolean andResult = true;
        boolean orResult = false;
        if (((LocationModel) binding.spnCity.getSelectedItem()).getName().equals(event.getCity())) {
            orResult = true;
        } else {
            andResult = false;
        }
        if (((SportModel) binding.spnSport.getSelectedItem()).getName().equals(event.getSport())) {
            orResult = true;
        } else {
            andResult = false;
        }
        if ((binding.spnCapacity.getSelectedItemPosition() == 1 && event.isFull())
                || (binding.spnCapacity.getSelectedItemPosition() == 0 && !event.isFull())) {
            orResult = true;
        } else {
            andResult = false;
        }
        if ((binding.spnOpen.getSelectedItemPosition() == 0 && event.isOpen())
                || (binding.spnOpen.getSelectedItemPosition() == 1 && !event.isOpen())
                || binding.spnOpen.getSelectedItemPosition() == 2) {
            orResult = true;
        } else {
            andResult = false;
        }


        return operator ? andResult : orResult;
    }
//    private EventFilterModel setupFilter() {
//        EventFilterModel filterModel = new EventFilterModel(searchForm.getActiveUserId());
//        if (searchForm.getShowCitySelection()) {
//            filterModel.setCityId(((LocationModel) binding.spnCity.getSelectedItem()).getId());
//        }
//        if (searchForm.getShowSportSelection()) {
//            filterModel.setSportId(((SportModel) binding.spnSport.getSelectedItem()).getId());
//        }
//        //TODO remove hardcoded values
//        if (searchForm.getShowFullSelection()) {
//            int fullSelection = binding.spnCapacity.getSelectedItemPosition();
//            filterModel.setFull(fullSelection == 1);
//        }
//        if (searchForm.getShowOpenSelection()) {
//            int openSelection = binding.spnOpen.getSelectedItemPosition();
//            filterModel.setOpen(openSelection < 2 ? openSelection == 0 : null);
//        }
//        if (searchForm.getShowParticipantSelection()) {
//            int participantSelection = binding.spnParticipant.getSelectedItemPosition();
//            filterModel.setParticipant(participantSelection < 2 ? participantSelection == 0 : null);
//        }
//        return filterModel;
//    }

}
