package com.authms.application.service;

import com.authms.application.port.input.LoginUseCase;
import com.authms.application.port.input.RefreshTokenUseCase;
import com.authms.application.port.input.RegisterUserUseCase;
import com.authms.application.port.output.IAccessRepository;
import com.authms.application.port.output.IUserRepository;
import com.authms.domain.Access;
import com.authms.domain.AuditActionType;
import com.authms.domain.Token;
import com.authms.domain.User;
import com.authms.domain.exception.UserAlreadyExistsException;
import com.authms.domain.mapper.DomainMapper;
import com.authms.infrastructure.config.AuditingConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements LoginUseCase, RefreshTokenUseCase, RegisterUserUseCase {

      private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());
      private final IUserRepository userRepository;
      private final IAccessRepository accessRepository;
      private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      @Value("${security.password.default}")
      private String passwordDefault;

      @Override
      public Mono<Token> login(String username, String password) {
            return null;
      }

      @Override
      public Mono<Token> refreshToken(String refreshToken) {
            return null;
      }

      @Override
      public Mono<User> registerUser(User user) {
            logger.info("registerUser: " + user.toString());
            return this.userRepository.existsByDni(user.getDni())
                  .filter(exists -> !exists)
                  .switchIfEmpty(Mono.error(new UserAlreadyExistsException("Ya existe un usuario con ese dni registrado")))
                  .then(createAccess(user))
                  .map(acc -> {
                        user.setAccess(acc);
                        return user;
                  })
                  .flatMap(user1 -> {
                        AuditingConfig.setAuditor(AuditActionType.SELF_REGISTRATION.getValue());
                        return this.userRepository.save(user1)
                              .doFinally(s -> AuditingConfig.clearAuditor());
                  });
      }

      public Mono<Access> createAccess(User user) {
            logger.info("createAccess: " + user.toString());
            String baseUsername = buildBaseUsername(user);
            String password = this.passwordEncoder.encode(this.passwordDefault);
            logger.info("baseUsername: " + baseUsername);

            return nextFreeUsername(baseUsername, 0)
                  .flatMap(username -> {
                        logger.info("username: " + username);
                        return this.accessRepository.save(Access.builder()
                              .password(password)
                              .username(username)
                              .accessEnabled(true)
                              .build());
                  });
      }

      /**
       * Builds the base username using the following convention:
       *
       * <ul>
       *   <li><strong>First initial of the given name</strong> (lower-case)</li>
       *   <li><strong>Full paternal surname</strong> (lower-case, no spaces)</li>
       *   <li><strong>First two letters of the maternal surname</strong>; if the maternal
       *       surname is shorter than two letters, all available characters are used</li>
       * </ul>
       *
       * <p>All pieces are concatenated without separators and converted to lower-case.</p>
       *
       * <h4>Example</h4>
       * <pre>
       * Given name         : Ariana
       * Paternal surname   : Robles
       * Maternal surname   : Flores
       *
       * Resulting username : aroblesfl
       * </pre>
       *
       * @param user a {@link User} with non-null, non-blank fields
       *             {@code name}, {@code surnamePaternal}, and {@code surnameMaternal}
       * @return a lower-case string that represents the base username
       * @throws NullPointerException if {@code user} or any required field is {@code null}
       * @implNote This method does not strip diacritics or special characters.  Normalize
       * them before calling if your business rules require it.
       */
      private static String buildBaseUsername(User user) {
            String firstInitial = user.getName().trim().toLowerCase().substring(0, 1);
            String paternal = user.getSurnamePaternal().trim().toLowerCase();
            String maternalPref = user.getSurnameMaternal()
                  .trim().toLowerCase()
                  .substring(0, Math.min(2, user.getSurnameMaternal().length()));
            return firstInitial + paternal + maternalPref; // ej. nramirezhe
      }

      /**
       * Recursively finds the first available username derived from a given base.
       * <p>
       * The algorithm appends an incremental numeric suffix to the base until
       * {@code accessRepository.existsByUsername(String)} returns {@code false}.
       * Evaluation is fully <em>lazy</em>: each existence check is only executed
       * when the resulting {@link Mono} is subscribed.
       * </p>
       *
       * <h4>Examples</h4>
       * <pre>
       * base = "nramirezhe"
       * ├─ nramirezhe   (taken)  → tries next
       * ├─ nramirezhe1  (taken)  → tries next
       * └─ nramirezhe2  (free)   → emits "nramirezhe2"
       * </pre>
       *
       * @param base   the username without numeric suffix (e.g. {@code "nramirezhe"})
       * @param suffix the numeric counter to append; use {@code 0} for the first call
       * @return a {@code Mono<String>} that emits the first free username when subscribed
       */
      private Mono<String> nextFreeUsername(String base, int suffix) {
            String candidate = suffix == 0 ? base : base + suffix;
            return accessRepository.existsByUsername(candidate)
                  .flatMap(exists -> exists
                        ? this.nextFreeUsername(base, suffix + 1)
                        : Mono.just(candidate));
      }


}
