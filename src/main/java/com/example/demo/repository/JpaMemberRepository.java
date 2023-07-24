package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;


import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em; //DB와의 통신을 처리. JPA를 쓸려면 Entity Manger을 주입받아야 함.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member); // 값이 없을 수도 있으므로
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result   = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();

        return result.stream().findAny();
        //객체지향 쿼리 사용
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member as m", Member.class)
                .getResultList();
    }
}