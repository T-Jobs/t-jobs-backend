package ru.ns.t_jobs.auth.credentials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
    Optional<Credentials> findByLogin(String login);
}

