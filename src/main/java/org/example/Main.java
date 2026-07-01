package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("The Hitchhiker's Guide to the Galaxy");
        book.setPrice(12.5F);
        book.setDescription("Science fiction comedy book");
        book.setIsbn("1-84023-742-2");
        book.setNbOfPage(354);
        book.setIllustrations(false);



        Book book2 = new Book();
        book2.setTitle("El mundo de Sofía");
        book2.setPrice(10F);
        book2.setDescription("History of philosofy");
        book2.setNbOfPage(498);
        book2.setIllustrations(false);
        Chapter chap = new Chapter("El Jardín del Edén, El Sombrero de Copa y Los Mitos",book2);
        book2.addChapter(chap);
            // Get an entity manager factory and an entity manager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookstorePU");
        EntityManager em = emf.createEntityManager();
        // Get a transaction
        EntityTransaction tx = em.getTransaction();
        // 1. START THE TRANSACTION HERE
        tx.begin();

        // 2. PERSIST THE BOOK OBJECT HERE
        em.persist(book);
        // 3. COMMIT THE TRANSACTION HERE
        tx.commit();

        try {
            tx.begin();
            em.persist(book2);
            tx.commit();
            System.out.println("✅ ÉXITO: El libro 2 y su capítulo fueron guardados en la tabla ITEM y CHAPTER.");
        } catch (Exception e) {

            System.out.println("❌ ERROR TRANSACCIONAL: " + e.getMessage());
            throw new RuntimeException(e);
        }


        System.out.println("Book saved with ID: " + book.getId());

        Book retrieveBook = em.find(Book.class, book.getId());

        if (retrieveBook != null) {
            System.out.println("Success! Retrieved book title: " + retrieveBook.getTitle() + " price: " +
                    retrieveBook.getPrice());
        }

        //update the retrieve
        tx.begin();
        retrieveBook.setPrice(19.99F);
        tx.commit();

        List<Book> books = em.createNamedQuery("findAllBooks").getResultList();
        System.out.println("Total books found in the database: " + books.size());
        for (Book b : books) {
            System.out.println(" - " + b.getTitle() + " price: " + b.getPrice());
        }

        // Close resources
        em.close();
        emf.close();

    }

}
