package com.example.luxoft.luxofttable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LuxoftTable {
    @Id
    private String primaryKey;
    private String name;
    private String description;
    private Instant updatedTimestamp;
}
