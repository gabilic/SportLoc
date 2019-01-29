package hr.foi.air.sportloc.view.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.view.ui.fragment.CommentListFragment;

import android.support.v4.app.Fragment;

import hr.foi.air.sportloc.view.ui.fragment.WriteCommentFragment;
import hr.foi.air.sportloc.view.util.Constants;

public class CommentActivity extends AppCompatActivity implements CommentListFragment.CommentsListViewListener {
    private Boolean openWriteComment;
    private Boolean userVote;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        openWriteComment = getIntent().getBooleanExtra(Constants.KEY, false);
        userVote = getIntent().getBooleanExtra(Constants.VOTE, false);
        id = getIntent().getIntExtra(Constants.USERID, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        if (openWriteComment == false) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.USERID, id);
            CommentListFragment commentListFragment = new CommentListFragment();
            commentListFragment.setArguments(getIntent().getExtras());
            commentListFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.comment_fragment, commentListFragment).commit();
        } else {
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.USERVOTE, userVote);
            bundle.putInt(Constants.USERID, id);
            WriteCommentFragment writeCommentFragment = new WriteCommentFragment();
            writeCommentFragment.setArguments(getIntent().getExtras());
            writeCommentFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.comment_fragment, writeCommentFragment).commit();
        }
    }

    @Override
    public void onOpenCommentsList() {
        CommentListFragment commentListFragment = new CommentListFragment();
        commentListFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.comment_fragment, commentListFragment).commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof CommentListFragment) {
            CommentListFragment commentListFragment = (CommentListFragment) fragment;
            commentListFragment.setListener(this::onOpenCommentsList);
        }
    }

}




