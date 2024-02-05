package view;

import base.repository.util.HibernateUtil;
import entity.Book;
import entity.Librarian;
import entity.Member;
import entity.Subject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import repository.impl.BookRepositoryImpl;
import repository.impl.LibrarianRepositoryImpl;
import repository.impl.MemberRepositoryImpl;
import repository.impl.SubjectRepositoryImpl;
import service.BookService;
import service.LibrarianService;
import service.MemberService;
import service.SubjectService;
import service.impl.BookServiceImpl;
import service.impl.LibrarianServiceImpl;
import service.impl.MemberServiceImpl;
import service.impl.SubjectServiceImpl;

import java.util.*;

public class Menu {
    private final EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
    private final BookService bookService = new BookServiceImpl(entityManager, new BookRepositoryImpl(entityManager));
    private final LibrarianService librarianService = new LibrarianServiceImpl(entityManager, new LibrarianRepositoryImpl(entityManager));
    private final MemberService memberService = new MemberServiceImpl(entityManager, new MemberRepositoryImpl(entityManager));
    private final SubjectService subjectService = new SubjectServiceImpl(entityManager, new SubjectRepositoryImpl(entityManager));
    private final Scanner scanner = new Scanner(System.in);
    private Member member = new Member();
    private Librarian librarian = new Librarian();

    public void showMenu() {
        System.out.println();
        System.out.println("          *******(Library)*******          ");
        System.out.println("1-SignUp");
        System.out.println("2-Login");
        System.out.println("3-Exit");
        System.out.println("please choose your option:");
        switch (scanner.nextInt()) {
            case 1 -> {
                signUp();
                logIn();
            }
            case 2 -> logIn();
            case 3 -> System.exit(0);
            default -> {
                System.out.println("invalid option");
                showMenu();
            }
        }
    }

    private void signUp() {
        System.out.println();
        System.out.println("firstName:----------------------");
        String name = scanner.next();
        System.out.println("LastName:--------------------");
        String family = scanner.next();
        System.out.println("userName:------------------");
        String username = scanner.next();
        System.out.println("password:------------------");
        String password = scanner.next();
        if (username.startsWith("*")) {
            Librarian userLibrarian = new Librarian(name, family, username, password, "admin");
            boolean validate = librarianService.validate(userLibrarian);
            if (validate) {
                System.out.println("Registration was successful");
            } else {
                System.out.println("Registration failed");
                showMenu();
            }
        } else {
            Member userMember = new Member(name, family, username, password);
            boolean validate = memberService.validate(userMember);
            if (validate) {
                System.out.println("Registration was successful");
            } else {
                System.out.println("Registration failed");
                showMenu();
            }
        }
    }


    private void logIn() {
        System.out.println();
        System.out.println("-------------(LOGIN)-----------------");
        System.out.println("userName:------------------");
        String username = scanner.next();
        System.out.println("password:------------------");
        String password = scanner.next();
        try {
            if (username.startsWith("*")) {
                Optional<Librarian> byUserNameAndPassword = librarianService.findByUserNameAndPassword(username, password);
                if (byUserNameAndPassword.isPresent()) {
                    librarian = byUserNameAndPassword.get();
                    programAdmin();
                }
            } else {
                Optional<Member> byUserNameAndPassword = memberService.findByUserNameAndPassword(username, password);
                if (byUserNameAndPassword.isPresent()) {
                    member = byUserNameAndPassword.get();
                    programMember();
                }
            }
        } catch (NoResultException e) {
            System.out.println("There is no user");
            System.out.println(e.getMessage());
            showMenu();
        }
    }

    private void programAdmin() {
        System.out.println();
        System.out.println("---------------------(MenuAdmin)---------------------");
        System.out.println("1-Subject category");
        System.out.println("2-Classification of books");
        System.out.println("3-Exit");
        System.out.println("please choose your option:");
        switch (scanner.nextInt()) {
            case 1 -> menuForSubject();
            case 2 -> menuForBooks();
            case 3 -> {
                librarian = null;
                showMenu();
            }
            default -> {
                System.out.println("invalid option");
                programAdmin();
            }
        }
    }

