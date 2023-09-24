package org.personal.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterIdOrderByCreatedDesc(Long requesterId);
    Request findRequestByRequesterId(Long requesterId);
}
