package com.pdp.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pdp.config.security.CustomUserDetails;
import com.pdp.dto.EventCinemaDTO;
import com.pdp.dto.MovieDetailsDTO;
import com.pdp.model.CinemaHall;
import com.pdp.model.Event;
import com.pdp.model.Ticket;
import com.pdp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  12:16
 **/
@Controller
@RequestMapping("/main")
public class MainController {

    private final MovieService movieService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final CinemaHallService cinemaHallService;
    private final TicketService ticketService;

    @Autowired
    public MainController(MovieService movieService, CategoryService categoryService, EventService eventService, CinemaHallService cinemaHallService, TicketService ticketService) {
        this.movieService = movieService;
        this.categoryService = categoryService;
        this.eventService = eventService;
        this.cinemaHallService = cinemaHallService;
        this.ticketService = ticketService;
    }

    //@PreAuthorize("hasAuthority(T(uz.pdp.springframeworkcore.config.security.Permissions).HAS
    @GetMapping("/home")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("movieList", movieService.getMoviesGroupedByCategory());
        modelAndView.addObject("categoryList", categoryService.findAll());
        modelAndView.addObject("lastMovie", movieService.findAllMovieDetails().getFirst());
        modelAndView.setViewName("/main/home");
        return modelAndView;
    }

    @GetMapping("/movies")
    public ModelAndView movies() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("movieList", movieService.findAllMovieDetails());
        modelAndView.setViewName("/main/movies");
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/search")
    public List<MovieDetailsDTO> search(@RequestParam("query") String query) {
        System.out.println(query);
        return movieService.findAllMovieDetails().stream()
                .filter(movie -> movie.movieTitle().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    @PostMapping("/movies/{category_param}")
    public ModelAndView moviesPost(@PathVariable("category_param") String categoryName) {
        ModelAndView modelAndView = new ModelAndView();
        List<MovieDetailsDTO> moviesByCategory = movieService.findAllMovieDetailsByCategory(categoryName);
        modelAndView.addObject("movieList", moviesByCategory);
        modelAndView.setViewName("/main/movies");
        return modelAndView;
    }

    @PostMapping("/movie-info")
    public ModelAndView movieInfoPost(@RequestParam("movie_id") Integer movieId) {
        ModelAndView modelAndView = new ModelAndView();
        MovieDetailsDTO movieDetails = movieService.findMovieDetailsById(movieId);
        List<EventCinemaDTO> upcomingEvents = eventService.findUpcomingEventByMovieId(movieId);
        modelAndView.addObject("movie", movieDetails);
        modelAndView.addObject("thriller", movieDetails.movieThrillerLink());
        modelAndView.addObject("upcomingEvents", upcomingEvents);
        modelAndView.addObject("movieSceneImages", movieDetails.sceneImages());
        modelAndView.setViewName("/main/movie-info");
        return modelAndView;
    }

    @GetMapping("/event-zone")
    public ModelAndView getEventZone() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/main/event_zone");
        return modelAndView;
    }

    @PostMapping("/event-zone")
    public ModelAndView postEventZone(@RequestParam("movie_id") Integer movieID) {
        ModelAndView modelAndView = new ModelAndView();
        List<EventCinemaDTO> events = eventService.findUpcomingEventByMovieId(movieID);
        modelAndView.addObject("events", events);
        modelAndView.addObject("movieId", movieID);
        modelAndView.setViewName("/main/event_zone");
        return modelAndView;
    }

    @PostMapping("/ticket-success")
    public ModelAndView ticketBuying(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam("cinema_hall_id") int cinemaHallID,
            @RequestParam("movie_id") int movieID,
            @RequestParam("row") int row,
            @RequestParam("column") int column,
            @RequestParam("eventID") int eventID
    ) {
        Event event = eventService.findById(eventID);

        Ticket ticket = Ticket.builder()
                .movieID(movieID)
                .cinema_hall_id(cinemaHallID)
                .price(event.getPrice())
                .showTime(event.getShowTime())
                .paymentMethod("UNKNOWN PAYMENT METHOD")
                .status(event.getStatus())
                .rowSeat(row)
                .columnSeat(column)
                .userID(customUserDetails.getUser().getId())
                .build();
        ticketService.save(ticket);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(event.getCapacity(), JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("seats");
        Type type = new TypeToken<int[][]>() {
        }.getType();
        int[][] seats = gson.fromJson(jsonArray, type);
        seats[row - 1][column - 1] = 1;
        JsonObject updatedCapacity = new JsonObject();
        updatedCapacity.add("seats", gson.toJsonTree(seats));

        String json = gson.toJson(updatedCapacity);
        event.setCapacity(json);
        eventService.update(event);

        return new ModelAndView("redirect:/auth/profile");
    }

    @GetMapping("/book-ticket")
    public ModelAndView bookTicket() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/main/book_ticket");
        return modelAndView;
    }

    @PostMapping("/book-ticket")
    public ModelAndView postBookTicket(@RequestParam("movie_id") Integer movieID, @RequestParam("event_id") Integer eventID) {
        ModelAndView modelAndView = new ModelAndView();
        Event event = eventService.findById(eventID);
        CinemaHall cinemaHall = cinemaHallService.findById(event.getCinemaHallID());
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(event.getCapacity(), JsonObject.class);
        JsonArray seatsArray = jsonObject.getAsJsonArray("seats");
        Type seatArrayType = new TypeToken<int[][]>() {
        }.getType();
        int[][] seats = gson.fromJson(seatsArray, seatArrayType);
        modelAndView.addObject("eventID", eventID);
        modelAndView.addObject("movieID", movieID);
        modelAndView.addObject("cinemaHall", cinemaHall);
        modelAndView.addObject("seats", seats);
        modelAndView.setViewName("/main/book_ticket");
        return modelAndView;
    }
}
