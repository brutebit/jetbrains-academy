package engine.controllers;

import engine.entities.User;
import engine.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@RestController
public class RegisterController {
  @Autowired
  UserRepository userRepo;

  @Autowired
  PasswordEncoder encoder;

  @PostMapping("/api/register")
  public ResponseEntity<User> register(@Valid @RequestBody RegisterUserReqBody body) {
    if (userRepo.findByEmail(body.email).isPresent())
      return ResponseEntity.badRequest().build();
    var user = new User();
    user.setEmail(body.email);
    user.setPassword(encoder.encode(body.password));
    return ResponseEntity.ok(userRepo.save(user));
  }

  static class RegisterUserReqBody {
    @Email(regexp = ".*\\..*")
    public String email;

    @Size(min = 5)
    public String password;
  }
}
