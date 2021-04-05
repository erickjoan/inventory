package com.hampcode.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.model.Account;
import com.hampcode.repository.UserRepository;
import com.hampcode.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<Account> getAll() throws Exception {
		return (List<Account>)userRepository.findAll();
	}

	@Override
	public Page<Account> getAll(Pageable pageable) throws Exception {
		return userRepository.findAll(pageable);
	}

	@Override
	public Account saveOrUpdate(Account entity) throws Exception {
		return userRepository.save(entity);
	}

	@Override
	public Optional<Account> getOne(Long id) throws Exception {
		return userRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		userRepository.deleteById(id);
	}

}
