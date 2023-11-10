package com.example.tooktook.component.jwt;

import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoService kakaoService;

    private static final String[] PERMIT_URL_ARRAY = {
            "/api/auth/kakao",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",

    };
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();

        for (String url : PERMIT_URL_ARRAY) {
            if (requestURI.startsWith(url)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            final String header = request.getHeader(AUTHORIZATION_HEADER);
            if (header == null || !header.startsWith("Bearer ")) {
                throw new GlobalException(ResponseCode.ErrorCode.WRONG_ACCESS_TOKEN_AUTH);
            }

            final String token = header.split(" ")[1].trim();
            if (jwtTokenProvider.isExpired(token)) {
                throw new GlobalException(ResponseCode.ErrorCode.EXPIRED_ACCESS_TOKEN);
            }

            String loginEmail = jwtTokenProvider.getMemberId(token);
            MemberDetailsDto member = kakaoService.loadMemberByEmail(loginEmail);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    member, null, member.getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info("Success");

        }
        catch (GlobalException e) {
            response.setStatus(e.getErrorCode().getStatus().value());
//            response.getWriter().write(e.getMessage());
        }
        catch (RuntimeException e) {

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal Server Error");
        }
            filterChain.doFilter(request,response);
    }

}
