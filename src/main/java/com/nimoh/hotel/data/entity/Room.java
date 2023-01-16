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
@Table
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private int maxPeople;
    private int standardPeople;
    private int countOfRooms;
    private String description;

    @OneToMany
    @JoinColumn(name = "amenity_id")
    private List<Amenity> amenities = new ArrayList<>();
}