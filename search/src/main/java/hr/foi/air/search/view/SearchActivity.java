package hr.foi.air.search.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.core.EventModel;
import hr.foi.air.search.R;
import hr.foi.air.search.R2;
import hr.foi.air.search.SearchForm;
import hr.foi.air.search.databinding.ActivitySearchBinding;
import hr.foi.air.search.model.EventFilterModel;
import hr.foi.air.search.model.LocationModel;
import hr.foi.air.search.model.SearchFormModel;
import hr.foi.air.search.model.SportModel;
import hr.foi.air.search.viewmodel.PicklistViewModel;
import hr.foi.air.search.viewmodel.SearchViewModel;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private SearchFormModel searchForm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        searchForm = getIntent().getExtras().getParcelable(this.getClass().getName());
        binding.setSearchForm(searchForm);

        loadSpinnerData();

        ButterKnife.bind(this);
    }

    private void loadSpinnerData() {
        PicklistViewModel viewModel = ViewModelProviders.of(this).get(PicklistViewModel.class);
        if (searchForm.getShowCitySelection()) {
            viewModel.getLocations();
            viewModel.getLocationObservable().observe(this, result -> {
                binding.spnCity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, result));
            });
        }
        if (searchForm.getShowSportSelection()) {
            viewModel.getSports();
            viewModel.getSportsObservable().observe(this, result -> {
                binding.spnSport.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, result));
            });
        }
    }

    @OnClick(R2.id.btn_search)
    public void search() {
        SearchViewModel viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        EventFilterModel filterModel = setupFilter();
        viewModel.getEvents(filterModel);
        viewModel.getEventsObservable().observe(this, result -> {
            SearchForm.dataArrived(result);
        });
    }

    private EventFilterModel setupFilter() {
        EventFilterModel filterModel = new EventFilterModel(searchForm.getActiveUserId());
        if (searchForm.getShowCitySelection()) {
            filterModel.setCityId(((LocationModel) binding.spnCity.getSelectedItem()).getId());
        }
        if (searchForm.getShowSportSelection()) {
            filterModel.setSportId(((SportModel) binding.spnSport.getSelectedItem()).getId());
        }
        //TODO remove hardcoded values
        if (searchForm.getShowFullSelection()) {
            int fullSelection = binding.spnCapacity.getSelectedItemPosition();
            filterModel.setFull(fullSelection == 1);
        }
        if (searchForm.getShowOpenSelection()) {
            int openSelection = binding.spnOpen.getSelectedItemPosition();
            filterModel.setOpen(openSelection < 2 ? openSelection == 0 : null);
        }
        if (searchForm.getShowParticipantSelection()) {
            int participantSelection = binding.spnOpen.getSelectedItemPosition();
            filterModel.setParticipant(participantSelection < 2 ? participantSelection == 0 : null);
        }
        return filterModel;
    }

    @OnClick(R2.id.btn_feeling_lucky)
    public void feelingLucky() {
        SearchViewModel viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.getEvents(new EventFilterModel());
        viewModel.getEventsObservable().observe(this, result -> {
            if (result != null && result.length > 0) {
                SearchForm.dataArrived(getRandomData(result));
            } else {
                SearchForm.dataArrived(null);
            }
        });
    }

    private EventModel[] getRandomData(EventModel[] result) {
        List<EventModel> resultList = Arrays.asList(result);
        Collections.shuffle(resultList);
        return Arrays.copyOf(resultList.toArray(new EventModel[0]), 5);
    }
}
