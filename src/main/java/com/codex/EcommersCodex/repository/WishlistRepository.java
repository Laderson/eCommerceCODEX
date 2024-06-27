package com.codex.EcommersCodex.repository;

import com.codex.EcommersCodex.models.ProdWish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<ProdWish, Long> {
    List<ProdWish> findByUsuarioId(Long userId);
}
