package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Singleton class to manage a database of Book objects
public class BookDatabase {

    // Static instance for singleton pattern
    private static BookDatabase instance;

    // List to store Book objects
    private List<org.example.Book> books;

    // Constant for the file name
    private static final String FILE_NAME = "books.txt";

    // Private constructor to prevent instantiation from other classes
    private BookDatabase() {
        books = new ArrayList<>();

        // Create the file if it does not exist
        File file = new File(FILE_NAME);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    // Method to get the singleton instance of BookDatabase
    public static synchronized BookDatabase getInstance() {
        if (instance == null) {
            instance = new BookDatabase();
        }
        return instance;
    }

    // Method to add a book to the database and save to file
    public void addBook(org.example.Book book) {
        books.add(book);
        saveBooksToFile(); // Save books to file immediately after adding a new book
    }

    // Method to remove a book from the database and save to file
    public void removeBook(org.example.Book book) {
        books.remove(book);
        saveBooksToFile(); // Save books to file immediately after removing a book
    }

    // Method to get the list of books from the database
    public List<org.example.Book> getBooks() {
        return books;
    }

    // Method to check if a book exists in the database
    public boolean containsBook(org.example.Book book) {
        for (org.example.Book b : books) {
            if (b.getType().equalsIgnoreCase(book.getType()) &&
                    b.getTitle().equalsIgnoreCase(book.getTitle()) &&
                    b.getAuthor().equalsIgnoreCase(book.getAuthor())) {
                return true;
            }
        }
        return false;
    }

    // Method to save the list of books to a file
    public void saveBooksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (org.example.Book book : books) {
                writer.write(book.getType() + "," + book.getTitle() + "," + book.getAuthor());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving books to file: " + e.getMessage());
        }
    }

    // Method to load the list of books from a file
    public void loadBooksFromFile() {
        books.clear(); // Clear the current list of books
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    books.add(new org.example.Book(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books from file: " + e.getMessage());
        }
    }
}