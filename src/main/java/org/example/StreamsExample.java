package org.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class StreamsExample {

    public static void main(final String[] args) {

        List<Author> authors = Library.getAuthors();

        banner("Authors information");
        // SOLVED With functional interfaces declared
        Consumer<Author> authorPrintConsumer = new Consumer<Author>() {
            @Override
            public void accept(Author author) {
                System.out.println(author);
            }
        };
        authors
                .stream()
                .forEach(authorPrintConsumer);

        // SOLVED With functional interfaces used directly
        authors
                .stream()
                .forEach(System.out::println);

        banner("Active authors");
        // Solved With functional interfaces declared
        Consumer<Author> activeAuthorPrintConsumer = new Consumer<Author>() {
            @Override
            public void accept(Author author) {
                if (author.isActive()) {
                    System.out.println(author);
                }
            }
        };
        authors
                .stream()
                .forEach(activeAuthorPrintConsumer);

        banner("Active authors - lambda");
        // Solved With functional interfaces used directly
        authors
                .stream()
                .filter(author -> author.isActive())
                .forEach(System.out::println);

        banner("Active books for all authors");
        // Solved With functional interfaces declared
        Consumer<Author> activeBooksForAuthors = new Consumer<Author>() {
            @Override
            public void accept(Author author) {
                List<Book> allBooks = author.getBooks();
                for (Book book : allBooks) {
                    if (book.isPublished()) {
                        System.out.println(book);
                    }
                }
            }
        };
        authors
                .stream()
                .forEach(activeBooksForAuthors);

        banner("Active books for all authors - lambda");
        // Solved With functional interfaces used directly
        authors.forEach(author -> {
            author.getBooks().stream()
                    .filter(book -> book.isPublished())
                    .forEach(System.out::println);
        });

        banner("Average price for all books in the library");
        // TODO With functional interfaces declared

        banner("Average price for all books in the library - lambda");
        // Solved With functional interfaces used directly
        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .collect(Collectors.toList())
                .stream()
                .mapToDouble(Book::getPrice)
                .average()
                .ifPresent(System.out::println);

        banner("Active authors that have at least one published book");
        // Solved With functional interfaces declared
        Consumer<Author> activeAuthorsHasAtLeastPublishedBook = new Consumer<Author>() {
            @Override
            public void accept(Author author) {
                List<Book> allBooks = author.getBooks();
                for (Book book : allBooks) {
                    if (book.isPublished() && author.isActive()) {
                        System.out.println(author.getName());
                    }
                    break;
                }
            }
        };
        authors
                .stream()
                .forEach(activeAuthorsHasAtLeastPublishedBook);

        banner("Active authors that have at least one published book - lambda");
        // Solved With functional interfaces used directly
        authors.stream()
                .filter(author -> author.isActive())
                .filter(author -> author.getBooks().stream().anyMatch(Book::isPublished))
                .collect(Collectors.toList())
                .forEach(System.out::println);

    }

    private static void banner(final String m) {
        System.out.println("#### " + m + " ####");
    }
}

class Library {
    public static List<Author> getAuthors() {
        return Arrays.asList(
                new Author("Author A", true, Arrays.asList(
                        new Book("A1", 100, true),
                        new Book("A2", 200, true),
                        new Book("A3", 220, true))),
                new Author("Author B", true, Arrays.asList(
                        new Book("B1", 80, true),
                        new Book("B2", 80, false),
                        new Book("B3", 190, true),
                        new Book("B4", 210, true))),
                new Author("Author C", true, Arrays.asList(
                        new Book("C1", 110, true),
                        new Book("C2", 120, false),
                        new Book("C3", 130, true))),
                new Author("Author D", false, Arrays.asList(
                        new Book("D1", 200, true),
                        new Book("D2", 300, false))),
                new Author("Author X", true, Collections.emptyList()));
    }
}

class Author {
    String name;
    boolean active;
    List<Book> books;

    Author(String name, boolean active, List<Book> books) {
        this.name = name;
        this.active = active;
        this.books = books;
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return name + "\t| " + (active ? "Active" : "Inactive");
    }
}

class Book {
    String name;
    int price;
    boolean published;

    public int getPrice() {
        return price;
    }

    public boolean isPublished() {
        return published;
    }

    Book(String name, int price, boolean published) {
        this.name = name;
        this.price = price;
        this.published = published;
    }

    @Override
    public String toString() {
        return name + "\t| " + "\t| $" + price + "\t| " + (published ? "Published" : "Unpublished");
    }
}
