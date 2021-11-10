package com.tpjad.project.photoalbumapi.dao.repository;

import com.tpjad.project.photoalbumapi.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(String role);
}
