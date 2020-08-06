package org.fasttrackit.sportsapp.transfer.user;

public class GetUserRequest {

    private String partialFirstName;
    private String partialLastName;
    private String partialEmail;
    private String partialNumber;

    public String getPartialFirstName() {
        return partialFirstName;
    }

    public void setPartialFirstName(String partialFirstName) {
        this.partialFirstName = partialFirstName;
    }

    public String getPartialLastName() {
        return partialLastName;
    }

    public void setPartialLastName(String partialLastName) {
        this.partialLastName = partialLastName;
    }

    public String getPartialEmail() {
        return partialEmail;
    }

    public void setPartialEmail(String partialEmail) {
        this.partialEmail = partialEmail;
    }

    public String getPartialNumber() {
        return partialNumber;
    }

    public void setPartialNumber(String partialNumber) {
        this.partialNumber = partialNumber;
    }

    @Override
    public String toString() {
        return "GetUserRequest{" +
                "partialFirstName='" + partialFirstName + '\'' +
                ", partialLastName='" + partialLastName + '\'' +
                ", partialEmail='" + partialEmail + '\'' +
                ", partialNumber=" + partialNumber +
                '}';
    }
}
