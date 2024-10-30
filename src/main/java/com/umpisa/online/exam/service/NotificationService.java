package com.umpisa.online.exam.service;

public interface NotificationService {

    void sendNotification(String message, String notificationType);
    void sendScheduledNotification();
}
