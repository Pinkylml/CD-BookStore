package org.example;

import javax.persistence.*;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        // Get an entity manager factory and an entity manager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookstorePU");
        EntityManager em = emf.createEntityManager();
        // Get a transaction
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Author author1 = new Author();
            author1.setName("Douglas Adams");
            author1.setEmail("douglas.adams@example.com");
            em.persist(author1);

            Book book = new Book();
            book.setTitle("The Hitchhiker's Guide to the Galaxy");
            book.setPrice(12.5F);
            book.setDescription("Science fiction comedy book");
            book.setIsbn("1-84023-742-2");
            book.setNbOfPage(354);
            book.setIllustrations(false);
            book.addChapter(new Chapter("The End of the World", book));
            book.addChapter(new Chapter("The Restaurant at the End of the Universe", book));
            book.addAuthor(author1);
            em.persist(book);

            Author author2 = new Author();
            author2.setName("Jostein Gaarder");
            author2.setEmail("jostein.gaarder@example.com");
            em.persist(author2);

            Book book2 = new Book();
            book2.setTitle("El mundo de Sofía");
            book2.setPrice(10F);
            book2.setDescription("History of philosofy");
            book2.setNbOfPage(498);
            book2.setIllustrations(false);
            book2.addChapter(new Chapter("El Jardín del Edén, El Sombrero de Copa y Los Mitos", book2));
            book2.addChapter(new Chapter("La filosofía de la naturaleza", book2));
            book2.addChapter(new Chapter("Sócrates, Platón y Aristóteles", book2));
            Address addBook2 = new Address();
            addBook2.setCity("Quito");
            addBook2.setState("Pichincha");
            addBook2.setCountry("Ecuador");
            addBook2.setStreet1("Av.Maldonado");
            book2.setLanguage(Language.SPANISH);
            book2.setPublisherAddress(addBook2);
            book2.addAuthor(author2);
            em.persist(book2);

            Author author3 = new Author();
            author3.setName("J.R.R. Tolkien");
            author3.setEmail("jrr.tolkien@example.com");
            em.persist(author3);

            Book book3 = new Book();
            book3.setTitle("The Lord of the Rings");
            book3.setPrice(22.99F);
            book3.setDescription("Epic fantasy novel.");
            book3.setIsbn("0-618-64015-0");
            book3.setNbOfPage(1178);
            book3.setIllustrations(true);
            book3.setLanguage(Language.ENGLISH);
            book3.addChapter(new Chapter("A Long-expected Party", book3));
            book3.addChapter(new Chapter("The Shadow of the Past", book3));
            book3.addChapter(new Chapter("The Bridge of Khazad-dûm", book3));
            Address addLotr = new Address();
            addLotr.setCity("London");
            addLotr.setCountry("United Kingdom");
            addLotr.setStreet1("221B Baker Street");
            book3.setPublisherAddress(addLotr);
            book3.addAuthor(author3);
            em.persist(book3);

            Author author4 = new Author();
            author4.setName("Miguel de Cervantes");
            author4.setEmail("miguel.cervantes@example.com");
            em.persist(author4);

            Book book4 = new Book();
            book4.setTitle("Don Quixote");
            book4.setPrice(15.0F);
            book4.setDescription("A Spanish epic novel by Miguel de Cervantes.");
            book4.setIsbn("978-0060934347");
            book4.setNbOfPage(1023);
            book4.setIllustrations(false);
            book4.setLanguage(Language.SPANISH);
            book4.addChapter(new Chapter("Which treats of the character and pursuits of the famous gentleman Don Quixote of La Mancha", book4));
            book4.addChapter(new Chapter("Of the first sally that the ingenious Don Quixote made from home", book4));
            book4.addChapter(new Chapter("Of the pleasant discourse that passed between Don Quixote and his squire Sancho Panza", book4));
            Address addQuixote = new Address();
            addQuixote.setCity("Madrid");
            addQuixote.setCountry("Spain");
            addQuixote.setStreet1("Plaza de España");
            book4.setPublisherAddress(addQuixote);
            book4.addAuthor(author4);
            em.persist(book4);

            // Create and persist News objects
            NewsId newsId1 = new NewsId();
            newsId1.setTitle("New Book Release");
            newsId1.setLanguage(Language.ENGLISH);
            News news1 = new News(newsId1, "A new book by a famous author is now available.");
            em.persist(news1);

            NewsId newsId2 = new NewsId();
            newsId2.setTitle("Lanzamiento de Libro");
            newsId2.setLanguage(Language.SPANISH);
            News news2 = new News(newsId2, "Un nuevo libro de un autor famoso ya está disponible.");
            em.persist(news2);


            tx.commit();
            System.out.println("✅ SUCCESS: All books, authors, and news were saved to the database.");

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.out.println("❌ TRANSACTIONAL ERROR: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // Find and update a book
        Book retrieveBook = em.find(Book.class, 1L); // Assuming first book has ID 1
        if (retrieveBook != null) {
            System.out.println("Success! Retrieved book title: " + retrieveBook.getTitle() + " price: " +
                    retrieveBook.getPrice());
            tx.begin();
            retrieveBook.setPrice(19.99F);
            tx.commit();
        }

        // List all books
        List<Book> books = em.createNamedQuery("findAllBooks", Book.class).getResultList();
        System.out.println("\nTotal books found in the database: " + books.size());
        for (Book b : books) {
            System.out.println(" - " + b.getTitle() + " price: " + b.getPrice());
        }


        Book orderedBook = em.find(Book.class, 2L); // Let's check 'El mundo de Sofía'

        if (orderedBook != null) {
            System.out.println("\n📖 Verifying Chapter Order for: " + orderedBook.getTitle());

            // Loop through the list and print the index and title
            List<Chapter> chapters = orderedBook.getChapters();
            for (int i = 0; i < chapters.size(); i++) {
                System.out.println("   Index " + i + " -> " + chapters.get(i).getTitle());
            }
        }

        String queryString = "SELECT b FROM Book b WHERE b.title = :title";

        Query query2 = em.createQuery(queryString);
        query2.setParameter("title","El mundo de Sofía");
        List<Book> booksSeco = query2.getResultList();

        for(Book book: booksSeco) {

            System.out.println("Book by title: " + book.getTitle());
        }



        List<Book> tolkienBooks = em.createNamedQuery("Book.findByAuthor", Book.class)
                                    .setParameter("authorName", "J.R.R. Tolkien")
                                    .getResultList();
        System.out.println("\nBooks by J.R.R. Tolkien:");
        for (Book bookResult : tolkienBooks) {
            System.out.println(" - " + bookResult.getTitle());
        }

        // Close resources
        em.close();
        emf.close();
    }
}