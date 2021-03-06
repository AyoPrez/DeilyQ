package deilyquote;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import deilyquote.UserMoments;
import deilyquote.UserQuotes;

import deilyquote.UserMomentsDao;
import deilyquote.UserQuotesDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userMomentsDaoConfig;
    private final DaoConfig userQuotesDaoConfig;

    private final UserMomentsDao userMomentsDao;
    private final UserQuotesDao userQuotesDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userMomentsDaoConfig = daoConfigMap.get(UserMomentsDao.class).clone();
        userMomentsDaoConfig.initIdentityScope(type);

        userQuotesDaoConfig = daoConfigMap.get(UserQuotesDao.class).clone();
        userQuotesDaoConfig.initIdentityScope(type);

        userMomentsDao = new UserMomentsDao(userMomentsDaoConfig, this);
        userQuotesDao = new UserQuotesDao(userQuotesDaoConfig, this);

        registerDao(UserMoments.class, userMomentsDao);
        registerDao(UserQuotes.class, userQuotesDao);
    }
    
    public void clear() {
        userMomentsDaoConfig.getIdentityScope().clear();
        userQuotesDaoConfig.getIdentityScope().clear();
    }

    public UserMomentsDao getUserMomentsDao() {
        return userMomentsDao;
    }

    public UserQuotesDao getUserQuotesDao() {
        return userQuotesDao;
    }

}
