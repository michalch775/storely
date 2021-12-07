package com.example.smartRecordServer.rental;

import com.example.smartRecordServer.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    public List<Rental> findByItem_code(Long code);
    public List<Rental> findByUser_email(String userId);
//    public List<Rental> findByItemCodeIs(Long code);
//    public List<Rental> findAllByCode(Long code);

}
