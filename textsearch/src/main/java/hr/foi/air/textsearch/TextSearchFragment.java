package hr.foi.air.textsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.core.EventModel;


public class TextSearchFragment extends Fragment {

    @BindView(R2.id.et_search)
    EditText etSearchQuery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_text, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R2.id.btn_search)
    public void search(){
        ArrayList<EventModel> result = new ArrayList<>();
        EventModel[] events = (EventModel[]) getArguments().getParcelableArray(TextSearchImpl.EVENTS);
        String query = etSearchQuery.getText().toString();
        for (EventModel event : events){
            if(event.containsText(query)){
                result.add(event);
            }
        }
        TextSearchImpl.returnResult(result.toArray(new EventModel[0]));
    }
}
