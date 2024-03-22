package ru.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shop.entity.Attendance;
import ru.shop.entity.Order;
import ru.shop.entity.Product;
import ru.shop.repository.AttendanceRepository;
import ru.shop.repository.OrderRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService implements IAttendanceService{
    @Autowired
    AttendanceRepository attendanceRepository;
    public Attendance findAttendanceById(int attendanceId){
        Optional<Attendance> attendanceFromDb = attendanceRepository.findById(attendanceId);
        return attendanceFromDb.orElse(new Attendance());
    }
    public List<Attendance> allAttendances() {
        return (List<Attendance>) attendanceRepository.findAll();
    }
    public boolean deleteAttendance(int attendanceId) {
        if (attendanceRepository.findById(attendanceId).isPresent()) {
            attendanceRepository.deleteById(attendanceId);
            return true;
        }
        return false;
    }
    public boolean saveAttendance(Attendance attendance) {
        if (attendance == null) {
            return false;
        }
        attendanceRepository.save(attendance);
        return true;
    }
    @Override
    public boolean updateAttendance(Attendance attendance) {
        if (attendance == null) {
            return true;
        }
        Attendance attendanceToUpdate = attendanceRepository.findById(attendance.getId()).get();
        attendanceToUpdate.setDateCreate(attendance.getDateCreate());
        attendanceToUpdate.setStatus(attendance.getStatus());
        attendanceRepository.updateAttendance(attendanceToUpdate.getId(),attendanceToUpdate.getDateCreate(),attendanceToUpdate.getStatus());
        return false;
    }

    @Override
    public List<Attendance> allAttendancesByStatus(String status) {
        List<Attendance>lstAttendance=new ArrayList<>();
        for (int i=0;i<attendanceRepository.findByStatusAttendance(status).size();i++){
            if ((new Date(System.currentTimeMillis()-7*24*60*60*1000).before(attendanceRepository.findByStatusAttendance(status).get(i).getDateCreate()))&&(new Date(System.currentTimeMillis()+1*24*60*60*1000).after(attendanceRepository.findByStatusAttendance(status).get(i).getDateCreate()))){
                lstAttendance.add(attendanceRepository.findByStatusAttendance(status).get(i));
            }
            else{
                attendanceRepository.deleteById(attendanceRepository.findByStatusAttendance(status).get(i).getId());
            }
        }
        return lstAttendance;
    }
}
