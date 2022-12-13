package edu.bowiestate.hotelManagement.reservation;

import edu.bowiestate.hotelManagement.customer.Customer;
import edu.bowiestate.hotelManagement.customer.CustomerRepository;
import edu.bowiestate.hotelManagement.person.PersonRepository;
import edu.bowiestate.hotelManagement.person.PersonService;
import edu.bowiestate.hotelManagement.customer.CustomerService;
import edu.bowiestate.hotelManagement.employee.Employee;
import edu.bowiestate.hotelManagement.employee.EmployeeCredentialsForm;
import edu.bowiestate.hotelManagement.housekeep.HouseKeepTask;
import edu.bowiestate.hotelManagement.housekeep.HouseKeepTaskForm;
import edu.bowiestate.hotelManagement.housekeep.HouseKeepTaskService;
import edu.bowiestate.hotelManagement.person.Person;
import edu.bowiestate.hotelManagement.room.Room;
import edu.bowiestate.hotelManagement.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private HouseKeepTaskService houseKeepTaskService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
    }

    public List<Reservation> findCurrentDaysReservations() {
        return reservationRepository.findByTodaysReservations(LocalDate.now());
    }

    public List<Reservation> findFutureReservations() {
        return reservationRepository.findByStartDateGreaterThanAndStatusOrderByStartDate(LocalDate.now(), Reservation.ReservationStatus.OPEN);
    }


    public Reservation findByConfirmationNum(Long confirmId) {
        Optional<Reservation> reservation = reservationRepository.findById(confirmId);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            return null;
        }
    }

    public Reservation FindReservationByConfirmNum(Long id){
        return reservationRepository.FindReservationByConfirmNum(id);
    }

    public Reservation checkIn(Long confirmId) {
        Reservation reservation = findByConfirmationNum(confirmId);
        if (reservation != null) {
            reservation.setPriceLocked('Y');
            reservation.setStatus(Reservation.ReservationStatus.IN_PROGRESS);
            reservation.getRoom().setStatus(Room.RoomStatus.OCCUPIED);
            return reservationRepository.save(reservation);
        } else {
            // error
            return null;
        }
    }

    public Reservation checkout(Long confirmId) {
        Reservation reservation = findByConfirmationNum(confirmId);
        if (reservation != null) {
            reservation.setStatus(Reservation.ReservationStatus.CLOSED);

            HouseKeepTaskForm taskForm = new HouseKeepTaskForm();
            taskForm.setTaskType(HouseKeepTask.TaskType.CLEANING);
            taskForm.setTaskRoomNum(reservation.getRoom().getRoomNum());
            taskForm.setDeadlineDate(LocalDate.now());

            HouseKeepTask houseKeepTask = houseKeepTaskService.saveHouseKeepTask(taskForm);
            reservation.setRoom(houseKeepTask.getRoom());

            return reservationRepository.save(reservation);
        } else {
            // error
            return null;
        }
    }

    public Reservation cancel(Long confirmId) {
        Reservation reservation = findByConfirmationNum(confirmId);
        if (reservation != null) {
            reservation.setStatus(Reservation.ReservationStatus.CLOSED);
            reservation.getRoom().setStatus(Room.RoomStatus.AVAILABLE);
            return reservationRepository.save(reservation);
        }
        return null;
    }

    public Reservation updateReservation(ReservationUpdateForm reservationUpdateForm) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationUpdateForm.getConfirmNum());
        if(reservation.isPresent()){
            Reservation existingReservation = reservation.get();
            Room room = roomService.findById(reservationUpdateForm.getRoomNum());
            if(room != null) {
                existingReservation.setRoom(room);
                existingReservation.setPrice(reservationUpdateForm.getPrice());
                existingReservation.setStartDate(reservationUpdateForm.getStartDate());
            //   existingReservation.setEndDate(reservationUpdateForm.getEndDate());
                return reservationRepository.save(existingReservation);
            }

        }
        return null;
    }

    public List<Reservation> findAllReservation() {
        return reservationRepository.FindAllReservation();
    }

    public List<Reservation> findAllReserv() {
        return reservationRepository.FindAllReserv();
    }

    public Reservation FindReservationByRoomNum(long Rid)
    {
            return reservationRepository.FindReservationByRoomNum(Rid);
    }

    public Person savePerson(ReservationDetailForm _rdf){
        Person person = personService.findByFirstnameAndLastname(_rdf.getFirstname(),_rdf.getLastname());
      //  long Pids = GenCustomerId();
        Person p = new Person();
        if(person == null)
        {

            p.setFirstname(_rdf.getFirstname());
            p.setLastname(_rdf.getLastname());
            p.setMiddle(_rdf.getMiddle());
            p.setTelephone(_rdf.getTelephone());
            p.setAddress(_rdf.getAddress());


            // p.setId(Pids);

          return   personRepository.save(p);

        }
        return p;
    }

    public Customer saveCustomer(ReservationDetailForm _rdf){

        Customer c = new Customer();
        Person p = new Person();
        p = savePerson(_rdf);
        if(p.getId() != null)
        {

            System.out.println("Cus"+ p.getId());
            c.setCustId(p.getId());
            c.setEmailAddress(_rdf.getEmail());
            c.setLicensePlateNum("");
            return  customerRepository.save(c);

        }
        return c;
    }

    public Reservation saveReservation(ReservationDetailForm _rdf)
    {
        Reservation r = reservationRepository.FindReservationByConfirmNum(_rdf.getCONFIRMNUM());
        Room room = roomService.findById(_rdf.getROOM_NUM());
        Customer c = new Customer();

        c = saveCustomer(_rdf);
        Customer cus = customerService.FindCustomerById(c.getCustId());

        System.out.println("cid"+c.getCustId());
        System.out.println("cid1"+cus.getCustId());
        System.out.println("cus"+cus);
       if(r == null) {


            Reservation reservation = new Reservation();

            reservation.setRoom(room);
            reservation.setPrice(RoomPrice(_rdf.getROOM_NUM()));
            //reservation.getConfirmNum()
            reservation.setStartDate(_rdf.getSTARTDATES());
            reservation.setEndDate(_rdf.getENDDATES());
            reservation.setPriceLocked('y');
            reservation.setDoNotDisturb(_rdf.getDoNotDisturb());
            reservation.setConfirmNum(_rdf.getCONFIRMNUM());
            reservation.setStatus(Reservation.ReservationStatus.IN_PROGRESS);
            reservation.setCustomer(cus);
            return reservationRepository.save(reservation);
        }


        return r;
    }
 /* public List<Object[]> FindReservationByConfirmNums(long ConfID){
        return  reservationRepository.FindReservationByConfirmNums(ConfID);
  }*/
   public long RoomPrice(long RoomID)
   {
        try
        {
            long Price = 0;
            if(RoomID == 1)
            {
                Price = 40;
            }
            else
                if(RoomID == 2)
                {
                    Price = 60;
                 } else if (RoomID == 3) {
                    Price = 90;
                } else if (RoomID == 4) {
                    Price = 40;
                }
            return Price;
        }
        catch (Exception ex)
        {
            return 0;
        }



   }

    public Reservation ResevationByCId(Long confirmId) {
        Reservation reservation = findByConfirmationNum(confirmId);
        if (reservation != null)
        {
            return reservation;
        }
        return null;
    }

    public int GenCustomerId()
    {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }

}
