package com.polaris.Stockify.repository;

import com.polaris.Stockify.model.Role;
import com.polaris.Stockify.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleName roleUser);

}
