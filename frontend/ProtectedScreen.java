package frontend;

import backend.AuthService;

import java.sql.SQLException;

public class ProtectedScreen extends Screen {
    @Override
    public void preScreenProcess() {
        try {
            if(!AuthService.getInstance().checkLoggedInUser())
                throw new RuntimeException();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}