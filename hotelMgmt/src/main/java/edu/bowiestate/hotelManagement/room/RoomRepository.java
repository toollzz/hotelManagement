package edu.bowiestate.hotelManagement.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    public List<Room> findAllByStatusNotIn(Collection<Room.RoomStatus> status);
    public List<Room> findAllByStatus(Room.RoomStatus status);

    @Query(nativeQuery = true, value = "Select * from Room ")
    public List<Room> findAllRooms();
}
