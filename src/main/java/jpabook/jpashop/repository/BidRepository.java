package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Bid;
import jpabook.jpashop.domain.BidItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BidRepository {

    private final EntityManager em;


    public void save(Bid bid) {
        em.persist(bid);
    }

    public BidItem findOne(Long id) {
        return em.find(BidItem.class, id);
    }

    public List<BidItem> findAll() {
        return em.createQuery("select b from BidItem b", BidItem.class)
                .getResultList();
    }

}
