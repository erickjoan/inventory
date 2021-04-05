package com.hampcode.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.model.Role;
import com.hampcode.repository.RoleRepository;
import com.hampcode.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<Role> getAll() throws Exception {
		return (List<Role>)roleRepository.findAll();
	}

	@Override
	public Page<Role> getAll(Pageable pageable) throws Exception {
		return roleRepository.findAll(pageable);
	}

	@Override
	public Role saveOrUpdate(Role entity) throws Exception {
		return roleRepository.save(null);
	}

	@Override
	public Optional<Role> getOne(Long id) throws Exception {
		return roleRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		roleRepository.deleteById(id);
		
	}

}
