package ru.shop.service;

import org.springframework.stereotype.Service;
import ru.shop.entity.Attendance;
import ru.shop.entity.Banner;

import java.util.List;

@Service
public interface IAttendanceService {
    boolean updateAttendance(Attendance attendance);
    List<Attendance> allAttendancesByStatus(String status);
}
