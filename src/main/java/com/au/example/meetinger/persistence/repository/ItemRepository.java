package com.au.example.meetinger.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.au.example.meetinger.persistence.model.Item;

public interface ItemRepository extends CrudRepository<Item, Long>{
	
	@Query("SELECT i FROM ITEM i WHERE i.meeting.meetingId = :meetingId")
    List<Item> findMeetingItems(@Param("meetingId") Long meetingId);

}