    private void menuForSubject() {
        while (true) {
            System.out.println();
            System.out.println("---------------------(MenuForSubject)---------------------");
            System.out.println("""
                      1)Add subject
                      2)Edit subject
                      3)remove subject
                      4)load All  subject
                      5)load All  subject that their books exist
                      6)back to main menu
                      -------------------------------------------------------------------------
                      please enter your option:
                    """);
            manageSubject();
        }
    }

    private void manageSubject() {
        switch (scanner.nextInt()) {
            case 1:
                addSubject();
                break;
            case 2:
                editSubject();
                break;
            case 3:
                removeSubject();
                break;
            case 4:
                loadAllSubject();
                break;
            case 5:
                loadAllSubjectByBook();
                break;
            case 6:
                programAdmin();
            default:
                System.out.println("invalid option");
                menuForSubject();
        }
    }

    private void addSubject() {
        System.out.println("please enter your title:");
        String title = scanner.next();
        try {
            Optional<Subject> byTitle = subjectService.findByTitle(title);
            if (byTitle.isPresent()) {
                System.out.println("The subject of the book is available");
            }
        } catch (NoResultException e) {
            subjectService.save(new Subject(title));
            System.out.println(title + " was successfully registered");
        }
    }

    private void editSubject() {
        List<Subject> subjects = subjectService.loadAll();
        for (Subject subject : subjects) {
            System.out.println("subject: " + subject);
            System.out.println("do you want to update it? y->Yes  n->No");
            if (scanner.next().equals("y")) {
                System.out.println("enter new title: ");
                subject.setTitle(scanner.next());
                subjectService.update(subject);
            } else {
                System.out.println();
            }
        }
    }

    private void removeSubject() {
        List<Subject> subjects = subjectService.loadAll();
        for (Subject subject : subjects) {
            System.out.println("subject: " + subject);
            System.out.println("do you want to remove it? y->Yes  n->No");
            if (scanner.next().equals("y")) {
                List<Book> booksBySubject = bookService.loadAll();
                for (Book book : booksBySubject) {
                    if (book.getSubject().getTitle().equals(subject.getTitle())) {
                        bookService.remove(book);
                    }
                }
                subjectService.remove(subject);
                System.out.println(subject.getTitle() + " was successfully removed");
            } else {
                System.out.println();
            }
        }
    }

    private void loadAllSubject() {
        List<Subject> subjects = subjectService.loadAll();
        for (Subject subject : subjects) {
            System.out.println(subject.getTitle());
        }
    }

    private void loadAllSubjectByBook() {
        List<Book> books = bookService.loadAll();
        for (Book book : books) {
            System.out.println(book.getSubject() + ": " + book.getTitle());
        }
    }

    private void menuForBooks() {
        while (true) {
            System.out.println();
            System.out.println("---------------------(MenuForBooks)---------------------");
            System.out.println("""
                      1)Add book
                      2)Edit book
                      3)remove book
                      4)load All book
                      5)RequestBorrowBook
                      6)back to main menu
                      -------------------------------------------------------------------------
                      please enter your option:
                    """);
            manageBook();
        }
    }

    private void manageBook() {
        switch (scanner.nextInt()) {
            case 1:
                addBook();
                break;
            case 2:
                editBook();
                break;
            case 3:
                removeBook();
                break;
            case 4:
                loadAllBooks();
                break;
            case 5:
                RequestBorrowBook();
                break;
            case 6:
                programAdmin();
                break;
            default:
                System.out.println("invalid option");
                menuForBooks();
        }
    }

