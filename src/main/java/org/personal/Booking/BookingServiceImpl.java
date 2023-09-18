package org.personal.Booking;

import lombok.RequiredArgsConstructor;
import org.personal.Booking.dto.BookingDto;
import org.personal.Booking.dto.BookingDtoInput;
import org.personal.Booking.dto.BookingDtoShort;
import org.personal.Booking.dto.BookingMapper;
import org.personal.Item.Item;
import org.personal.Item.ItemService;
import org.personal.Item.dto.ItemDto;
import org.personal.Item.dto.ItemMapper;
import org.personal.User.User;
import org.personal.User.UserService;
import org.personal.User.dto.UserMapper;
import org.personal.exeption.BookingDataException;
import org.personal.exeption.BookingNotFoundException;
import org.personal.exeption.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ItemMapper itemMapper;
    private final ItemService itemService;

    @Override
    public BookingDto create(BookingDtoInput bookingDto, Long bookerId) {
        Booking booking = bookingMapper.fromDto(bookingDto, bookerId);
        User booker = userMapper.dtoToUser(userService.getUserById(bookerId));
        Item item = itemMapper.fromDto(itemService.getById(bookingDto.getItemId()));
        User owner = item.getOwner();

        if (booker.getId().equals(owner.getId())) {
            throw new BookingDataException("Owner can not book his own item");
        }
        if (!item.getAvailable()) {
            throw new BookingDataException("Selected item is currently unavailable");
        }
        if (booking.getStart().isBefore(LocalDateTime.now()) ||
                booking.getEnd().isBefore(LocalDateTime.now()) ||
                booking.getEnd().isBefore(booking.getStart())) {
            throw new BookingDataException("Booking start time or booking end time is incorrect");
        }
        booking.setBooker(booker);
        booking.setItem(item);

        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto update(Long bookingId, Long userId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new BookingNotFoundException("Booking with ID = " + bookingId + " not found"));

        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new UserNotFoundException("Item owner not found");
        }
        if (approved && booking.getStatus().equals(BookingStatus.APPROVED)) {
            throw new BookingDataException("Booking status already approved");
        }
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto getBookingById(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new BookingNotFoundException("No booking found with ID " + bookingId + " for user with ID " + userId + "."));
        Item item = booking.getItem();
        long bookerId = booking.getBooker().getId();
        long ownerId = item.getOwner().getId();
        if (bookerId != userId && ownerId != userId) {
            throw new BookingDataException("Only the item owner or the individual who booked the item can view this booking.");
        }
        return bookingMapper.toDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookingByUser(Long userId, String state) {
        userService.getUserById(userId);
        return switch (state) {
            case "ALL" -> mapList(bookingRepository.findAllByBooker_IdOrderByStartDesc(userId));
            case "CURRENT" ->
                    mapList(bookingRepository.findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(userId,
                            LocalDateTime.now(), LocalDateTime.now()));
            case "PAST" ->
                    mapList(bookingRepository.findAllByBooker_IdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now()));
            case "FUTURE" ->
                    mapList(bookingRepository.findAllByBooker_IdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now()));
            case "WAITING" ->
                    mapList(bookingRepository.findAllByBooker_IdAndStatusOrderByStart(userId, BookingStatus.WAITING));
            case "REJECTED" ->
                    mapList(bookingRepository.findAllByBooker_IdAndStatusOrderByStart(userId, BookingStatus.REJECTED));
            default -> throw new BookingDataException("Invalid booking state");
        };
    }

    @Override
    public List<BookingDto> getAllBookingByOwner(Long ownerId, String state) {
        userService.getUserById(ownerId);
        return switch (state) {
            case "ALL" -> mapList(bookingRepository.findAllByItem_Owner_IdOrderByStartDesc(ownerId));
            case "CURRENT" -> mapList(bookingRepository.findAllByItem_Owner_IdAndStartBeforeAndEndAfterOrderByStartDesc(
                    ownerId, LocalDateTime.now(), LocalDateTime.now()));
            case "PAST" ->
                    mapList(bookingRepository.findAllByItem_Owner_IdAndEndBeforeOrderByStartDesc(ownerId, LocalDateTime.now()));
            case "FUTURE" ->
                    mapList(bookingRepository.findAllByItem_Owner_IdAndStartAfterOrderByStartDesc(ownerId, LocalDateTime.now()));
            case "WAITING" ->
                    mapList(bookingRepository.findAllByItem_Owner_IdAndStatusOrderByStart(ownerId, BookingStatus.WAITING));
            case "REJECTED" ->
                    mapList(bookingRepository.findAllByItem_Owner_IdAndStatusOrderByStart(ownerId, BookingStatus.REJECTED));
            default -> throw new BookingDataException("Invalid booking state");
        };
    }

    @Override
    public Map<Item, List<BookingDtoShort>> getLastAndNextBooking(Long ownerId) {
        var map = new HashMap<Item, List<BookingDtoShort>>();
        itemService.getAll(ownerId).forEach(item -> {
            BookingDtoShort last = bookingMapper.toShortDto(bookingRepository.findFirstByItem_IdAndBooker_IdAndEndIsBefore(item.getId(), ownerId, LocalDateTime.now()));
            BookingDtoShort next = bookingMapper.toShortDto(bookingRepository.findFirstByItem_IdAndStartAfterOrderByStartAsc(item.getId(), LocalDateTime.now()));
            Item i = itemMapper.fromDto(item);
            map.put(i, new ArrayList<>());
            var innerMap = map.get(i);
            innerMap.add(last);
            innerMap.add(next);
        });
        return map;
    }

    private List<BookingDto> mapList(List<Booking> list) {
        return list.stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

}
