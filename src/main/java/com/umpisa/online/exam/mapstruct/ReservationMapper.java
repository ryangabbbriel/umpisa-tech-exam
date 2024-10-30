package com.umpisa.online.exam.mapstruct;

import com.umpisa.online.exam.dto.request.ReservationRequest;
import com.umpisa.online.exam.entity.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    Reservation map(ReservationRequest request);
}
