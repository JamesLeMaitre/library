package dev.jtm.library.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jtm.library.entities.security.AppRole;
import dev.jtm.library.entities.security.AppUsers;
import dev.jtm.library.repositories.security.AppUsersRepository;
import dev.jtm.library.security.request.LoginRequest;
import dev.jtm.library.security.request.RegisterRequest;
import dev.jtm.library.security.request.ResetPasswordRequest;
import dev.jtm.library.security.response.AppUserResponse;
import dev.jtm.library.security.response.JwtResponse;
import dev.jtm.library.security.utils.constants.JwtConstant;
import dev.jtm.library.services.security.AppUsersService;
import dev.jtm.library.utils.DataFormatter;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.jtm.library.security.utils.constants.JavaConstant.API_BASE_URL;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping(API_BASE_URL)
public class AuthRController extends DataFormatter<AppUserResponse> {
    private final AppUsersService userService;

    private final AppUsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;



    @PostMapping("register")
    public Object register(@RequestBody @Valid RegisterRequest registerRequest){
        try {
            return  renderData(true, userService.storeUser(registerRequest),"Create ");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }

    }

   @PostMapping("login")
    public Object login(@RequestBody() LoginRequest loginRequest){
        try {
            String token = userService.authenticate(loginRequest, authenticationManager);
            JwtResponse response = new JwtResponse();
            response.setAccess_token(token);
            String s = response.getAccess_token();
            return  renderStringData(true,s,"Create ");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }

    }

   /* @PostMapping(value = "login", consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest request) {
        JwtResponse response = new JwtResponse();
        String token = userService.authenticate(request, authenticationManager);
        response.setAccess_token(token);
        return ResponseEntity.ok().body(response);
    }*/

    @PostMapping("me")
    public Object remember(@RequestBody() AppUserResponse me){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AppUserResponse response = userService.authUser(authentication);

            return  renderData(true,response,"Create ");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }

    }



    @PutMapping(value = "edit/account")
    public Object update( @RequestBody RegisterRequest  request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AppUserResponse response = userService.updateUser(request, authentication);
            if( authentication.getName() ==null){
                return  renderStringData(false,"No User" ,"item not found");
            }
            return  renderData(true, response,"update done successfully");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }


/*    @DeleteMapping(value = "delete/{username}/account", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<?> remove(@PathVariable String username) {
        userService.removeUser(username);
        return ResponseEntity.ok().body("Suppresion reussie");
    }*/

    @DeleteMapping("delete/{username}/account")
    public Object delete(@PathVariable String username){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication.getName() == null){
                return  renderStringData(false,"No User to delete" ,"item not found");
            }
            userService.removeUser(username);
            return  renderStringData(true, "Delete successfully","Done");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }

 /*   @PutMapping(value = "reset/{username}/password", consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<AppUserResponse> updatePassword(@Valid @RequestBody ResetPasswordRequest request, @PathVariable String username) throws PasswordNotMatchException {
        AppUserResponse response = userService.resetPassword(request, username);
        return ResponseEntity.ok().body(response);
    }*/

    @PutMapping("reset/{username}/password")
    public Object updatePassword(@Valid @RequestBody ResetPasswordRequest request, @PathVariable String username){
        try {
            AppUserResponse response = userService.resetPassword(request, username);
            return  renderData(true,response,"Operation successfully ");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }

 /*   @GetMapping(value = "disabled/{username}/account")
    public ResponseEntity<AppUserResponse> disabled(@PathVariable String username) {
        AppUserResponse response = userService.disabledAccount(username);
        return ResponseEntity.ok().body(response);
    }*/

    @GetMapping("disabled/{username}/account")
    public Object disabled(@PathVariable String username){
        try {
            AppUserResponse response = userService.disabledAccount(username);
            return  renderData(true,response,"Operation successfully ");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }

/*    @GetMapping(value = "enabled/{username}/account")
    public ResponseEntity<AppUserResponse> enabled(@PathVariable String username) {
        AppUserResponse response = userService.enabledAccount(username);
        return ResponseEntity.ok().body(response);
    }*/

    @GetMapping("enabled/{username}/account")
    public Object getResponse(@PathVariable String username){
        try {
            AppUserResponse response = userService.enabledAccount(username);
            return  renderData(true,response,"Operation successfully ");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return  renderStringData(false,"Error while processing" ,exceptionAsString);
        }
    }

   /* @GetMapping(path = "/refreshToken")
    public void refreshoToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationToken = request.getHeader(JwtConstant.HEADER_STRING);
        if (authorizationToken != null && authorizationToken.startsWith(JwtConstant.TOKEN_PREFIX)) {
            try {
                String jwt = authorizationToken.substring(JwtConstant.TOKEN_PREFIX.length());
                Algorithm algo = Algorithm.HMAC256(JwtConstant.SECRET);
                JWTVerifier jWTVerifier = JWT.require(algo).build();
                DecodedJWT decodedJWT = jWTVerifier.verify(jwt);

                String username = decodedJWT.getSubject();

                AppUsers appUser = usersRepository.getCurrentUser(username);

                String jwtAccessToken = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstant.EXPIRATION_TIME))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roleName", appUser.getRoles().stream()
                                .map(AppRole::getRoleName)
                                .collect(Collectors.toList()))
                        .sign(algo);

                Map<String, String> idToken = new HashMap<>();
                idToken.put(JWTUtil.ACCESS_TOKEN, jwtAccessToken);
                idToken.put(JWTUtil.REFRESH_TOKEN, jwt);

                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);

            } catch (Exception ex) {
                response.setHeader("error-message", ex.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            throw new RestDefaultException("Refresh token required");
        }
    }*/
}
