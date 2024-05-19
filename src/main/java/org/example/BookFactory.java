package org.example;

// Factory class to create Book objects
public class BookFactory {

    // Method to create a Book object with a specified type, title, and author
    public org.example.Book createBook(String type, String title, String author) {
        // Return a new Book object with the provided details
        return new org.example.Book(type, title, author);
    }
}
