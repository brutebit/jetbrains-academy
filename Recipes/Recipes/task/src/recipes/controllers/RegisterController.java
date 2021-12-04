package recipes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.entities.User;
import recipes.gateways.UserGateway;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Validated
@RestController
public class RegisterController {

  private final UserGateway userGateway;
  private final PasswordEncoder encoder;

  public RegisterController(UserGateway userGateway, PasswordEncoder encoder) {
    this.userGateway = userGateway;
    this.encoder = encoder;
  }

  @PostMapping("/api/register")
  public ResponseEntity register(@Valid @RequestBody RegisterUserReqBody body) {
    System.out.println(body.email);
    System.out.println(body.password);
    if (userGateway.findByEmail(body.email).isPresent())
      return ResponseEntity.badRequest().build();
    var user = new User();
    user.setEmail(body.email);
    user.setPassword(encoder.encode(body.password));
    userGateway.save(user);
    return ResponseEntity.ok().build();
  }

  static class RegisterUserReqBody {
    @Email(regexp = ".*\\..*")
    public String email;

    @Size(min = 8)
    @NotBlank
    public String password;
  }
}
