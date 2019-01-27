package hr.foi.air.search.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.adapters.ViewBindingAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.search.BR;
import hr.foi.air.search.DataArrivedHandler;
import hr.foi.air.search.R;
import hr.foi.air.search.R2;
import hr.foi.air.search.databinding.ActivitySearchBinding;
import hr.foi.air.search.model.EventFilterModel;
import hr.foi.air.search.model.SearchFormModel;
import hr.foi.air.search.viewmodel.PicklistViewModel;
import hr.foi.air.search.viewmodel.SearchViewModel;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private DataArrivedHandler dataArrivedHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        binding.setFilter(new EventFilterModel());
        SearchFormModel searchForm = getIntent().getExtras().getParcelable(this.getClass().getName());
        dataArrivedHandler = searchForm.getDataArrivedHandler();
        binding.setSearchForm(searchForm);

        loadSpinnerData(searchForm.getShowCitySelection(), searchForm.getShowSportSelection());

        ButterKnife.bind(this);
//        List<String> strings = new ArrayList<>();
//        strings.add("testttttt");
//        strings.add("aaaaaaa");
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_list_item_1,
//                strings);
//        binding.spnCity.setAdapter(arrayAdapter);
//        binding.set(
//                new SportArrayAdapter(getActivity(),
//                        android.R.layout.simple_spinner_item,
//                        result));

    }

    private void loadSpinnerData(Boolean showCitySelection, Boolean showSportSelection) {
        PicklistViewModel viewModel = ViewModelProviders.of(this).get(PicklistViewModel.class);
        if (showCitySelection) {
            viewModel.getLocations();
            viewModel.getLocationObservable().observe(this, result -> {
                binding.spnCity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, result));
            });
        }
        if (showSportSelection) {
            viewModel.getSports();
            viewModel.getSportsObservable().observe(this, result -> {
                binding.spnSport.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, result));
            });
        }
    }

    @OnClick(R2.id.btn_search)
    public void search() {
        SearchViewModel viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.getEvents(new EventFilterModel());
        viewModel.getEventsObservable().observe(this,result ->{
            dataArrivedHandler.onDataArrived(result);
        });
    }

    @OnClick(R2.id.btn_feeling_lucky)
    public void feelingLucky() {

    }
}
