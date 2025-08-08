package com.example.springboot_thread_pool.dao;


import com.example.springboot_thread_pool.entity.Infouser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface InfouserRepository extends JpaRepository<Infouser, Long> {


    @Modifying
    @Transactional
    @Query(value = "UPDATE infouser SET userbalance = :userbalance WHERE usernumber = :usernumber", nativeQuery = true)
    void updateye(@Param("userbalance") int userbalance, @Param("usernumber") String usernumber);

    Infouser findByUsernumber(String usernumber);
    @Modifying
    @Transactional
    @Query(value = "UPDATE infouser SET yiduihuan = :yiduihuan WHERE usernumber = :usernumber", nativeQuery = true)
    void updateduihuan(@Param("yiduihuan") int yiduihuan, @Param("usernumber") String usernumber);



    @Modifying
    @Transactional
    @Query(value = "UPDATE infouser SET " +
            "userbalance = :userbalance," +
            "useryaoqing = :useryaoqing," +
            "isi = :isi " +
            "WHERE usernumber = :usernumber", nativeQuery = true)
    void updatestart(@Param("userbalance") int userbalance,
                  @Param("useryaoqing") int useryaoqing,
                  @Param("usernumber") String usernumber,
                     @Param("isi") int isi);


}
