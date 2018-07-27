package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;

@RestController
public class FlightService {

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

    class Flight {
        // figure out how to make this a Date later
        @JsonProperty("Departs")
        private String departs;

        @JsonProperty("Tickets")
        private List<Ticket> tickets = new ArrayList<>();

        public String getDeparts() {
            return departs;
        }

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

        class Ticket {
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

        class Person {
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
    }

}
