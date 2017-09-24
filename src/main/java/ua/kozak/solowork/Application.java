package ua.kozak.solowork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kozak.solowork.dao.AuditoryDAO;
import ua.kozak.solowork.dao.UserDAO;

@Component
public class Application {

    @Autowired
    private AuditoryDAO auditoryDAO;

    public AuditoryDAO getAuditoryDAO() {
        return auditoryDAO;
    }

    public void setAuditoryDAO(AuditoryDAO auditoryDAO) {
        this.auditoryDAO = auditoryDAO;
    }
}
