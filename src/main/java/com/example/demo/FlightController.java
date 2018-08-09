package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FlightController {

    @GetMapping("/flights/flight")
    public Flight getSingleFlightInfo() {
        Flight flight = new Flight();

        flight.setDeparts("2017-04-21 14:34");
        flight.setTickets("Some", "Name", 200);

        return flight;
    }

    @GetMapping("/flights")
    public List<Flight> getAllFlights() {
        List<Flight> flightList = new ArrayList<>();
        Flight flight1 = getSingleFlightInfo();
        Flight flight2 = new Flight();

        flight2.setDeparts("2017-04-21 14:34");
        flight2.setTickets("SomeOther", null, 400);

        flightList.add(flight1);
        flightList.add(flight2);

        return flightList;
    }

    @PostMapping("/flights/tickets/total")
    public Result getTicketTotal(@RequestBody String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Ticket> tickets = mapper.readValue(json, Flight.class).tickets;

        Integer total = 0;

        for (int i = 0; i < tickets.size(); i++) {
            total = total + tickets.get(i).price;
        }

        Result flightTotal = new Result();
        flightTotal.setResult(total);

        return flightTotal;
    }

    static class Flight {
        // figure out how to make this a Date later
        private String departs;

        private List<Ticket> tickets = new ArrayList<>();

        @JsonProperty("Departs")
        public String getDeparts() {
            return departs;
        }

        @JsonProperty("Tickets")
        public List<Ticket> getTickets() {
            return tickets;
        }

        public void setDeparts(String departs) {
            this.departs = departs;
        }

        public void setTickets(String firstName, String lastName, int price) {
            // assemble a ticket
            Ticket ticket = new Ticket();
            ticket.setPassenger(firstName, lastName);
            ticket.setPrice(price);

            this.tickets.add(ticket);
        }
    }

    static class Ticket {
        private Person passenger;
        private int price;

        @JsonProperty("Passenger")
        public Person getPassenger() {
            return passenger;
        }

        @JsonProperty("Price")
        public int getPrice() {
            return price;
        }

        public void setPassenger(String firstName, String lastName) {
            Person newPassenger = new Person();
            newPassenger.setFirstName(firstName);
            newPassenger.setLastName(lastName);

            this.passenger = newPassenger;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    static class Person {
        private String firstName;
        private String lastName;

        @JsonProperty("FirstName")
        public String getFirstName() {
            return firstName;
        }

        @JsonProperty("LastName")
        public String getLastName() {
            return lastName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    static class Result {
        private Integer result;

        public void setResult(Integer input) {
            this.result = input;
        }

        public Integer getResult() {
            return this.result;
        }
    }

}
