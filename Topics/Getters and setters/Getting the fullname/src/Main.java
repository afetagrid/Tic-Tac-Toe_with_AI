class User {
    private String firstName;
    private String lastName;

    public User() {
        this.firstName = "";
        this.lastName = "";
    }

    public void setFirstName(String firstName) {
        // write your code here
        if (firstName != null && firstName != "") {
            this.firstName = firstName;
        }
    }

    public void setLastName(String lastName) {
        // write your code here
        if (lastName != null && lastName != "") {
            this.lastName = lastName;
        }
    }

    public String getFullName() {
        if (this.firstName == "" && this.lastName == "") {
            return "Unknown";
        } else if (this.firstName == "") {
            return this.lastName;
        } else if (this.lastName == "") {
            return this.firstName;
        } else {
            return this.firstName + " " + this.lastName;
        }
    }
}
