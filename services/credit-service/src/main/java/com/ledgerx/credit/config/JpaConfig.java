package com.ledgerx.credit.config;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Configuration
public class JpaConfig {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Enable the "deletedFilter" on every Hibernate session,
     * so only non-deleted entities are visible.
     */
    //@PostConstruct + TransactionSynchronizationManager ensures the filter is always active for transactions.
    @PostConstruct
    public void init() {
        // Ensure filter is applied when a transaction starts
        TransactionSynchronizationManager.registerSynchronization(new org.springframework.transaction.support.TransactionSynchronizationAdapter() {
        //TransactionSynchronizationManager : Each time a database transaction starts, do something before it finishes.
            @Override
            public void beforeCommit(boolean readOnly) {
                enableSoftDeleteFilter();
            }
        });
    }

    /**
     * Activate a Hibernate filter "deletedFilter"
     * automatically on every transaction (if defined on entities).
     */
    @Transactional
    public void enableSoftDeleteFilter() {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", false);
    }
}
//The config ensures that whenever you use JPA or repositories,
//Hibernate automatically hides rows where deleted = true,
//without you needing to remember to add that filter yourself.