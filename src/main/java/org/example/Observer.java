package org.example;

// Observer interface to be implemented by classes that need to respond to changes in Book objects
public interface Observer {

    // Method to be called when a Book object is updated
    void update(org.example.Book book);
}