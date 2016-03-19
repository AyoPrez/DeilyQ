package com.ayoprez.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import deilyquote.UserMoments;
import deilyquote.UserMomentsDao;
import deilyquote.UserQuotes;
import deilyquote.UserQuotesDao;

/**
 * Created by ayo on 13.02.16.
 */
public class FavQuotesRepository {

    public static void insertOrUpdate(Context context, UserQuotes userQuotes) {
        getUserQuotesDao(context).insertOrReplace(userQuotes);
    }

    public static void insertArrayList(Context context, ArrayList<UserQuotes> userQuotes){
        for(UserQuotes quote : userQuotes)
            insertOrUpdate(context, quote);
    }

    public long getIdFromData(Context context, UserQuotes userQuotes){
        QueryBuilder qb = getUserQuotesDao(context).queryBuilder();
        qb.where(new WhereCondition.StringCondition("Quote = '"+ userQuotes.getQuote() +"' " +
                "AND Author = '"+ userQuotes.getAuthor() +"' "));

        List<UserMoments> idList = qb.list();
        return idList.get(0).getId();
    }

    public long getLastId(Context context){
        List<UserQuotes> userQuotesList = getUserQuotesDao(context).loadAll();

        if(userQuotesList.size() == 0){
            return 0;
        }else{
            long lastId = getUserQuotesDao(context).loadAll().get(getRowsCount(context) - 1).getId();
            return lastId + 1;
        }
    }

    public static int getRowsCount(Context context){
        return getUserQuotesDao(context).loadAll().size();
    }

    public static List<UserQuotes> getAllQuotes(Context context) {
        return getUserQuotesDao(context).loadAll();
    }

    public static UserQuotes getQuoteForId(Context context, long id) {
        return getUserQuotesDao(context).load(id);
    }

    private static UserQuotesDao getUserQuotesDao(Context c) {
        return new CreateDatabase(c).getDaoSession().getUserQuotesDao();
    }
}
