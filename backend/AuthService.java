package backend;

import database.models.Auth;
import database.models.LoginSession;
import database.repositories.AuthRepo;
import frontend.LoggedInUser;
import frontend.UserType;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {

    private static AuthService instance;
    private AuthRepo authRepo;

    private AuthService() {
        authRepo = new AuthRepo();
    }

    public static AuthService getInstance() {
        if (instance == null)
            instance = new AuthService();
        return instance;
    }

    public boolean validateUser(String email, String password, UserType userType) throws SQLException {
        // Fetch User from database using email and match password
        // If match found, fill the user details in LoggedInUser singleton object and return true otherwise false
        Optional<Auth> optionalAuth = authRepo.getAuth(email);
        if (optionalAuth.isEmpty() || !optionalAuth.get().getPassword().equals(password))
            return false;
        Auth auth = optionalAuth.get();
        if(!checkValidUserType(auth.getUser().getId(), userType))
            return false;
        authRepo.deleteLoginSession(LoggedInUser.getInstance().getUserId());
        authRepo.addLoginSession(auth);
        LoggedInUser.getInstance().setUserId(auth.getUser().getId());
        LoggedInUser.getInstance().setEmail(auth.getUser().getEmail());
        LoggedInUser.getInstance().setName(auth.getUser().getFirstName() + " " + auth.getUser().getLastName());
        return true;
    }

    public boolean checkValidUserType(Integer userId, UserType userType) throws SQLException {
        switch (userType)
        {
            case STUDENT:
                Optional<Integer> studentOptional = authRepo.getStudent(userId);
                if(studentOptional.isPresent())
                {
                    LoggedInUser.getInstance().setId(studentOptional.get());
                    return true;
                }
                break;
            case FACULTY:
                Optional<Integer> facultyOptional = authRepo.getFaculty(userId);
                if(facultyOptional.isPresent())
                {
                    LoggedInUser.getInstance().setId(facultyOptional.get());
                    return true;
                }
                break;
            case ACADOFFICE:
                Optional<Integer> acadOptional =authRepo.getAcademicOffice(userId);
                if(acadOptional.isPresent())
                {
                    LoggedInUser.getInstance().setId(acadOptional.get());
                    return true;
                }
        }
        return false;
    }

    public void logoutUser() throws SQLException {
        // Remove the user from LoggedInUser and from login session
        authRepo.deleteLoginSession(LoggedInUser.getInstance().getUserId());
    }

    public boolean checkLoggedInUser() throws SQLException {
        // Check User has an active login session or not
        Optional<LoginSession> loginSession = authRepo.getLoginSession(LoggedInUser.getInstance().getUserId());
        return loginSession.isPresent();
    }
}