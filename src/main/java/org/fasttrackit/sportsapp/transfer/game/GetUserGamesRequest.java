package org.fasttrackit.sportsapp.transfer.game;

public class GetUserGamesRequest {

    private Long activeUserId;

    public Long getActiveUserId() {
        return activeUserId;
    }

    public void setActiveUserId(Long activeUserId) {
        this.activeUserId = activeUserId;
    }

    @Override
    public String toString() {
        return "GetUserGamesRequest{" +
                "activeUserId=" + activeUserId +
                '}';
    }
}
