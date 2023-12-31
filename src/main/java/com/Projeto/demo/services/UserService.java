package com.Projeto.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.Projeto.demo.entities.User;
import com.Projeto.demo.repositories.UserRepository;
import com.Projeto.demo.services.exceptions.DatabaseException;
import com.Projeto.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service // Para classificar como camada de serviço para utilizar o Autowired (component
			// registration)
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Long Id) {
		Optional<User> obj = userRepository.findById(Id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(Id)); // orElseThrow - Tenta dar o get() e se caso
																			// nao tiver o Usuário nesse id, ele retorna
																			// a exceção
	}

	public User insert(User obj) {
		return userRepository.save(obj);

	}

	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public User update(Long id, User obj) {
		try {
			User entity = userRepository.getReferenceById(id); //getTeferenceById ele nao busca no banco de dados, 
																//ele só deixa instanciado sendo mais eficiente. O findById 
																//busca direto no banco de dados e ja faz a alteração.
			updateData(entity, obj);
			return userRepository.save(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}		

	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
}
