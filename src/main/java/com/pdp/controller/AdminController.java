package com.pdp.controller;

import com.pdp.dto.EventDTO;
import com.pdp.dto.MovieCreatingDTO;
import com.pdp.model.*;
import com.pdp.service.*;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since 07/August/2024  10:35
 **/
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class AdminController {

    private final MovieService movieService;
    private final CategoryService categoryService;
    private final MovieCategoryService movieCategoryService;
    private final ImageService imageService;
    private final MovieSceneImageService movieSceneImageService;
    private final UsersService usersService;
    private final CinemaHallService cinemaHallService;
    private final EventService eventService;
    private final AuthService authService;

    public AdminController(MovieService movieService, CategoryService categoryService, MovieCategoryService movieCategoryService, ImageService imageService, MovieSceneImageService movieSceneImageService, UsersService usersService, CinemaHallService cinemaHallService, EventService eventService, AuthService authService) {
        this.movieService = movieService;
        this.categoryService = categoryService;
        this.movieCategoryService = movieCategoryService;
        this.imageService = imageService;
        this.movieSceneImageService = movieSceneImageService;
        this.usersService = usersService;
        this.cinemaHallService = cinemaHallService;
        this.eventService = eventService;
        this.authService = authService;
    }

    @PostMapping("/event")
    public ModelAndView event(@ModelAttribute EventDTO dto) {
        LocalDateTime eventDateTime = LocalDateTime.of(dto.year(), dto.month(), dto.day(), dto.hour(), 0);
//        if (!isEventTimeValid(eventDateTime))
//            return redirectToErrorPage("The event must start at least 1 minutes from the current time.");
//        if (hasEventConflict(dto.cinemaHallId(), eventDateTime))
//            return redirectToErrorPage("The event must start at least 3 hours after the last scheduled event in this cinema hall.");
        saveEvent(dto, eventDateTime);
        return new ModelAndView("redirect:/admin/admin_pages");
    }


    @PostMapping("/delete-movie")
    public ModelAndView deleteMovie(@RequestParam("movieId") Integer movieID) {
        System.out.println(movieID);
        movieService.delete(movieID);
        return new ModelAndView("redirect:/admin/admin_pages");
    }

    @PostMapping("/block-user")
    public ModelAndView blockUser(@RequestParam("id") Integer id) {
        authService.blockUser(id);
        return new ModelAndView("redirect:/admin/admin_pages");
    }


    @PostMapping("/error")
    public ModelAndView error(@RequestParam("error") String error) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", error);
        modelAndView.setViewName("/admin/error");
        return modelAndView;
    }

    @GetMapping("/admin_pages")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("categoryList", categoryService.findAll());
        modelAndView.addObject("userList", usersService.findAll());
        modelAndView.addObject("movieList", movieService.findAllMovieDetails());
        modelAndView.setViewName("/admin/admin_pages");
        return modelAndView;
    }

    @SneakyThrows
    @PostMapping("/create_movie")
    public ModelAndView createMovie(@ModelAttribute MovieCreatingDTO dto) {
        int imageID = handleMovieImage(dto.movieImage());
        Integer movieID = createAndSaveMovie(dto, imageID);
        handleSceneImages(dto.sceneImages(), movieID);
        handleCategories(dto.categories(), movieID);
        return new ModelAndView("redirect:/admin/admin_pages");
    }


    @GetMapping("/event/{id}")
    public ModelAndView createCinema(@PathVariable(name = "id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("movieID", id);
        modelAndView.addObject("cinemaDetails", cinemaHallService.findAllCinemaDetails());
        modelAndView.setViewName("/admin/event");
        return modelAndView;
    }

    private int handleMovieImage(MultipartFile movieImage) {
        String movieImageName = generateUniqueFileName(movieImage);
        saveFileToDirectory(movieImage, movieImageName);

        Image image = Image.builder()
                .extension("/static/img/")
                .generatedName(movieImageName)
                .fileName(movieImage.getOriginalFilename())
                .fileType(StringUtils.getFilenameExtension(movieImage.getOriginalFilename()))
                .build();
        return imageService.save(image);
    }

    private Integer createAndSaveMovie(MovieCreatingDTO dto, int imageID) {
        Movie movie = Movie.builder()
                .title(dto.title())
                .director(dto.director())
                .description(dto.description())
                .duration(dto.duration())
                .language(dto.language())
                .rating(dto.rating())
                .thrillerLink(dto.thrillerLink())
                .releaseDate(dto.releaseDate())
                .imageID(imageID)
                .build();
        return movieService.save(movie);
    }

    private void handleSceneImages(Iterable<MultipartFile> sceneImages, Integer movieID) {
        for (MultipartFile sceneImage : sceneImages) {
            String sceneImageName = generateUniqueFileName(sceneImage);
            saveFileToDirectory(sceneImage, sceneImageName);

            Image image = Image.builder()
                    .extension("/static/img/")
                    .generatedName(sceneImageName)
                    .fileName(sceneImage.getOriginalFilename())
                    .fileType(StringUtils.getFilenameExtension(sceneImage.getOriginalFilename()))
                    .build();
            Integer imageID = imageService.save(image);

            MovieSceneImage movieSceneImage = MovieSceneImage.builder()
                    .imageID(imageID)
                    .movieID(movieID)
                    .build();
            movieSceneImageService.save(movieSceneImage);
        }
    }

    private void handleCategories(Iterable<Integer> categories, Integer movieID) {
        for (Integer categoryID : categories) {
            MovieCategory movieCategory = MovieCategory.builder()
                    .categoryID(categoryID)
                    .movieID(movieID)
                    .build();
            movieCategoryService.save(movieCategory);
        }
    }

    private String generateUniqueFileName(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        return uuid + "." + extension;
    }

    @SneakyThrows
    private void saveFileToDirectory(MultipartFile file, String fileName) {
        Path directoryPath = Paths.get("/Users/mac/Desktop/IntelliJ IDEA/NetflixClone/src/main/resources/static/img");
        Path filePath = directoryPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    private boolean isEventTimeValid(LocalDateTime eventDateTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        return eventDateTime.isAfter(currentTime.plusHours(1));
    }

    private boolean hasEventConflict(int cinemaHallId, LocalDateTime eventStartTime) {
        return eventService.hasEventConflict(cinemaHallId, eventStartTime);
    }

    private void saveEvent(EventDTO dto, LocalDateTime eventDateTime) {
        CinemaHall cinemaHall = cinemaHallService.findById(dto.cinemaHallId());
        Event event = Event.builder()
                .capacity(cinemaHall.getCapacity())
                .cinemaHallID(dto.cinemaHallId())
                .movieID(dto.movieId())
                .status(cinemaHall.getStatus())
                .price(dto.price())
                .showTime(eventDateTime)
                .description(dto.description())
                .build();
        eventService.save(event);
    }

    private ModelAndView redirectToErrorPage(String errorMessage) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", errorMessage);
        modelAndView.setViewName("redirect:/admin/error?error=" + errorMessage);
        return modelAndView;
    }
}
