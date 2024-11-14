package org.quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Part 2
        Book originalShallowBook = BookPool.getBook("Shallow Copy");
        Book clonedShallowBook = originalShallowBook.clone();
        originalShallowBook.getChapters().remove(2);

        System.out.println("Original Book-\n" + originalShallowBook);
        System.out.println("Shallow copied Book-\n" + clonedShallowBook);

        // Part 3
        Book originalDeepBook = BookPool.getBook("Deep Copy");
        Book clonedDeepBook = originalDeepBook.clone();
        originalDeepBook.getChapters().remove(2);

        System.out.println("\nOriginal Book-\n" + originalDeepBook);
        System.out.println("Deep copied Book-\n" + clonedDeepBook);
    }
}


// Part 1
class Book implements Cloneable {
    private String title;
    private String author;
    private List<String> chapters = new ArrayList<>();

    public Book(String title, String author, List<String> chapters) {
        this.title = title;
        this.author = author;
        this.chapters = chapters;
    }

    @Override
    public Book clone() {
        try {
            return (Book) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getChapters() {
        return chapters;
    }

    public void setChapters(List<String> chapters) {
        this.chapters = chapters;
    }

    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author + ", chapters=" + chapters + "]";
    }
}

class BookShallowCopy extends Book {

    public BookShallowCopy(String title, String author, List<String> chapters) {
        super(title, author, chapters);
    }

    @Override
    public BookShallowCopy clone() {
        BookShallowCopy shallowClone = (BookShallowCopy) super.clone();
        shallowClone.setTitle(this.getTitle());
        shallowClone.setAuthor(this.getAuthor());
        shallowClone.setChapters(this.getChapters());
        return shallowClone;
    }
}

class BookDeepCopy extends Book {

    public BookDeepCopy(String title, String author, List<String> chapters) {
        super(title, author, chapters);
    }
    @Override
    public BookDeepCopy clone() {
        BookDeepCopy deepCopy = (BookDeepCopy) super.clone();
        deepCopy.setTitle(this.getTitle());
        deepCopy.setAuthor(this.getAuthor());
        List<String> chapters = new ArrayList<>();
        for (String chapter : this.getChapters()) {
            chapters.add(chapter);
        }
        deepCopy.setChapters(chapters);
        return deepCopy;
    }
}

class BookPool {
    private static HashMap<String, Book> pool = new HashMap<>();
    private static List<String> chapters = new ArrayList<>();

    static {
        chapters.add("Chapter 1");
        chapters.add("Chapter 2");
        chapters.add("Chapter 3");
        List<String> alternateChapters = new ArrayList<>(chapters);

        Book shallowBook = new BookShallowCopy("Shallow Book", "Proust", chapters);
        pool.put("Shallow Copy", shallowBook);

        Book deepBook = new BookDeepCopy("Deep Book", "Hemingway", alternateChapters);
        pool.put("Deep Copy", deepBook);
    }

    public static Book getBook(String title) {
        return pool.get(title).clone();
    }

}