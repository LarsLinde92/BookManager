package org.example;

// Class that implements the Observer interface to handle book additions
public class BookAddedObserver implements Observer {

    // Override the update method to perform actions when a book is added
    @Override
    public void update(org.example.Book book) {
        // Print a message to indicate a new book has been added
        System.out.println("New book added: " + book.getTitle() + " by " + book.getAuthor());
    }
}