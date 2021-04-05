package com.hampcode.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hampcode.model.Account;

@Repository
public interface UserRepository extends JpaRepository<Account, Long> {
	@Query("SELECT u FROM Account u WHERE CONCAT(u.id,' ',u.userName) LIKE %?1%")
	Page<Account> findAll(String keyword, Pageable pageable);
	
	Account findByUserName(String userName);
}
