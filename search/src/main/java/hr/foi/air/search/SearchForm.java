package hr.foi.air.search;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

import hr.foi.air.core.DataArrivedHandler;
import hr.foi.air.search.model.SearchFormModel;
import hr.foi.air.search.model.WebServiceCaller;
import hr.foi.air.search.view.SearchActivity;

public class SearchForm implements Serializable {

    private SearchFormModel model;
    private static DataArrivedHandler dataArrivedHandler;
    private String serviceUrl;

    private SearchForm(SearchFormBuilder builder) {
        serviceUrl = builder.serviceUrl;
        model = new SearchFormModel(builder.activeUserId);
        model.setShowCitySelection(builder.showCitySelection);
        model.setShowSportSelection(builder.showSportSelection);
        model.setShowFullSelection(builder.showFullSelection);
        model.setShowOpenSelection(builder.showOpenSelection);
        model.setShowParticipantSelection(builder.showParticipantSelection);
        dataArrivedHandler = builder.dataArrivedHandler;
    }

    public static class SearchFormBuilder {
        private String serviceUrl;
        private Integer activeUserId;
        private DataArrivedHandler dataArrivedHandler;

        private Boolean showSportSelection = false;
        private Boolean showCitySelection = false;
        private Boolean showFullSelection = false;
        private Boolean showParticipantSelection = false;
        private Boolean showOpenSelection = false;

        public SearchFormBuilder(String serviceUrl, Integer activeUserId, DataArrivedHandler dataArrivedHandler) {
            this.serviceUrl = serviceUrl;
            this.activeUserId = activeUserId;
            this.dataArrivedHandler = dataArrivedHandler;
        }

        public SearchFormBuilder setShowAllOptions(Boolean showAllOptions) {
            this.showSportSelection = showAllOptions;
            this.showCitySelection = showAllOptions;
            this.showFullSelection = showAllOptions;
            this.showParticipantSelection = showAllOptions;
            this.showOpenSelection = showAllOptions;
            return this;
        }

        public SearchFormBuilder setShowSportSelection(Boolean showSportSelection) {
            this.showSportSelection = showSportSelection;
            return this;
        }

        public SearchFormBuilder setShowCitySelection(Boolean showCitySelection) {
            this.showCitySelection = showCitySelection;
            return this;
        }

        public SearchFormBuilder setShowFullSelection(Boolean showFullSelection) {
            this.showFullSelection = showFullSelection;
            return this;
        }

        public SearchFormBuilder setShowParticipantSelection(Boolean showParticipantSelection) {
            this.showParticipantSelection = showParticipantSelection;
            return this;
        }

        public SearchFormBuilder setShowOpenSelection(Boolean showOpenSelection) {
            this.showOpenSelection = showOpenSelection;
            return this;
        }

        public SearchForm build() {
            return new SearchForm(this);
        }
    }

    public void showSearchActivity(Context context) {
        WebServiceCaller.getInstance().setupRetrofit(serviceUrl);
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(SearchActivity.class.getName(), model);
        //intent.putExtra(DataArrivedHandler.class.getName(), dataArrivedHandler);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void dataArrived(Object[] result) {
        dataArrivedHandler.onDataArrived(result);
    }

}
