package com.bugtracker.api.services.impl;

import com.bugtracker.api.dto.userdto.UserRequestDto;
import com.bugtracker.api.dto.authdto.AuthenticationRequestDto;
import com.bugtracker.api.dto.authdto.AuthenticationResponseDto;
import com.bugtracker.api.dto.tokendto.AccessTokenRequestDto;
import com.bugtracker.api.services.AuthenticationService;
import com.bugtracker.api.security.jwt.JwtTokenService;
import com.bugtracker.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Override
    public AuthenticationResponseDto authenticateUser(AuthenticationRequestDto authenticationRequestDto) {
        Authentication authentication = getAuthenticationFromUsernamePassword(
                        authenticationRequestDto.username(),
                        authenticationRequestDto.password()
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtTokenService.generateAccessToken(userDetails);
        String refreshToken = jwtTokenService.generateRefreshToken(userDetails);
        return new AuthenticationResponseDto(accessToken, refreshToken);
    }

    @Override
    public Authentication getAuthenticationFromUsernamePassword(String username, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
    }

    @Override
    public void registerUser(UserRequestDto userRequestDto) {
        userService.createUser(userRequestDto);
    }

    @Override
    public String refreshAccessToken(AccessTokenRequestDto accessTokenRequestDto) {
        return jwtTokenService.generateAccessToken(accessTokenRequestDto.refreshToken());
    }

}
