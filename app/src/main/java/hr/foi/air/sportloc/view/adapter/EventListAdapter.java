package hr.foi.air.sportloc.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import hr.foi.air.sportloc.databinding.EventListItemBinding;
import hr.foi.air.sportloc.service.model.EventModel;
import hr.foi.air.sportloc.view.util.OnEventClickListener;
import hr.foi.air.sportloc.view.util.OnEventDetailsClickListener;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private ArrayList<EventModel> eventList;
    private OnEventClickListener eventListener;
    private OnEventDetailsClickListener detailsListener;
    private static int lastExpandedPosition = -1;

    public EventListAdapter(ArrayList<EventModel> eventList, OnEventClickListener eventListener,
                            OnEventDetailsClickListener detailsListener) {

        this.eventList = eventList;
        this.eventListener = eventListener;
        this.detailsListener = detailsListener;
    }

    public static int getLastExpandedPosition() {
        return lastExpandedPosition;
    }

    public static void setLastExpandedPosition(int lastExpandedPosition) {
        EventListAdapter.lastExpandedPosition = lastExpandedPosition;
    }

    public ArrayList<EventModel> getEventList() {
        return eventList;
    }

    @Override
    @NonNull
    public EventListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        EventListItemBinding binding = EventListItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventModel event = eventList.get(position);
        holder.bind(event, position, eventListener, detailsListener);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final EventListItemBinding binding;

        private ViewHolder(EventListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(EventModel event, int position, OnEventClickListener eventListener,
                         OnEventDetailsClickListener detailsListener) {

            binding.setEvent(event);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(v -> eventListener.onItemClick(position));
            binding.btnDetails.setOnClickListener(v -> detailsListener.onItemClick(event));
        }
    }
}
