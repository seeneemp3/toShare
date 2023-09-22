package org.personal.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBooker_IdOrderByStartDesc(Long bookerId);
    List<Booking> findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(
            Long bookerId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByBooker_IdAndEndBeforeOrderByStartDesc(Long bookerId, LocalDateTime end);

    List<Booking> findAllByBooker_IdAndStartAfterOrderByStartDesc(Long bookerId, LocalDateTime start);

    List<Booking> findAllByBooker_IdAndStatusOrderByStart(Long bookerId, BookingStatus status);

    List<Booking> findAllByItem_Owner_IdOrderByStartDesc(Long itemOwnerId);

    List<Booking> findAllByItem_Owner_IdAndStartBeforeAndEndAfterOrderByStartDesc(
            Long itemOwnerId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByItem_Owner_IdAndEndBeforeOrderByStartDesc(Long itemOwnerId, LocalDateTime end);

    List<Booking> findAllByItem_Owner_IdAndStartAfterOrderByStartDesc(Long itemOwnerId, LocalDateTime start);

    List<Booking> findAllByItem_Owner_IdAndStatusOrderByStart(Long itemOwnerId, BookingStatus status);

    Booking findFirstByItem_IdAndStartAfterOrderByStartAsc(Long itemId, LocalDateTime now);

    Booking findFirstByItem_IdAndBooker_IdAndEndIsBefore(Long itemId, Long userId,
                                                                  LocalDateTime now);
}
