package com.ledgerx.credit.config;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    public void enableSoftDeleteFilter() {
        Session session = entityManager.unwrap(Session.class);
        if (session.getEnabledFilter("deletedFilter") == null) {
            session.enableFilter("deletedFilter").setParameter("isDeleted", false);
        }
    }
}

//The config ensures that whenever you use JPA or repositories,
//Hibernate automatically hides rows where deleted = true,
//without you needing to remember to add that filter yourself.