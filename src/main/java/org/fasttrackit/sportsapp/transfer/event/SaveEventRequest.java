package org.fasttrackit.sportsapp.transfer.event;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class SaveEventRequest {

    @NotNull
    private String name;
    @NotNull
    private String description;
    private int participants;
    @NotNull
    private LocalDate date;
    @NotNull
    private String location;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "SaveEventRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", participants=" + participants +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
