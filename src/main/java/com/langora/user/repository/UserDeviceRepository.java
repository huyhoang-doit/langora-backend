package com.langora.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.user.domain.entity.UserDevice;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, String> {
    List<UserDevice> findByUserId(String userId);
}
