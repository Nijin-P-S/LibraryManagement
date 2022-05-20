package com.minorProject.libraryManagement.repository;

import com.minorProject.libraryManagement.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class StudentCacheRepository {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private static final String KEY_PREFIX = "std::";
    private static final Integer KEY_EXPIRY = 20;

    public void saveStudent(Student student) throws Exception {
        if(student.getId() == 0) //Since datatype of Id is in int and not in Integer
            throw new Exception("Id not Present for the student");
        String key = getKey(student.getId());
        redisTemplate.opsForValue().set(key,student, KEY_EXPIRY, TimeUnit.MINUTES);
    }

    public Student getStudent(Integer studentId){
        if(studentId == null)
            return null;
        String key = getKey(studentId);
        return (Student) redisTemplate.opsForValue().get(key);
    }

    private String getKey(int studentId){
        return KEY_PREFIX+studentId;
    }
}
