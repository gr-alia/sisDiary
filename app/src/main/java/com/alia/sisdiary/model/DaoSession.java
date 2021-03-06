package com.alia.sisdiary.model;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.alia.sisdiary.model.ScheduledSubject;
import com.alia.sisdiary.model.Subject;

import com.alia.sisdiary.model.ScheduledSubjectDao;
import com.alia.sisdiary.model.SubjectDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig scheduledSubjectDaoConfig;
    private final DaoConfig subjectDaoConfig;

    private final ScheduledSubjectDao scheduledSubjectDao;
    private final SubjectDao subjectDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        scheduledSubjectDaoConfig = daoConfigMap.get(ScheduledSubjectDao.class).clone();
        scheduledSubjectDaoConfig.initIdentityScope(type);

        subjectDaoConfig = daoConfigMap.get(SubjectDao.class).clone();
        subjectDaoConfig.initIdentityScope(type);

        scheduledSubjectDao = new ScheduledSubjectDao(scheduledSubjectDaoConfig, this);
        subjectDao = new SubjectDao(subjectDaoConfig, this);

        registerDao(ScheduledSubject.class, scheduledSubjectDao);
        registerDao(Subject.class, subjectDao);
    }
    
    public void clear() {
        scheduledSubjectDaoConfig.clearIdentityScope();
        subjectDaoConfig.clearIdentityScope();
    }

    public ScheduledSubjectDao getScheduledSubjectDao() {
        return scheduledSubjectDao;
    }

    public SubjectDao getSubjectDao() {
        return subjectDao;
    }

}
