package com.tschannerl.superoserver.models;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_task")
public class Task {
    public enum Status {INIT, RUN, COMPLETED};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(length = 1024)
    private String description;
    private Date dtCreate = Date.from(LocalDateTime.now().atZone(ZoneId.ofOffset("UTC", ZoneOffset.of("-00:00"))).toInstant());
    private Date dtUpdate = Date.from(LocalDateTime.now().atZone(ZoneId.ofOffset("UTC", ZoneOffset.of("-00:00"))).toInstant());
    private Date dtConclusion = null;
    private Status status = Status.INIT;

    public Task() {
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Task(Long id, String title, String description, Date dtUpdate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dtUpdate = dtUpdate;
    }

    public Task(Long id, Date dtUpdate, Date dtConclusion, Status status) {
        this.id = id;
        this.dtUpdate = dtUpdate;
        this.dtConclusion = dtConclusion;
        this.status = status;
    }
}
