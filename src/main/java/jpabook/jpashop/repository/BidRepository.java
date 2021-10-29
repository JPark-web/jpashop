package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Bid;
import jpabook.jpashop.domain.BidItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BidRepository {

    private final EntityManager em;


    public void save(BidItem bidItem) {
        em.persist(bidItem);
    }

    public BidItem findOne(Long id) {
        return em.find(BidItem.class, id);
    }

    public List<BidItem> findAll(Long id) {
        return em.createQuery("select b from BidItem b where b.item.id = "+id, BidItem.class)
                .getResultList();
    }








}
