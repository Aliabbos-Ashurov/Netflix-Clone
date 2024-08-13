package com.pdp.controller;

import com.pdp.config.security.CustomUserDetailService;
import com.pdp.config.security.CustomUserDetails;
import com.pdp.dto.SignUpDTO;
import com.pdp.model.UserRoles;
import com.pdp.model.Users;
import com.pdp.service.TicketService;
import com.pdp.service.UserRolesService;
import com.pdp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  08:49
 **/
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRolesService userRolesService;
    private final UsersService usersService;
    private final TicketService ticketService;

    @Autowired
    public AuthController(UsersService usersService, UserRolesService userRolesService, TicketService ticketService) {
        this.usersService = usersService;
        this.userRolesService = userRolesService;
        this.ticketService = ticketService;
    }

    @GetMapping("/profile")
    public ModelAndView profile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/auth/profile");
        Users user = customUserDetails.getUser();
        modelAndView.addObject("tickets", ticketService.getUserTickets(user.getId()));
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/logout")
    private ModelAndView logOut() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/auth/logout");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(name = "error", required = false) String errorMessage) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/auth/login");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView signUp() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/auth/signup");
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView signUp(@ModelAttribute SignUpDTO signUpDTO) {
        Integer userID = usersService.saveFromDTO(signUpDTO);
        userRolesService.save(UserRoles.builder()
                .roleID(1) // DEFAULT -> USER
                .userID(userID)
                .build());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/main/home");
        return modelAndView;
    }
}
