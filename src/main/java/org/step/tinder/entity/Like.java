package org.step.tinder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Like {
    String who;
    String whom;
}
