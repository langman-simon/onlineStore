package com.hyperion.dataAccess.dao;

import com.hyperion.dataAccess.repository.UserRepository;
import com.hyperion.dataAccess.util.UserMapper;
import com.hyperion.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserDAO implements UserDataAccess {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserDAO(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login).map(mapper::toModel);
    }

    @Override
    public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }

    @Override
    @Transactional
    public User save(User user) {
        return mapper.toModel(repository.save(mapper.toEntity(user)));
    }
}
