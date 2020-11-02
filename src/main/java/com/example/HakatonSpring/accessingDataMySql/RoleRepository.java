package com.example.HakatonSpring.accessingDataMySql;


import com.example.HakatonSpring.model.ERole;
import com.example.HakatonSpring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
