package com.authms.application.service;

import com.authms.application.port.input.RegisterAccessUseCase;
import com.authms.application.port.output.IAccessRepository;
import com.authms.application.port.output.IPasswordEncoder;
import com.authms.domain.Access;
import com.authms.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
public class AccessService implements RegisterAccessUseCase {

      private final IAccessRepository accessRepository;
      private final IPasswordEncoder passwordEncoder;
      @Value("${security.password.default}")
      private String passwordDefault;

      @Override
      public Mono<Access> create(User user) {
            log.info("createAccess: " + user.toString());
            String baseUsername = buildBaseUsername(user);
            String password = this.passwordEncoder.encode(this.passwordDefault);
            log.info("baseUsername: " + baseUsername);

            return nextFreeUsername(baseUsername, 0)
                  .flatMap(username -> {
                        log.info("username: " + username);
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
