package hr.foi.air.sportloc.view.ui.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import hr.foi.air.sportloc.R;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hr.foi.air.sportloc.service.model.CommentModel;
import hr.foi.air.sportloc.service.model.ModelEnum;
import hr.foi.air.sportloc.service.model.UserModel;
import hr.foi.air.sportloc.view.ui.activity.ProfileActivity;
import hr.foi.air.sportloc.view.util.Constants;
import hr.foi.air.sportloc.view.util.IntentManager;
import hr.foi.air.sportloc.viewmodel.CommentsViewModel;
import hr.foi.air.sportloc.databinding.FragmentCommentListBinding;


public class CommentListFragment extends Fragment {
    private Unbinder unbinder;
    private CommentsListViewListener listener;
    private int id;

    @BindView(R.id.lv_comment)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCommentListBinding viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment_list, container, false);
        View view = viewBinding.getRoot();
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        id = bundle.getInt(Constants.USERID);
        showComments(id);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public interface CommentsListViewListener {
        void onOpenCommentsList();
    }

    public void setListener(CommentsListViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showComments(Integer id) {
        CommentsViewModel commentsViewModel = new CommentsViewModel();
        commentsViewModel.getComments(id);

        commentsViewModel.getCommentsObservable().observe(getActivity(), result -> {
            List<CommentModel> list = result;

            String usernameListArray[] = new String[list.size()];

            for (int i = 0; i < list.size(); i++) {
                CommentModel comment;
                comment = list.get(i);
                String commentatorUsername = comment.getCommentator();
                usernameListArray[i] = commentatorUsername;
            }

            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, usernameListArray);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object clickItemObj = parent.getAdapter().getItem(position);
                    String selectedUsername = clickItemObj.toString();
                    String userComment = "";

                    for (int i = 0; i < list.size(); i++) {
                        CommentModel comment;
                        comment = list.get(i);
                        String commentatorUsername = comment.getCommentator();
                        if (commentatorUsername.equals(selectedUsername)) {
                            userComment = comment.getComment();
                        }
                    }

                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    TextView title = new TextView(getContext());
                    title.setText(getResources().getString(R.string.comment_commentator_username) + clickItemObj.toString());

                    int usernameLength = clickItemObj.toString().length();
                    SpannableString ss = new SpannableString(title.getText());
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            UserModel user = new UserModel();
                            user.setUsername(selectedUsername);
                            Map<String, Object> map = new HashMap<>();
                            map.put(ModelEnum.UserModel.name(), user);
                            IntentManager.startActivity(getContext(), ProfileActivity.class, map);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(Color.BLUE);
                            ds.setFakeBoldText(true);
                            ds.setUnderlineText(false);
                        }
                    };
                    usernameLength += 20;
                    ss.setSpan(clickableSpan, 20, usernameLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    title.setText(ss);
                    title.setMovementMethod(LinkMovementMethod.getInstance());


                    title.setPadding(10, 10, 10, 10);
                    title.setGravity(Gravity.CENTER);
                    title.setTextColor(Color.BLACK);
                    title.setTextSize(20);
                    alertDialog.setCustomTitle(title);


                    TextView msg = new TextView(getContext());
                    msg.setText(userComment);
                    msg.setGravity(Gravity.CENTER_HORIZONTAL);
                    msg.setTextColor(Color.BLACK);
                    alertDialog.setView(msg);

                    new Dialog(getContext());
                    alertDialog.show();
                }
            });
        });
    }
}
