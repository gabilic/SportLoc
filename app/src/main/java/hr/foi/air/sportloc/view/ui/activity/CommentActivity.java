package hr.foi.air.sportloc.view.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.view.ui.fragment.CommentListFragment;
import android.support.v4.app.Fragment;
import hr.foi.air.sportloc.view.ui.fragment.WriteCommentFragment;

public class CommentActivity extends AppCompatActivity implements CommentListFragment.CommentsListViewListener{
    Boolean openWriteComment;
    Boolean userVote;
    String stringId;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        openWriteComment = getIntent().getBooleanExtra("key", false);
        userVote=getIntent().getBooleanExtra("vote",false);
        stringId=getIntent().getStringExtra("stringId");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        if(openWriteComment==false){
            Bundle bundle=new Bundle();
            bundle.putString("stringId",stringId);

            CommentListFragment commentListFragment = new CommentListFragment();
            commentListFragment.setArguments(getIntent().getExtras());

            commentListFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.comment_fragment, commentListFragment).commit();
        }
        else{
            Bundle bundle=new Bundle();
            bundle.putBoolean("userVote",userVote);
            bundle.putString("stringId",stringId);

            WriteCommentFragment writeCommentFragment=new WriteCommentFragment();
            writeCommentFragment.setArguments(getIntent().getExtras());

            writeCommentFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.comment_fragment, writeCommentFragment).commit();
        }
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




