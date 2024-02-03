package instahyre.assigment.app.service;

import org.springframework.http.HttpStatusCode;

public interface SpamService {
    void markSpam(String phoneNumber);
}
