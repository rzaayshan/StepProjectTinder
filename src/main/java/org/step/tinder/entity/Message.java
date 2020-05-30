package org.step.tinder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Message {
    private String from;
    private String to;
    private LocalDateTime time;
    private String mes;
}
