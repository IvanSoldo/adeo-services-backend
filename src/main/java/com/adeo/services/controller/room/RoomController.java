package com.adeo.services.controller.room;

import com.adeo.services.controller.AdeoErrorMessage;
import com.adeo.services.entity.Room;
import com.adeo.services.service.room.RoomNotFoundException;
import com.adeo.services.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_RECEPTIONIST"})
    @GetMapping("api/v1/rooms")
    public RoomResponseList getAllRooms() {
        List<Room> rooms = this.roomService.getAll();
        return RoomResponseList.from(rooms);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("api/v1/rooms")
    @ResponseStatus(HttpStatus.OK)
    public RoomCreateResponse save(@RequestBody @Valid RoomSaveRequest roomSaveRequest) {
        Room room = roomSaveRequest.from();
        String roomId = this.roomService.save(room);
        return new RoomCreateResponse(roomId);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("api/v1/rooms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") String id, @RequestBody @Valid RoomUpdateRequest roomUpdateRequest) {
        Room room = roomUpdateRequest.from(id);
        this.roomService.update(room);
    }

    @GetMapping("api/v1/rooms/{id}")
    public RoomResponse getRoomDetails(@PathVariable("id") String id) {
        Room room = roomService.getById(id);
        return RoomResponse.from(room);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("api/v1/rooms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        this.roomService.delete(id);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    AdeoErrorMessage handleRoomNotFound(RoomNotFoundException e) {
        return new AdeoErrorMessage(e.getMessage());
    }

}
