package org.fasttrackit.sportsapp.transfer;

import java.time.LocalDate;

public class GetEventsRequest {

    private String partialName;
    private LocalDate date;
    private String partialLocation;

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPartialLocation() {
        return partialLocation;
    }

    public void setPartialLocation(String partialLocation) {
        this.partialLocation = partialLocation;
    }

    @Override
    public String toString() {
        return "GetEventsRequest{" +
                "partialName='" + partialName + '\'' +
                ", date=" + date +
                ", partialLocation='" + partialLocation + '\'' +
                '}';
    }
}
