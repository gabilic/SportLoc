package hr.foi.air.sportloc.viewmodel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;
import hr.foi.air.sportloc.service.model.CommentModel;

public class CommentsViewModel {
    private LiveData<List<CommentModel>> commentsObservable;

    public void getComments(Integer id){
        commentsObservable=WebServiceCaller.getInstance().getComments(id);
    }

    public LiveData<List<CommentModel>> getCommentsObservable(){
        return  commentsObservable;
    }
}
