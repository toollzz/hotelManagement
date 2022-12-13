package edu.bowiestate.hotelManagement.reservation;

import edu.bowiestate.hotelManagement.room.Room;

public class ReservationRoomDto {


        private Room room;

        private ReservationDetailForm address;

        //setters ang getters ...

        public Room getRoom() {
                return room;
        }

        public void setRoom(Room room) {
                this.room = room;
        }


}
