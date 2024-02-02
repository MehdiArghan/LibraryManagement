package view;

import base.repository.util.HibernateUtil;
import entity.Librarian;
import entity.Member;
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
        System.out.println("-------------LOGIN-----------------");
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
            System.out.println("username and password is inCorrect");
            showMenu();
        }
    }

    private void programMember() {

    }

    private void programAdmin() {

    }
}
