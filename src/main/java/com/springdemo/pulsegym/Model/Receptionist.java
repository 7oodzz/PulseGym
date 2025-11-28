package com.springdemo.pulsegym.Models;

@Entity
public class Receptionist {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;
}
