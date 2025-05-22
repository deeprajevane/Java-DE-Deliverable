package com.practice.NotificationService.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeEvent {
    private String id;
    private String name;
    private String action;
}
