package view;

import base.repository.util.HibernateUtil;
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

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
                LoadAllSubject();
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
                subjectService.remove(subject);
                System.out.println(subject.getTitle() + " was successfully removed");
            } else {
                System.out.println();
            }
        }
    }

    private void LoadAllSubject() {
        List<Subject> subjects = subjectService.loadAll();
        for (Subject subject : subjects) {
            System.out.println(subject.getTitle());
        }
    }

    private void menuForBooks() {
    }

    private void programMember() {
    }
}
