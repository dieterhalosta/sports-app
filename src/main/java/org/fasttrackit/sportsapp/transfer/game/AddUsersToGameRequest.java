package org.fasttrackit.sportsapp.transfer.game;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AddUsersToGameRequest {

    @NotNull
    private List<Long> userIds;

}
