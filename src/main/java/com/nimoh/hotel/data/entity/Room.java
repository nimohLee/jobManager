package com.nimoh.hotel.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private int maxPeople;
    private int standardPeople;
    private int countOfRooms;
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable = true)
    private List<Amenity> amenities = new ArrayList<>();
}