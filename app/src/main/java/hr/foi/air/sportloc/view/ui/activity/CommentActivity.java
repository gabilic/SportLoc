package hr.foi.air.sportloc.view.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.view.ui.fragment.CommentListFragment;
import android.support.v4.app.Fragment;

public class CommentActivity extends AppCompatActivity implements CommentListFragment.CommentsListViewListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        CommentListFragment commentListFragment=new CommentListFragment();
        commentListFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.comment_fragment, commentListFragment).commit();
    }

    @Override
    public void onOpenCommentsList(){
        CommentListFragment commentListFragment=new CommentListFragment();
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




