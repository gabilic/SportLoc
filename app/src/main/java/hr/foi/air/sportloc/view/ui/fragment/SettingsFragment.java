package hr.foi.air.sportloc.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import hr.foi.air.core.EventSearch;
import hr.foi.air.core.SearchAnnotation;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.service.serviceUtil.DataUtil;
import hr.foi.air.sportloc.view.util.MessageSender;
import hr.foi.air.sportloc.view.util.SearchSettingsEnum;

@SearchAnnotation(name = "test")
public class SettingsFragment extends Fragment {

    @BindView(R.id.lv_plugins)
    ListView pluginsListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, SearchSettingsEnum.values());
        pluginsListView.setAdapter(arrayAdapter);
    }


    @OnItemClick(R.id.lv_plugins)
    public void onRowClick(AdapterView<?> parent, View view, int position, long id) {
        SearchSettingsEnum selectedSetting = SearchSettingsEnum.getByName(parent.getItemAtPosition(position).toString());
        try {
            EventSearch search = (EventSearch) Class.forName(selectedSetting.getPath()).newInstance();
            DataUtil.getInstance().setEventSearch(search);

            MessageSender.sendMessage(getActivity().getApplicationContext(),  getResources().getString(R.string.search_settings_selected) + selectedSetting);
        } catch (ClassNotFoundException | IllegalAccessException | java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

//    public <T> List<Class<? extends T>> findAllMatchingTypes(Class<T> toFind) {
//        List<Class<?>> foundClasses = new ArrayList<Class<?>>();
//        List<Class<? extends T>> returnedClasses = new ArrayList<Class<? extends T>>();
//        //this.toFind = toFind;
//        //walkClassPath();
//        for (Class<?> clazz : foundClasses) {
//            returnedClasses.add((Class<? extends T>) clazz);
//        }
//        return returnedClasses;
//    }
}
