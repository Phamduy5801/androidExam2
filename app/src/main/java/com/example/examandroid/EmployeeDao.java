package com.example.examandroid;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmployeeDao {
    @Insert
    long insertUser(Employee employeeEntity);

    @Update
    void updateUser(Employee employeeEntity);

    @Query("SELECT * FROM employee where id=:id")
    Employee findUser(int id);

    @Query("SELECT * FROM employee")
    List<Employee> getAllUser();

    @Delete
    void deleteUser(Employee userEntity);
}
