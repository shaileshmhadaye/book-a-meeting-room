package com.neo.bookameetingroom.repositories;

import com.neo.bookameetingroom.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("tokenRepository")
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);
}
