package hr.foi.air.sportloc.view.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.service.model.ActiveUserModel;
import hr.foi.air.sportloc.service.model.CommentModel;
import hr.foi.air.sportloc.databinding.FragmentWriteCommentBinding;
import hr.foi.air.sportloc.view.util.Constants;
import hr.foi.air.sportloc.view.util.MessageSender;
import hr.foi.air.sportloc.viewmodel.CommentsViewModel;


public class WriteCommentFragment extends Fragment {
    @BindView(R.id.et_comment)
    EditText etComment;

    private Unbinder unbinder;
    private Boolean userVote;
    private int id;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentWriteCommentBinding viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_comment, container, false);
        View view = viewBinding.getRoot();

        Bundle bundle = this.getArguments();
        userVote = bundle.getBoolean(Constants.USERVOTE);
        id = bundle.getInt(Constants.USERID);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_save_comment)
    public void sendComment() {
        int commentatorId = ActiveUserModel.getInstance().getActiveUser().getUserId();
        Boolean vote = userVote;
        String comment = etComment.getText().toString();

        CommentModel commentModel = new CommentModel();
        commentModel.setUserId(id);
        commentModel.setCommentatorId(commentatorId);
        commentModel.setVote(vote);
        commentModel.setComment(comment);
        observeCommentWrite(commentModel);
    }

    @OnClick(R.id.btn_cancel_coment)
    public void cancelComment() {
        getActivity().onBackPressed();
    }

    public void observeCommentWrite(CommentModel commentModel) {
        if (etComment.getText().length() < 20) {
            MessageSender.sendError(getContext(), getResources().getString(R.string.write_comment_length));
        } else {
            CommentsViewModel commentsViewModel = new CommentsViewModel();
            commentsViewModel.writeComment(commentModel);
            commentsViewModel.sendCommentObservable().observe(this, result -> {
                if (result != null) {
                    if (result) {
                        MessageSender.sendMessage(getContext(), getResources().getString(R.string.write_comment_success));

                        getActivity().onBackPressed();
                    } else {
                        MessageSender.sendError(getContext(), getResources().getString(R.string.write_comment_exists));
                    }
                } else {
                    MessageSender.sendError(getContext(), getResources().getString(R.string.write_comment_error));
                }
            });
        }
    }
}
