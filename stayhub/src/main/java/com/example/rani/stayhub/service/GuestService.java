package com.example.rani.stayhub.service;

import java.util.List;
import com.example.rani.stayhub.dto.GuestDto;

public interface GuestService {

    List<GuestDto> getAllGuests();

    void updateGuest(Long guestId, GuestDto guestDto);

    void deleteGuest(Long guestId);

    GuestDto addNewGuest(GuestDto guestDto);
}
