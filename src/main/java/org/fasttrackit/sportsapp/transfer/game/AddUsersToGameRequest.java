package org.fasttrackit.sportsapp.transfer.game;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AddUsersToGameRequest {

    @NotNull
    private List<Long> userIds;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    @Override
    public String toString() {
        return "AddUsersToGameRequest{" +
                "userIds=" + userIds +
                '}';
    }
}
