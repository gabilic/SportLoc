package hr.foi.air.feelingluckysearch;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.core.EventModel;
import hr.foi.air.feelingluckysearch.databinding.FragmentSearchFeelingLuckyBinding;


public class FeelingLuckySearchFragment extends Fragment {


    private FragmentSearchFeelingLuckyBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_feeling_lucky, container, false);
        ButterKnife.bind(this,binding.getRoot());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R2.id.btn_feeling_lucky)
    public void feelingLucky() {
        List<EventModel> resultList = Arrays.asList((EventModel[]) getArguments().getParcelableArray(FeelingLuckySearchImpl.EVENTS));
        Collections.shuffle(resultList);
        FeelingLuckySearchImpl.returnResult(Arrays.copyOf(resultList.toArray(new EventModel[0]), Integer.valueOf(binding.etSearchResult.getText().toString())));
    }

}
