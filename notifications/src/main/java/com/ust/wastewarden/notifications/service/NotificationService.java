package com.ust.wastewarden.notifications.service;

import com.ust.wastewarden.notifications.dtos.BinDTO;
import com.ust.wastewarden.notifications.dtos.UserDTO;
import com.ust.wastewarden.notifications.model.Notification;
import com.ust.wastewarden.notifications.model.NotificationStatus;
import com.ust.wastewarden.notifications.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Autowired
    private RestTemplate restTemplate;

    // (binfill update -> check status -> run trucks -> make bins empty)
    // Functionality to monitor bin statuses and send notifications
    // Sceduling to check the bin statuses are needed
    public void checkBinStatusAndNotify() {

        // the way of fetching data from other microservices needs updation  -> !Important pending
        String binServiceUrl = "http://bin-service/bins/status/full";
        ResponseEntity<List<BinDTO>> response = restTemplate.exchange(
                binServiceUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BinDTO>>() {});
        List<BinDTO> fullBins = response.getBody();

//        assert fullBins != null;   No fullBins found exception handling needed
        fullBins.forEach(bin -> {
            // Fetch admins from User Service
            String userServiceUrl = "http://user-service/users/admins";
            ResponseEntity<List<UserDTO>> userResponse = restTemplate.exchange(
                    userServiceUrl, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<UserDTO>>() {});

            List<UserDTO> admins = userResponse.getBody();

//            assert admins != null;  No Admin found exception handling needed
            admins.forEach(admin -> {
                Notification notification = new Notification();
                notification.setUserId(admin.id());
                notification.setMessage("Bin at location " + bin.location() + " is full.");
                notification.setStatus(NotificationStatus.UNREAD);
                notification.setCreatedAt(LocalDateTime.now());
                notificationRepository.save(notification);
            });
        });
    }

    // Retrieve unread notifications for a user
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndStatus(userId, NotificationStatus.UNREAD);
    }
}
