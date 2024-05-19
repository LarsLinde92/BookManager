package org.example;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

// Main application class to manage books
public class BookManagerApp {

    // Fields for various components of the application
    private BookDatabase database;
    private BookFactory factory;
    private BookAddedObserver observer;
    private SortingAlgorithm sortingAlgorithm;

    // Constructor to initialize the components
    public BookManagerApp() {
        database = BookDatabase.getInstance();
        factory = new BookFactory();
        observer = new BookAddedObserver();
        sortingAlgorithm = new SortingAlgorithm();
    }

    // Method to run the application
    public void run() {
        database.loadBooksFromFile(); // Load books from file at startup
        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            // Display the main menu options
            System.out.println("Welcome to BookManager! Please choose an option:");
            System.out.println("1. Add a new book");
            System.out.println("2. Search for books");
            System.out.println("3. Remove a book");
            System.out.println("4. Exit");
            command = scanner.nextLine();

            // Handle user commands
            switch (command) {
                case "1":
                    addBook(scanner);
                    break;

                case "2":
                    displayAvailableOptions();
                    searchBooks(scanner);
                    break;

                case "3":
                    removeBook(scanner);
                    break;

                case "4":
                    System.out.println("Exiting the application. Saving books to file...");
                    database.saveBooksToFile(); // Save books to file before exiting
                    System.out.println("Books updated in the file.");
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
                    break;
            }
        } while (!command.equals("4"));
    }

    // Method to add a new book
    private void addBook(Scanner scanner) {
        String type, title, author;

        // Get the book type from the user
        while (true) {
            System.out.println("Enter book type (Genre) or 0 to cancel:");
            type = scanner.nextLine();
            if (type.equals("0")) {
                System.out.println("Cancelled. Returning to the main menu.");
                return;
            }
            if (isValidInput(type)) break;
            System.out.println("Invalid input. Book type must be at least 2 letters and contain only letters and spaces.");
        }

        // Get the book title from the user
        while (true) {
            System.out.println("Enter book title or 0 to cancel:");
            title = scanner.nextLine();
            if (title.equals("0")) {
                System.out.println("Cancelled. Returning to the main menu.");
                return;
            }
            if (isValidInput(title)) break;
            System.out.println("Invalid input. Book title must be at least 2 letters and contain only letters and spaces.");
        }

        // Get the book author from the user
        while (true) {
            System.out.println("Enter book author or 0 to cancel:");
            author = scanner.nextLine();
            if (author.equals("0")) {
                System.out.println("Cancelled. Returning to the main menu.");
                return;
            }
            if (isValidInput(author)) break;
            System.out.println("Invalid input. Book author must be at least 2 letters and contain only letters and spaces.");
        }

        // Create and add the book to the database if it does not already exist
        org.example.Book book = factory.createBook(type, title, author);
        if (!database.containsBook(book)) {
            database.addBook(book);
            observer.update(book);
            database.saveBooksToFile(); // Save books to file immediately after adding a new book
        } else {
            System.out.println("Duplicate book. This book already exists in the database.");
        }
    }

    // Method to remove a book
    private void removeBook(Scanner scanner) {
        List<org.example.Book> books = database.getBooks();

        if (books.isEmpty()) {
            System.out.println("No books available to remove.");
            return;
        }

        // Display the available books
        System.out.println("Available books:");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
        System.out.println("Enter the number of the book to remove or 0 to cancel:");

        int bookIndex;
        // Get the index of the book to remove from the user
        while (true) {
            String input = scanner.nextLine();
            try {
                bookIndex = Integer.parseInt(input) - 1;
                if (bookIndex == -1) {
                    System.out.println("Cancelled. Returning to the main menu.");
                    return;
                }
                if (bookIndex >= 0 && bookIndex < books.size()) {
                    break;
                } else {
                    System.out.println("Invalid number. Please enter a number between 1 and " + books.size() + ", or 0 to cancel.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Remove the selected book from the database
        org.example.Book book = books.get(bookIndex);
        database.removeBook(book);
        System.out.println("Book removed: " + book);
    }

    // Method to validate user input
    private boolean isValidInput(String input) {
        return input != null && input.matches("^[a-zA-Z ]{2,}$");
    }

    // Method to search for books
    private void searchBooks(Scanner scanner) {
        System.out.println("Enter search query (Genre, Title, or Author) or 0 to cancel:");
        String query = scanner.nextLine().toLowerCase();

        if (query.equals("0")) {
            System.out.println("Cancelled. Returning to the main menu.");
            return;
        }

        if (query.trim().isEmpty()) {
            System.out.println("You need to write the Genre, Title, or Author.");
            return;
        }

        // Sort books alphabetically before searching
        List<org.example.Book> books = database.getBooks();
        sortingAlgorithm.quickSort(books, 0, books.size() - 1);

        boolean found = false;
        // Search for books matching the query
        for (org.example.Book book : books) {
            if (book.getTitle().toLowerCase().contains(query) ||
                    book.getAuthor().toLowerCase().contains(query) ||
                    book.getType().toLowerCase().contains(query)) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found.");
        }
    }

    // Method to display available genres, titles, and authors
    private void displayAvailableOptions() {
        Set<String> genres = new HashSet<>();
        Set<String> titles = new HashSet<>();
        Set<String> authors = new HashSet<>();

        // Collect genres, titles, and authors from the books
        for (org.example.Book book : database.getBooks()) {
            genres.add(book.getType());
            titles.add(book.getTitle());
            authors.add(book.getAuthor());
        }

        System.out.println("");
        System.out.println("Available Genres: ");
        printInColumns(genres, 50);
        System.out.println("");
        System.out.println("Available Titles: ");
        printInColumns(titles, 50);
        System.out.println("");
        System.out.println("Available Authors: ");
        printInColumns(authors, 50);
        System.out.println("");
    }

    // Method to print items in columns with a maximum line length
    private void printInColumns(Set<String> items, int maxLineLength) {
        StringBuilder sb = new StringBuilder();
        int lineLength = 0;
        for (String item : items) {
            if (lineLength + item.length() > maxLineLength) {
                System.out.println(sb.toString());
                sb.setLength(0);
                lineLength = 0;
            }
            sb.append(item).append(", ");
            lineLength += item.length() + 2;
        }
        if (sb.length() > 0) {
            System.out.println(sb.toString());
        }
    }

    // Method to explain the algorithms and design patterns used in the application
    void explainAlgorithms() {
        System.out.println();
        System.out.println("In this application, we use the following algorithms and design patterns:");
        System.out.println("1. Quicksort algorithm for sorting the list of books alphabetically by title.");
        System.out.println("2. Singleton pattern in BookDatabase to ensure a single instance of the database.");
        System.out.println("3. Observer pattern to notify when a new book is added to the database.");
        System.out.println("4. Factory pattern for creating book instances.");
        System.out.println();
    }
}