    private void addBook() {
        List<Subject> subjects = subjectService.loadAll();
        for (Subject subject : subjects) {
            System.out.println("subject: " + subject);
            System.out.println("do you want select it or no ? y->Yes  n->No");
            if (scanner.next().equals("y")) {
                System.out.println("enter title book: ");
                String title = scanner.next();
                System.out.println("enter author of this book: ");
                String author = scanner.next();
                Book book = new Book(title, author);
                book.setSubject(subject);
                bookService.save(book);
                System.out.println("saved book");
            } else {
                System.out.println();
            }
        }
    }

    private void editBook() {
        List<Book> bookList = bookService.loadAll();
        for (Book book : bookList) {
            System.out.println("Title:" + book.getTitle() + "  Author:" + book.getAuthor());
            System.out.println("Are you sure that edit this book? y ->yes  n ->no");
            if (scanner.next().equals("y")) {
                System.out.println("please enter your title book: ");
                String titleBook = scanner.next();
                System.out.println("please enter your Author book: ");
                String author = scanner.next();
                List<Subject> subjects = subjectService.loadAll();
                for (Subject subject : subjects) {
                    System.out.println("subject: " + subject.getTitle());
                    System.out.println("do you want select it or no? y->Yes  n->No");
                    if (scanner.next().equals("y")) {
                        book.setTitle(titleBook);
                        book.setAuthor(author);
                        book.setSubject(subject);
                        bookService.update(book);
                        System.out.println(book.getTitle() + " successfully updated");
                    } else {
                        System.out.println();
                    }
                }
            } else {
                System.out.println();
            }
        }
    }

    private void removeBook() {
        List<Book> bookList = bookService.loadAll();
        for (Book book : bookList) {
            System.out.println("Title:" + book.getTitle() + "  Author:" + book.getAuthor());
            System.out.println("Are you sure that remove this book? y ->yes  n ->no");
            if (scanner.next().equals("y")) {
                bookService.remove(book);
                System.out.println(book.getTitle() + " removed");
            } else {
                System.out.println();
            }
        }
    }

    private void loadAllBooks() {
        List<Book> bookList = bookService.loadAll();
        for (Book book : bookList) {
            System.out.println("Subject:" + book.getSubject() +
                    "  Title:" + book.getTitle() + "  Author:" + book.getAuthor());
        }
    }

    private void RequestBorrowBook() {
        List<Member> members = memberService.loadAll();
        for (Member member : members) {
            System.out.println(member.getFirstName() + " " + member.getLastName());
            if (!member.getBookReserveList().isEmpty()) {
                Set<Book> temporaryReserveBook = new HashSet<>(member.getBookReserveList());
                Set<Book> temporaryBorrowedBook = new HashSet<>(member.getBookBorrowedList());
                Set<Book> temporaryHistoryBorrowedList = new HashSet<>(member.getHistoryOFBorrowedBookList());
                for (Book book : temporaryReserveBook) {
                    System.out.println("subject: " + book.getSubject().getTitle() + " title:" + book.getTitle() + " author:" + book.getAuthor());
                    System.out.println("do you want to confirm reserve it? y ->yes  n -> no");
                    if (scanner.next().equals("y")) {
                        temporaryReserveBook.remove(book);
                        temporaryBorrowedBook.add(book);
                        temporaryHistoryBorrowedList.add(book);
                        member.setHistoryOFBorrowedBookList(temporaryHistoryBorrowedList);
                        member.setBookReserveList(temporaryReserveBook);
                        member.setBookBorrowedList(temporaryBorrowedBook);
                        memberService.update(member);
                        System.out.println("updated");
                    }
                }
            } else {
                System.out.println("bookReserveList is Empty");
            }


            if (!member.getBookRenewalDeadlineList().isEmpty()) {
                Set<Book> temporaryRenewalDeadlineList = new HashSet<>(member.getBookRenewalDeadlineList());
                Set<Book> temporaryBorrowedList = new HashSet<>(member.getBookBorrowedList());
                for (Book book : temporaryRenewalDeadlineList) {
                    System.out.println("subject: " + book.getSubject().getTitle() + " title:" + book.getTitle() + " author:" + book.getAuthor());
                    System.out.println("do you want to confirm renewal it? y ->yes  n -> no");
                    String input = scanner.next();
                    if (input.equals("y")) {
                        temporaryRenewalDeadlineList.remove(book);
                        temporaryBorrowedList.add(book);
                        member.setBookRenewalDeadlineList(temporaryRenewalDeadlineList);
                        member.setBookBorrowedList(temporaryBorrowedList);
                        memberService.update(member);
                        System.out.println("updated");
                    } else if (input.equals("n")) {
                        temporaryRenewalDeadlineList.remove(book);
                        temporaryBorrowedList.remove(book);
                        member.setBookRenewalDeadlineList(temporaryRenewalDeadlineList);
                        member.setBookBorrowedList(temporaryBorrowedList);
                        memberService.update(member);
                        System.out.println("updated");
                    }
                }
            } else {
                System.out.println("Book Renewal Deadline List is Empty");
            }
        }
    }

