package com.tpjad.project.photoalbumapi.dao.repository;

import com.tpjad.project.photoalbumapi.dao.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {
}
