package com.example.medcheckb7.api;

import com.example.medcheckb7.db.entities.User;
import com.example.medcheckb7.db.entities.service.UserService;
import com.example.medcheckb7.dto.request.PasswordChangeRequest;
import com.example.medcheckb7.dto.request.UserRequest;
import com.example.medcheckb7.dto.response.SimpleResponse;
import com.example.medcheckb7.dto.response.UserResponse;
import com.example.medcheckb7.dto.response.UserResponseForGetResult;
import com.example.medcheckb7.dto.response.UserResponseResultList;
import com.example.medcheckb7.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "User Api")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get profile", description = "Get authenticated user")
    @GetMapping()
    public UserResponse getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.getUserById(user.getId());
    }

    @Operation(summary = "Get user", description = "Get user profile")
    @GetMapping("{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Update user", description = "Updating user data")
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public UserResponse updatingUserData(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        return userService.updateUser(userId, userRequest);
    }

    @Operation(summary = "Update password", description = "Change user password")
    @PutMapping("/password/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public SimpleResponse changeUserPassword(@PathVariable Long id, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        return userService.changeUserPassword(id, passwordChangeRequest);
    }

    @Operation(summary = "Get all users for result and Admin", description = "Return all users")
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponseForGetResult> getAllUsers() {
        List<UserResponseForGetResult> users = userService.getAllUsers();
        return ResponseEntity.ok(users).getBody();
    }

    @Operation(summary = "Get all users by id for Admin", description = "Returns a list of all registered users")
    @GetMapping("/usersForAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseResultList getAllUserId(@RequestParam(name = "userId") Long userId) {
        return userService.getUserByIdForAdmin(userId);
    }

    @Operation(summary = "delete User", description = "you can delete an user here by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteUser(@PathVariable Long id) throws NotFoundException {
        return userService.deleteUsers(id);
    }

    @Operation(summary = "Search users for result and Admin", description = "Return search users")
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponseForGetResult> getAllSearchUsers(@RequestParam(name = "text") String text) {
        List<UserResponseForGetResult> users = userService.searchUsers(text);
        return ResponseEntity.ok(users).getBody();
    }
}