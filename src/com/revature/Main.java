package com.revature;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;


/* 1. Import ---> java.sql
 * 2. Load and register the driver --->  com.mysql.jdbc.Driver
 * 3. Create Connection ----> Connection
 * 4. Create Statement ----> Statement
 * 5. Execute Query ---->
 * 6. Process the results ---->
 * 7. Close ---->
 */


public class Main {
    static String username = null;
    String password = null;
    static boolean inputError1 = false;
    boolean inputError2 = false;
    boolean access = false;
    static int choice1 = 0;
    static int choice2 = 0;
    static int choice3 = 0;
    String value1;
    static String FName;
    static String LName;
    static String name = FName + " " + LName;
    static String email = null;
    static String uname = null;
    static String upass = null;
    static String epass = null;
    static int randomId = (int) Math.floor(Math.random() * 100);
    static String ulogin;
    static String eulogin;
    static String plogin;
    static String eplogin;
    static boolean creation = false;
    static EmployeeDao dao;

    static Account account;
    static Scanner input = new Scanner(System.in);

    public static void MenuPage() {

        try {
            while (!inputError1) {
                System.out.println("Welcome to North Park Bank");
                System.out.println("");
                System.out.println("1 - Log In | 2 - Create An Account | 3 - Employee Log In");
                choice1 = input.nextInt();
                if (choice1 == 1) {
                    inputError1 = true;
                    LogInPage();
                } else if (choice1 == 2) {
                    inputError1 = true;
                    creation = false;
                    CreateUserPage();
                } else if (choice1 == 3){
                    inputError1 = true;
                    EmployeeLoginPage();
                } else {
                    System.out.println("Invalid Input!");
                    inputError1 = false;
                    MenuPage();
                }
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
            System.out.println("Input Error - System Shutting Down");
            inputError1 = false;
            System.exit(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void CreateUserPage() {
        System.out.println("1 - User Account | 2 - Employee Account");
        choice2 = input.nextInt();
        if (choice2 == 1) {
            System.out.print("First Name: ");
            FName = input.next();
            System.out.print("Last Name: ");
            LName = input.next();
            name = FName + " " + LName;
            System.out.print("E-mail Address: ");
            email = input.next();
            System.out.print("Username: ");
            uname = input.next();
            System.out.print("Password: ");
            upass = input.next();
            try {
                User user = new User(randomId, name, email, uname, upass);
                UserDao userDao = UserDaoFactory.getUserDao();
                userDao.addUser(user);
                creation = false;
                LogInPage();

            } catch (ClassCastException | SQLException exception) {
                exception.printStackTrace();
                System.out.println("User Error");
                System.exit(0);

            }
        } else if (choice2 == 2) {
            System.out.println("Account Creation Password: ");
            String empPass = input.next();
            if (empPass.contentEquals("admin")) {
                System.out.print("Enter First Name: ");
                FName = input.next();
                System.out.print("Enter Last Name: ");
                LName = input.next();
                name = FName + " " + LName;
                System.out.print("Enter E-mail: ");
                email = input.next();
                System.out.print("Enter Password: ");
                epass = input.next();

                try {
                    Employee employee = new Employee(randomId, name, email, epass);
                    EmployeeDao dao = EmployeeDaoFactory.getEmployeeDao();
                    dao.addEmployee(employee);
                    EmployeeLoginPage();
                } catch (SQLIntegrityConstraintViolationException ex) {
                    ex.printStackTrace();
                    System.out.println("Duplicate Entry - System Shutting Down");
                    System.exit(0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Invalid Password! Shutting Down...");
                CreateUserPage();
            }
        }
    }

    public static void CreateAccountPage() throws SQLException {
        AccountDao accountDao = AccountDaoFactory.accountUserDao();
        User user = new User(randomId, name, email, ulogin, plogin);
        System.out.println("");
        System.out.println("Do you want to open up an account? Y/N");
        String choice4 = input.nextLine();
        if (choice4.equals("Y") || choice4.equals("y")) {
            if(creation){
                System.out.println("User Owns An Account!");
                AccountMenu();
            }else{
                Account account = new Account(user.getUsername(), 0, 0, 0, 0, 0);
                accountDao.addAccount(account, user);
                creation = true;
                AccountMenu();
            }
            AccountMenu();
        } else if (choice4.equals("N") || choice4.equals("n")) {
            LogInPage();
        } else {
            CreateAccountPage();
        }
    }

    public static void LogInPage() throws SQLException {
        UserDao userDao = UserDaoFactory.getUserDao();
        System.out.print("Enter Username: ");
        ulogin = input.next();
        System.out.print("Enter Password: ");
        plogin = input.next();
        ulogin.trim();
        plogin.trim();
        userDao.getLogin(ulogin, plogin);
        if (userDao.getLogin(ulogin, plogin)) {
            User user = new User(randomId, name, email, ulogin, plogin);
            System.out.println("Login Successful");
            AccountMenu();
        } else {
            System.out.println("Invalid Login!");
            LogInPage();
        }
    }

    public static void EmployeeLoginPage() throws SQLException {
        EmployeeDao employeeDao = EmployeeDaoFactory.getEmployeeDao();
        System.out.print("Enter Email: ");
        eulogin = input.next();
        System.out.print("Enter Password: ");
        eplogin = input.next();
        employeeDao.getEmployeeLogin(eulogin, eplogin);
        if(employeeDao.getEmployeeLogin(eulogin, eplogin)){
            Employee employee = new Employee(randomId, name, eulogin, eplogin);
            System.out.println("Login Successful");
            EmployeeMenu();
        } else {
            System.out.println("Invalid Login!");
            EmployeeLoginPage();
        }
    }

    public static void AccountMenu() throws SQLException {
        AccountDao accountDao = AccountDaoFactory.accountUserDao();
        UserDao userDao = UserDaoFactory.getUserDao();
        User user = new User(randomId, name, email, ulogin, plogin);
        Account account = accountDao.getAccount(ulogin);
        System.out.println("");
        System.out.println("1 - Balance | 2 - Deposit | 3 - Withdraw | 4 - Transfer | 5 - Invest | 6 - User | 7 - Exit");

        try {
            choice3 = input.nextInt();
            switch (choice3) {
                case 1: //Balance
                    System.out.println(accountDao.getAccount(ulogin));
                    AccountMenu();
                    break;
                case 2: //Deposit
                    System.out.print("Enter Amount to Deposit : ");
                    double amount1 = input.nextDouble();
                    accountDao.Deposit(amount1, user);
                    AccountMenu();
                    break;
                case 3: //Withdraw

                    if (account.getBalance() < 0){
                        System.out.println("Not Enough Funds");
                    }else {
                        try{
                            accountDao.getAccount(ulogin);
                            System.out.print("Enter Amount To Withdraw: ");
                            Scanner scanner = new Scanner(System.in);
                            double amount2 = scanner.nextInt();
                            if (account.getBalance() < amount2){
                                System.out.println("Not Enough Funds!");
                            }else{
                                accountDao.Withdraw(amount2, user);
                            }
                            AccountMenu();
                            break;
                        }catch(NullPointerException ex){
                            ex.printStackTrace();
                            System.out.println("Not Enough Funds!");
                            break;
                        }
                    }
                case 4: //Transfers
                    System.out.print("Enter Username For Transfer: ");
                    try{
                        String transferUser = input.next();
                        accountDao.getAccount(transferUser);
                        if(user.getUsername().equals(transferUser)){
                            System.out.println("Invalid Input!");
                            AccountMenu();
                        }else{
                            System.out.print("Enter Amount: ");
                            double transferAmount = input.nextDouble();
                            if(transferAmount > account.getBalance()){
                                System.out.println("Not Enough Funds!");
                                AccountMenu();
                            }else{
                                accountDao.Transfer(account,transferUser,transferAmount);
                                AccountMenu();
                            }
                        }


                        break;
                    }catch(SQLException e){
                        System.out.println("Username Does Not Exist");
                        e.printStackTrace();
                        break;
                    }


                case 5: //Investments
                    System.out.println("Well that's fantastic. A really smart decision, young man. We can put that check in a money market mutual fund, then we'll re-invest the earnings into foreign currency accounts with compounding interest aaaand it's gone.");
                    accountDao.Investments(account, user);
                    AccountMenu();
                    break;
                case 6: //User Menu
                    UserMenu();
                    break;
                case 7: //Exit
                    System.out.println("Thank You For Using North Park Bank!");
                    System.exit(0);
                    break;
                default: //Input Error
                    System.out.println("Input Error!");
                    AccountMenu();
                    break;
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
            System.out.println("Invalid Input! System Shutting Down..");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void UserMenu() throws SQLException {
        UserDao userDao = UserDaoFactory.getUserDao();
        AccountDao accountDao = AccountDaoFactory.accountUserDao();
        User user = new User(randomId, name, email, ulogin, plogin);
        Account account = accountDao.getAccount(ulogin);
        System.out.println("");
        System.out.println("1 - User Information | 2 - Create Bank Account | 3 - Bank Account | 4 - Exit");
        try {
            choice3 = input.nextInt();
            switch (choice3) {
                case 1: //User Information
                    System.out.println(userDao.getUser(user));
                    UserMenu();
                    break;
                case 2: //Create Account
                    CreateAccountPage();
                    break;
                case 3: //Account Menu
                    AccountMenu();
                    break;
                case 4: //Exit
                    System.out.println("Thank You For Using North Park Bank!");
                    System.exit(0);
                    break;
                default: //Input Error
                    System.out.println("Input Error!");
                    UserMenu();
                    break;
            }
        }catch (InputMismatchException e) {
            e.printStackTrace();
            System.out.println("Invalid Input! System Shutting Down..");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void EmployeeMenu() throws SQLException {
        EmployeeDao employeeDao = EmployeeDaoFactory.getEmployeeDao();
        Employee employee = new Employee(randomId, name, eulogin, eplogin);
        System.out.println("");
        System.out.println("1 - User Information | 2 - All Bank Users | 3 - Exit");
        int choice5 = input.nextInt();
        switch(choice5) {
            case 1: // User
                System.out.print("Enter Username: ");
                String enterUser = input.next();
                System.out.println(employeeDao.getUser(enterUser));
                EmployeeMenu();
                break;
            case 2: // All Users
                System.out.println(employeeDao.getUsers());
                EmployeeMenu();
                break;
            case 3: // Exit
                System.exit(0);
                break;
            default: // Input Error
                EmployeeMenu();
                break;
        }
    }

    public static void main (String[] args) throws SQLException {
        UserDao userDao = UserDaoFactory.getUserDao();
        AccountDao accountDao = AccountDaoFactory.accountUserDao();
        EmployeeDao employeeDao = EmployeeDaoFactory.getEmployeeDao();
        MenuPage();
    }
}

//    Need this is main
//    UserDao userDao = UserDaoFactory.getUserDao();
//    AccountDao accountDao = AccountDaoFactory.accountUserDao();
//    EmployeeDao employeeDao = EmployeeDaoFactory.getEmployeeDao();

//    Employee create, update, delete
//    Employee employee = new Employee(3,"Melch", "melch.com");
//    employeeDao.updateEmployee(employee, 1);

//    Employee Select All
//    employeeDao.getEmployees();

//    Creates Bank Account and Links to User Account
//    User user = new User(55,"Paul","P@Gmail.com", "Paul", "pass");
//    userDao.addUser(user);
//    Account account = new Account(user.getUsername(),0,0,0,0,0);
//    accountDao.addAccount(account, user);

//    How to transfer
//    Account account = new Account("Warrior", 650, 100,0,0,0);
//    accountDao.Transfer(account,"Hunt",50);

//    Investments
//    Account account = new Account("Hunt", 550, 650,100,0,0);
//    accountDao.Investments(account);

//    Select One
//    Account account = new Account("Hunt", 550, 650,100,0,0);
//    System.out.println(accountDao.getAccount(account));

//    Select All
//    System.out.println(accountDao.getAccounts());