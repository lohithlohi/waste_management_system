package com.ust.wastewarden.notifications.repository;

import com.ust.wastewarden.notifications.model.Notification;
import com.ust.wastewarden.notifications.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndStatus(Long userId, NotificationStatus status);
}
