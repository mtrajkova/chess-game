package com.project.chess.model.dto;

import org.springframework.http.HttpHeaders;

public class JwtAuthenticationResponse {
    private ActiveUserDto activeUserDto;
    private HttpHeaders httpHeaders = new HttpHeaders();

    public JwtAuthenticationResponse() {

    }

    public JwtAuthenticationResponse(ActiveUserDto activeUserDto, String headerName, String jwt) {
        this.activeUserDto = activeUserDto;
        this.httpHeaders.add(headerName, jwt);
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public ActiveUserDto getActiveUserDto() {
        return activeUserDto;
    }

    public void setActiveUserDto(ActiveUserDto activeUserDto) {
        this.activeUserDto = activeUserDto;
    }
}
