package com.ayoprez.database;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import deilyquote.UserMoments;
import deilyquote.UserMomentsDao;


/**
 * Created by Ayoze on 29/01/15.
 */
public class UserMomentsRepository {

    public static void insertOrUpdate(Context context, UserMoments userMoments) {
        getUserMomentsDao(context).insertOrReplace(userMoments);
    }

    public long getIdFromData(Context context, UserMoments userMoments){
        QueryBuilder qb = getUserMomentsDao(context).queryBuilder();
        qb.where(new WhereCondition.StringCondition("Time = '"+ userMoments.getTime() +"' " +
                "AND Language = '"+ userMoments.getLanguage() +"' "));

        List<UserMoments> idList = qb.list();
        return idList.get(0).getId();
    }

    public void deleteSelectedMoment(Context context, int selected){
        getUserMomentsDao(context).delete(getAllMoments(context).get(selected));
    }

    public long getLastId(Context context){
        List<UserMoments> userMomentsList = getUserMomentsDao(context).loadAll();

        if(userMomentsList.size() == 0){
            return 0;
        }else{
            long lastId = getUserMomentsDao(context).loadAll().get(getRowsCount(context) - 1).getId();
            return lastId + 1;
        }
    }

    public int getRowsCount(Context context){
        return getUserMomentsDao(context).loadAll().size();
    }

    public static void clearMoments(Context context) {
        getUserMomentsDao(context).deleteAll();
    }

    public static void deleteMomentWithId(Context context, long id) {
        getUserMomentsDao(context).delete(getMomentForId(context, id));
    }

    public static List<UserMoments> getAllMoments(Context context) {
        return getUserMomentsDao(context).loadAll();
    }

    public static UserMoments getMomentForId(Context context, long id) {
        return getUserMomentsDao(context).load(id);
    }

    private static UserMomentsDao getUserMomentsDao(Context c) {
        return new CreateDatabase(c).getDaoSession().getUserMomentsDao();
    }
}