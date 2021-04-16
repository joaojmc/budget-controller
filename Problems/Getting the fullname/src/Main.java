class User {
    private String firstName;
    private String lastName;

    public User() {
        this.firstName = "";
        this.lastName = "";
    }

    public void setFirstName(String firstName) {
        this.firstName = java.util.Objects.requireNonNullElse(firstName, "");
    }

    public void setLastName(String lastName) {
        this.lastName = java.util.Objects.requireNonNullElse(lastName, "");
    }

    public String getFullName() {
        if (this.firstName.isBlank() && this.lastName.isBlank()) {
            return "Unknown";
        } else if (this.firstName.isBlank()) {
            return this.lastName;
        } else if (this.lastName.isBlank()) {
            return this.firstName;
        } else {
            return firstName + " " + lastName;
        }
    }
}