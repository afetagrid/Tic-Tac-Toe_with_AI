class Book {

    private String title;
    private int yearOfPublishing;
    private String[] authors;   

    public void setTitle(String title) {
        this.title = title;
    }
    public void setYearOfPublishing(int year) {
        this.yearOfPublishing = year;
    }
    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }
    public int getYearOfPublishing() {
        return yearOfPublishing;
    }
    public String[] getAuthors() {
        return authors;
    }
}
