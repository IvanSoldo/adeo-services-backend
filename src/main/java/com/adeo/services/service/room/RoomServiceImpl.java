package com.adeo.services.service.room;

import com.adeo.services.entity.Room;
import com.adeo.services.repository.OrderRepository;
import com.adeo.services.repository.RoomRepository;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;
  private final OrderRepository orderRepository;

  public RoomServiceImpl(RoomRepository roomRepository, OrderRepository orderRepository) {
    this.roomRepository = roomRepository;
    this.orderRepository = orderRepository;
  }

  @Override
  public List<Room> getAll() {
    return roomRepository.findAllByIsDeletedFalse();
  }

  @Override
  public String save(Room room) {
    UUID uuid = UUID.randomUUID();
    String processedUuid = uuid.toString().replace("-", "");
    room.setUuid(processedUuid);
    Room savedRoom = this.roomRepository.save(room);
    return savedRoom.getUuid();
  }

  @Override
  public void update(Room room) {
    Room roomToUpdate = this.roomRepository.findByUuidAndIsDeletedFalse(room.getUuid())
        .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + room.getUuid()));
    roomToUpdate.update(room);
    this.roomRepository.save(roomToUpdate);
  }

  @Transactional
  @Override
  public void delete(String id) {
    boolean isRoomExist = this.roomRepository.existsByUuidAndIsDeletedFalse(id);

    if (!isRoomExist) {
      throw new RoomNotFoundException("Room not found with id: " + id);
    }

    boolean isOrdersExist = orderRepository.existsByRoomUuid(id);

    if (isOrdersExist) {
      Room room = this.roomRepository.findByUuidAndIsDeletedFalse(id)
          .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + id));

      room.setDeleted(true);
      roomRepository.save(room);

    } else {
      this.roomRepository.removeByUuid(id);
    }

  }

  @Override
  public Room getById(String id) {
    Room room = this.roomRepository.findByUuidAndIsDeletedFalse(id)
        .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + id));
    return room;
  }
}
