package com.example.springboot_thread_pool.dao;


import com.example.springboot_thread_pool.entity.Pindaouser;
import com.example.springboot_thread_pool.entity.Shipin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PindaouserRepository extends JpaRepository<Pindaouser, Long> {


    @Modifying
    @Transactional
    @Query(value = "UPDATE member SET expirationtime = :expirationtime WHERE membernumber = :membernumber", nativeQuery = true)
    void updateshijian(@Param("expirationtime") String expirationtime, @Param("membernumber") String membernumber);

}
