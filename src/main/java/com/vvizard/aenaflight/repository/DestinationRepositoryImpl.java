package com.vvizard.aenaflight.repository;

import com.vvizard.aenaflight.model.Destination;
import com.vvizard.aenaflight.model.Dispetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;


@Repository
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DestinationRepositoryImpl implements DestinationRepository{

    @PersistenceContext
    private EntityManager em;

    @NotNull
    private final Dispetcher dispetcher;
    
    @Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public void save(Destination destination) {
        try {

            em.persist(destination);

            dispetcher.saveRecords.accumulate(1);

        }catch (Throwable e){
            System.out.printf("Ошибка сохранения: %s\n", e);
        }
    }


}
