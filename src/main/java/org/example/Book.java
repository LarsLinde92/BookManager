package org.example;

// Class representing a Book, implementing Comparable for sorting by title
public class Book implements Comparable<Book> {
    private String genre;
    private String title;
    private String author;

    // Constructor to initialize a Book object
    public Book(String genre, String title, String author) {
        this.genre = genre;
        this.title = title;
        this.author = author;
    }

    // Getter method for genre
    public String getType() {
        return genre;
    }

    // Getter method for title
    public String getTitle() {
        return title;
    }

    // Getter method for author
    public String getAuthor() {
        return author;
    }

    // Override toString method to provide a string representation of a Book
    @Override
    public String toString() {
        return "Book{" + "genre='" + genre + '\'' + ", title='" + title + '\'' + ", author='" + author + '\'' + '}';
    }

    // Override compareTo method to compare books by title (case-insensitive)
    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }
}