    private void programMember() {
        while (true) {
            System.out.println("""
                    ---------------------(MenuForMember)---------------------
                    1)profile
                    2)Borrowing books
                    3)Extending the book loan
                    4)history of Borrowed books
                    5)Exit
                    ---------------------------------------------------------
                    """);
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println(member);
                    break;
                case 2:
                    reserveBook();
                    break;
                case 3:
                    RequestForExtensionOfBookBorrowing();
                    break;
                case 4:
                    historyOfBorrowedBooks();
                    break;
                case 5:
                    member = null;
                    showMenu();
                    break;
                default:
                    System.out.println("invalid option");
                    programMember();
                    break;
            }
        }
    }

    private void historyOfBorrowedBooks() {
        if (!member.getHistoryOFBorrowedBookList().isEmpty()) {
            Set<Book> historyOFBorrowedBookList = member.getHistoryOFBorrowedBookList();
            for (Book book : historyOFBorrowedBookList) {
                System.out.println(book);
            }
        } else {
            System.out.println("history in Empty");
        }
    }

    private void reserveBook() {
        if (!bookService.loadAll().isEmpty()) {
            List<Book> bookList = bookService.loadAll();
            Set<Book> books = new HashSet<>();
            for (Book book : bookList) {
                System.out.println("subject: " + book.getSubject() + " title:" + book.getTitle() + " author:" + book.getAuthor());
                System.out.println("Do you want to reserve the book?y ->yes  n -> no");
                if (scanner.next().equals("y")) {
                    books.add(book);
                } else {
                    System.out.println();
                }
            }
            if (!books.isEmpty()) {
                member.setBookReserveList(books);
                memberService.update(member);
                System.out.println("bookReserve successfully updated");
            } else {
                System.out.println("Not Reserve");
            }
        } else {
            System.out.println("There is no book");
        }
    }

    private void RequestForExtensionOfBookBorrowing() {
        if (!member.getBookBorrowedList().isEmpty()) {
            Set<Book> bookBorrowedList = member.getBookBorrowedList();
            Set<Book> bookRenewalDeadlineList = member.getBookRenewalDeadlineList();
            for (Book book : bookBorrowedList) {
                System.out.println("Subject:" + book.getSubject() + " title:" + book.getTitle() + " author:" + book.getAuthor());
                System.out.println("do you want to renewal deadline? y ->yes  n -> no");
                String input = scanner.next();
                if (input.equals("y")) {
                    bookBorrowedList.remove(book);
                    bookRenewalDeadlineList.add(book);
                }
            }
            if (!bookRenewalDeadlineList.isEmpty()) {
                member.setBookBorrowedList(bookBorrowedList);
                member.setBookRenewalDeadlineList(bookRenewalDeadlineList);
                memberService.update(member);
                System.out.println("updated");
            } else {
                System.out.println("List is Empty");
            }
        } else {
            System.out.println("BookBorrowedList is Empty");
        }
    }
}