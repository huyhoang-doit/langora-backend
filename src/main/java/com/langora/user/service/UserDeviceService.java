package com.langora.user.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.user.domain.entity.UserDevice;
import com.langora.user.dto.request.UserDeviceRegisterRequest;
import com.langora.user.dto.response.UserDeviceResponse;
import com.langora.user.infrastructure.mapper.UserDeviceMapper;
import com.langora.user.repository.UserDeviceRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDeviceService {

    UserDeviceRepository userDeviceRepository;
    UserDeviceMapper userDeviceMapper;

    @Transactional(readOnly = true)
    public List<UserDeviceResponse> getDevices(String userId) {
        return userDeviceRepository.findByUserId(userId).stream()
                .map(userDeviceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDeviceResponse registerDevice(String userId, UserDeviceRegisterRequest request) {
        UserDevice device = UserDevice.builder()
                .userId(userId)
                .deviceType(request.getDeviceType())
                .deviceToken(request.getDeviceToken())
                .deviceName(request.getDeviceName())
                .operatingSystem(request.getOperatingSystem())
                .appVersion(request.getAppVersion())
                .isActive(true)
                .lastActiveAt(OffsetDateTime.now())
                .createdAt(OffsetDateTime.now())
                .build();

        device = userDeviceRepository.save(device);
        return userDeviceMapper.toResponse(device);
    }
}
