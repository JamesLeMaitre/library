package dev.jtm.library.servicesimpl.security;

import dev.jtm.library.entities.security.AppRole;
import dev.jtm.library.entities.security.AppUsers;
import dev.jtm.library.repositories.security.AppRoleRepository;
import dev.jtm.library.repositories.security.AppUsersRepository;
import dev.jtm.library.security.exceptions.PasswordNotMatchException;
import dev.jtm.library.security.exceptions.RoleNotFoundException;
import dev.jtm.library.security.request.LoginRequest;
import dev.jtm.library.security.request.RegisterRequest;
import dev.jtm.library.security.request.ResetPasswordRequest;
import dev.jtm.library.security.response.AppUserResponse;
import dev.jtm.library.security.utils.JwtUtils;
import dev.jtm.library.security.utils.UserPrincipal;
import dev.jtm.library.services.security.AppUsersService;
import dev.jtm.library.services.security.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImplementation implements AppUsersService {

    private final AppUsersRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppRoleRepository roleRepository;
    private final EmailService emailService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUsers> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Utilisateur n'existe pas"));
        return user.map(UserPrincipal::new).get();
    }

    @Override
    public String authenticate(LoginRequest request, AuthenticationManager authenticationManager) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        Optional<AppUsers> user = userRepository.findByUsername(request.getUsername());
        user.orElseThrow(() -> new UsernameNotFoundException("Utilisateur Non trouvée!"));
        return jwtUtils.generateJwtToken(user.map(UserPrincipal::new).get());
    }

    @Override
    public AppUserResponse authUser(Authentication authentication) {
        AppUserResponse response = new AppUserResponse();
        String username = authentication.getName();
        Optional<AppUsers> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Utilisateur Non trouvée!"));
        BeanUtils.copyProperties(user.get(), response);
        return response;
    }

    @Override
    public AppUserResponse storeUser(RegisterRequest registerRequest) throws RoleNotFoundException, MessagingException {
        AppUserResponse response = new AppUserResponse();
        registerRequest.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        AppUsers user = new AppUsers();
        BeanUtils.copyProperties(registerRequest, user);

        AppUsers newUser = userRepository.save(user);
        addRoleToUser(registerRequest.getRoleName(), newUser.getUsername());
        emailService.sendNewEmail(newUser.getUsername(), newUser.getEmail(), "Activez votre compte");

        BeanUtils.copyProperties(newUser, response);
        return response;
    }

    @Override
    public AppUserResponse updateUser(RegisterRequest request, Authentication authentication) {
        AppUserResponse response = new AppUserResponse();

        Optional<AppUsers> user = userRepository.findByUsername(authentication.getName());
        user.orElseThrow(() -> new UsernameNotFoundException("Utilisateur n'existe pas"));
        BeanUtils.copyProperties(request, user.get());

        AppUsers updateUser = userRepository.save(user.get());
        BeanUtils.copyProperties(updateUser, response);

        return response;
    }

    @Override
    public void removeUser(String username) {
        Optional<AppUsers> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Utilisateur n'existe pas"));
        userRepository.delete(user.get());
    }

    @Override
    public AppUserResponse resetPassword(ResetPasswordRequest request, String username) throws PasswordNotMatchException {
        AppUserResponse response = new AppUserResponse();

        Optional<AppUsers> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Utilisateur n'existe pas"));

        if (!bCryptPasswordEncoder.matches(request.getOld_password(), user.get().getPassword())){
            throw new PasswordNotMatchException("Votre ancien mot de passe saisi es incorrect");
        }
        if (!request.getNew_password().equals(request.getConfirm_password())){
            throw new PasswordNotMatchException("Veuillez saisir les même mot de passe");
        }

        user.get().setPassword(bCryptPasswordEncoder.encode(request.getNew_password()));
        AppUsers userPassword = userRepository.save(user.get());
        BeanUtils.copyProperties(userPassword, response);
        return response;
    }

    @Override
    public AppUserResponse disabledAccount(String username) {
        AppUserResponse response = new AppUserResponse();
        Optional<AppUsers> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Utilisateur n'existe pas"));

        user.get().setIsActive(false);
        AppUsers userPassword = userRepository.save(user.get());
        BeanUtils.copyProperties(userPassword, response);
        return response;
    }

    @Override
    public AppUserResponse enabledAccount(String username) {
        AppUserResponse response = new AppUserResponse();
        Optional<AppUsers> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Utilisateur n'existe pas"));

        user.get().setIsActive(true);
        AppUsers userPassword = userRepository.save(user.get());
        BeanUtils.copyProperties(userPassword, response);
        return response;
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return roleRepository.save(role);
    }

    public void addRoleToUser(String roleName, String username) throws RoleNotFoundException, UsernameNotFoundException {
        Optional<AppRole> role = roleRepository.findByRoleName(roleName);
        role.orElseThrow(() -> new RoleNotFoundException("Role n'existe pas"));
        Optional<AppUsers> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Utilisateur n'existe pas"));
        user.get().getRoles().add(role.get());
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
