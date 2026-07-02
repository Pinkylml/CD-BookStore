package org.example;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(name = "findAllBooks", query = "SELECT b FROM Book b"),
        @NamedQuery(
                name = "Book.findByAuthor",
                query = "SELECT b FROM Book b JOIN b.authors a WHERE a.name = :authorName"
        )

})

@DiscriminatorValue("B")

public class Book extends Item {

    private String isbn;
    private Integer nbOfPage;
    private Boolean illustrations;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Transient
    private Float internalDiscount;

    @Enumerated(EnumType.STRING)
    private Language language;


    @Embedded
    private Address publisherAddress;


    @OneToMany(mappedBy = "book", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @OrderColumn(name = "chapter_index")
    private List<Chapter> chapters = new ArrayList<>();

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] eBook;

    @ElementCollection
    @CollectionTable(name = "book_tags")
    @Column(name = "tag_name")
    private List<String> tags = new ArrayList<String>();


    @ManyToMany
    @JoinTable(name = "book_author")
    private List<Author> authors = new ArrayList<>();

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }


    public Address getPublisherAddress() {
        return publisherAddress;
    }

    public void setPublisherAddress(Address publisherAddress) {
        this.publisherAddress = publisherAddress;
    }

    public byte[] geteBook() {
        return eBook;
    }

    public void seteBook(byte[] eBook) {
        this.eBook = eBook;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public void addChapter(Chapter chapter) {
        this.chapters.add(chapter);
        if (chapter.getBook() != this) {
            chapter.setBook(this);
        }
    }

    //getters and setters

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getNbOfPage() {
        return nbOfPage;
    }

    public void setNbOfPage(Integer nbOfPage) {
        this.nbOfPage = nbOfPage;
    }

    public Boolean getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(Boolean illustrations) {
        this.illustrations = illustrations;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Float getInternalDiscount() {
        return internalDiscount;
    }

    public void setInternalDiscount(Float internalDiscount) {
        this.internalDiscount = internalDiscount;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}