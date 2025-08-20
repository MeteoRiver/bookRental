package com.metoeriver.library.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.metoeriver.library.user.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
}
