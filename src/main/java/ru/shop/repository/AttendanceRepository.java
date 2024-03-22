package ru.shop.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.entity.Attendance;
import ru.shop.entity.Banner;
import ru.shop.entity.Product;

import java.sql.Date;
import java.util.List;

@Repository
public interface AttendanceRepository extends CrudRepository<Attendance,Integer> {
    @Query("SELECT * FROM attendances WHERE status = :status ORDER BY date_create ASC")
    List<Attendance> findByStatusAttendance(@Param("status")String status);
    @Modifying
    @Query("UPDATE attendances SET date_create=:dateCreate, status=:status WHERE id=:id")
    void updateAttendance(@Param("id")int id, @Param("dateCreate") Date dateCreate, @Param("status")String status);
}
