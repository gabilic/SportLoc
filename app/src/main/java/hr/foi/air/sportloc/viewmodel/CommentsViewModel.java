package hr.foi.air.sportloc.viewmodel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;
import hr.foi.air.sportloc.service.model.CommentModel;

public class CommentsViewModel {
    private LiveData<List<CommentModel>> commentsObservable;
    private LiveData<Boolean> writeCommentObservable;

    public void getComments(Integer id){
        commentsObservable=WebServiceCaller.getInstance().getComments(id);
    }
    public void writeComment(CommentModel comment){
        writeCommentObservable=WebServiceCaller.getInstance().writeComment(comment);
    }
    public LiveData<List<CommentModel>> getCommentsObservable(){
        return  commentsObservable;
    }
    public LiveData<Boolean> sendCommentObservable(){
        return writeCommentObservable;
    }
}
