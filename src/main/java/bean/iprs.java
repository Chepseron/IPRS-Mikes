package bean;

import db.Accounts;
import db.Admins;
import db.Audittrails;
import db.Cards;
import db.Corebankingtable;
import db.Emp;
import db.Quotes;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.MeterGaugeChartModel;

@ManagedBean(name = "iprs")
@SessionScoped
public class iprs {

    @PersistenceContext(name = "com.mycompany_cardMaven_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private String username;
    private String password;
    private String newPassword;
    private String confirmPword;
    private Audittrails audit = new Audittrails();
    private List<Audittrails> auditList = new ArrayList();
    private String[] selectedResponsibilities = new String[30];
    private Accounts Accounts = new Accounts();
    private List<Accounts> AccountsList = new ArrayList();
    private HtmlDataTable htmlDataTable = new HtmlDataTable();
    private List<Cards> cardList = new ArrayList();
    private Emp emp = new Emp();
    private List<Emp> EmpList = new ArrayList();
    private Cards Cards = new Cards();
    private List<Cards> unAssignedcardList = new ArrayList();
    private Admins admins = new Admins();
    private List<Admins> adminsList = new ArrayList();

    private Groups groups = new Groups();
    private List<Groups> GroupsList = new ArrayList();

    private List<Admins> adminsListBlank = new ArrayList();
    private List<Cards> visaCardsList = new ArrayList();
    private List<Groups> groupsListBlank = new ArrayList();

    private Corebankingtable Corebankingtable = new Corebankingtable();
    private List<Corebankingtable> CorebankingtableList = new ArrayList();

    private List<Cards> emptyCardList = new ArrayList();
    private List<Quotes> quotesList = new ArrayList();
    private ScheduleModel eventModel;
    private ScheduleModel lazyEventModel;
    private MeterGaugeChartModel assignedMeter;
    private MeterGaugeChartModel unassignedMeter;
    private int brcode1;
    private int custno1;
    private int currency1;
    private int ledgercode1;
    private int subacccode1;

    private int brcode2;
    private int custno2;
    private int currency2;
    private int ledgercode2;
    private int subacccode2;

    private int brcode3;
    private int custno3;
    private int currency3;
    private int ledgercode3;
    private int subacccode3;

    private String acctnumber1;
    private String acctnumber2;
    private String acctnumber3;

    private boolean user = false;
    private boolean group = false;
    private boolean account = false;
    private boolean audittrails = false;
    private boolean empview = false;
    private boolean corebanking = false;
    private boolean accountsview = false;

    public iprs() {
    }

    @PostConstruct
    public void init() {
        try {
            createMeterGaugeModels();
            setEventModel(new DefaultScheduleModel());
            getEventModel().addEvent(new DefaultScheduleEvent("assign cards to accounts", previousDay8Pm(), previousDay11Pm()));
            getEventModel().addEvent(new DefaultScheduleEvent("assign cards to accounts", today1Pm(), today6Pm()));
            getEventModel().addEvent(new DefaultScheduleEvent("assign cards to accounts", nextDay9Am(), nextDay11Am()));
            getEventModel().addEvent(new DefaultScheduleEvent("assign cards to accounts", theDayAfter3Pm(), fourDaysLater3pm()));
            this.setLazyEventModel(new LazyScheduleModel() {
                public void loadEvents(Date start, Date end) {
                    Date random = iprs.this.getRandomDate(start);
                    addEvent(new DefaultScheduleEvent("Lazy Event 1", random, random));

                    random = iprs.this.getRandomDate(start);
                    addEvent(new DefaultScheduleEvent("Lazy Event 2", random, random));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final File LOCATION = new File("C:/uploads");

    public void handleFileUpload(FileUploadEvent event) {
        if (event.getFile() != null) {
            try {
                String prefix = FilenameUtils.getBaseName(event.getFile().getFileName());
                String suffix = FilenameUtils.getExtension(event.getFile().getFileName());
                File save = File.createTempFile(prefix + "-", "." + suffix, LOCATION);
                Files.write(save.toPath(), event.getFile().getContents());

                Path path = save.toPath();
                System.out.println("the path is +++++++++++++++=" + path.getFileName());
                utx.begin();
                em.createNativeQuery("LOAD DATA LOCAL INFILE 'C:/uploads/" + path.getFileName() + "' INTO TABLE  Emp FIELDS TERMINATED BY '|' LINES TERMINATED BY '\\n'").executeUpdate();
                utx.commit();

                FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                // Add success message here.
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(iprs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload(FileUploadEvent event) {

        if (getFile() != null) {
            try {

                File targetFolder = new File(getFile().getFileName());

                if (!targetFolder.exists()) {
                    targetFolder.mkdirs();
                }
                try {
                    InputStream inputStream = getFile().getInputstream();
                    OutputStream out = new FileOutputStream(new File(targetFolder, getFile().getFileName()));
                    int read = 0;
                    byte[] bytes = new byte[1024];
                    while ((read = inputStream.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    inputStream.close();
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                getUtx().begin();
                getEm().createNativeQuery("LOAD DATA LOCAL INFILE '" + targetFolder.getCanonicalPath() + "\\" + getFile() + "' INTO TABLE  Emp FIELDS TERMINATED BY '|' LINES TERMINATED BY '\\n'").executeUpdate();
                getUtx().commit();

                System.out.println(getFile());
            } catch (Exception ex) {
                ex.printStackTrace();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
            }
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "successful upload");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
        }
    }

    private void createMeterGaugeModels() {
        setUnassignedMeter(assigned());
        setAssignedMeter(unassigned());
    }

    public String changePassword() {

        try {

            if (!newPassword.equals(confirmPword)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Passwords dont match");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
            } else {
                admins = (Admins) em.createQuery("select a from Admins a where a.username = '" + username + "' and  a.password = '" + password + "'").getSingleResult();
                admins.setPassword(getNewPassword());
                getUtx().begin();
                getAdmins().setDateCreated(new java.util.Date());
                getAudit().setAction("changed password");
                getAudit().setUsername(username);
                getAudit().setDateperformed(new Date());
                getEm().persist(getAudit());
                getEm().merge(getAdmins());
                getUtx().commit();

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!success!", "You have successfully changed your password");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
            }
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "User credentials dont exist in the database");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            ex.printStackTrace();
        }
        return null;
    }

    public String resetPassword() {

        try {

            admins.setPassword("123456");
            getUtx().begin();
            getAdmins().setDateCreated(new java.util.Date());
            getAudit().setAction("Reset password");
            getAudit().setUsername(admins.getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());
            getEm().merge(admins);
            getUtx().commit();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!success!", "You have successfully changed " + admins.getUsername() + "'s password");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);

        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            ex.printStackTrace();
        }
        return null;
    }

    public String createAdmin() {

        System.out.println("imefika hapa");
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }
            getUtx().begin();
            getAdmins().setDateCreated(new java.util.Date());
            getAudit().setAction("created an admin");
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());
            getEm().persist(getAdmins());
            getUtx().commit();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!success!", "You have successfully created " + admins.getFirstName());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            ex.printStackTrace();
        }
        return null;
    }

    public String createGroup() {
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }

            for (int i = 0; i < getSelectedResponsibilities().length; i++) {

                getUtx().begin();
                getGroups().setDescription(getGroups().getDescription());
                getGroups().setGroupName(getGroups().getGroupName());
                getGroups().setResponsibilities(getSelectedResponsibilities()[i]);
                getAudit().setAction("created an group");
                getAudit().setUsername(getUsername());
                getAudit().setDateperformed(new Date());
                getEm().persist(getAudit());
                getEm().persist(getGroups());
                getUtx().commit();
            }
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            ex.printStackTrace();
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!SUCCESS!", "Successfully created a group");
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("loginInfoMessages", message);
        return null;
    }

    public String editAdmin() {
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }
            getEm().find(Admins.class, getAdmins().getAdminId());
            getUtx().begin();
            getAudit().setAction("Edited an admin");
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());
            getEm().merge(getAdmins());
            getUtx().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private MeterGaugeChartModel assigned() {
        List<Number> intervals = new ArrayList() {
        };
        for (int i = 0; i < 200; i++) {
            intervals.add(i + 20);
        }

        return new MeterGaugeChartModel(Integer.valueOf(130), intervals);
    }

    private MeterGaugeChartModel unassigned() {
        List<Number> intervals = new ArrayList() {
        };
        for (int i = 0; i < 200; i++) {
            intervals.add(i + 20);
        }
        return new MeterGaugeChartModel(Integer.valueOf(130), intervals);
    }

    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(5, (int) (Math.random() * 30.0D) + 1);

        return date.getTime();
    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(1), 1, calendar.get(5), 0, 0, 0);

        return calendar.getTime();
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 0, 0, 0);

        return calendar;
    }

    private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 1);
        t.set(5, t.get(5) - 1);
        t.set(10, 8);

        return t.getTime();
    }

    private Date previousDay11Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 1);
        t.set(5, t.get(5) - 1);
        t.set(10, 11);

        return t.getTime();
    }

    private Date today1Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 1);
        t.set(10, 1);

        return t.getTime();
    }

    private Date theDayAfter3Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(5, t.get(5) + 2);
        t.set(9, 1);
        t.set(10, 3);

        return t.getTime();
    }

    private Date today6Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 1);
        t.set(10, 6);

        return t.getTime();
    }

    private Date nextDay9Am() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 0);
        t.set(5, t.get(5) + 1);
        t.set(10, 9);

        return t.getTime();
    }

    private Date nextDay11Am() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 0);
        t.set(5, t.get(5) + 1);
        t.set(10, 11);

        return t.getTime();
    }

    private Date fourDaysLater3pm() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 1);
        t.set(5, t.get(5) + 4);
        t.set(10, 3);

        return t.getTime();
    }

    public String login() {
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }

            setAdmins((Admins) getEm().createQuery("select a from Admins a where a.username='" + getUsername() + "' and a.password = '" + getPassword() + "'").getSingleResult());

            GroupsList = em.createQuery("select g from Groups g where g.groupName = '" + admins.getGroupID().getGroupName() + "'").getResultList();
            for (Groups g : GroupsList) {
                if (g.getResponsibilities().equalsIgnoreCase("emp")) {
                    setEmpview(true);
                    setCorebanking(true);
                }
                if (g.getResponsibilities().equalsIgnoreCase("users")) {
                    setUser(true);

                }
                if (g.getResponsibilities().equalsIgnoreCase("groups")) {
                    setGroup(true);
                }
                if (g.getResponsibilities().equalsIgnoreCase("accounts")) {
                    setAccount(true);

                }
                if (g.getResponsibilities().equalsIgnoreCase("accountsview")) {
                    setAccountsview(true);
                }
                if (g.getResponsibilities().equalsIgnoreCase("audittrails")) {
                    setAudittrails(true);
                }
                if (g.getResponsibilities().equalsIgnoreCase("corebanking")) {
                    setCorebanking(true);

                }
            }
            getUtx().begin();
            getAudit().setAction("Logged into the system");
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());
            getUtx().commit();

            return "iprs.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please provide correct credentials");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            e.printStackTrace();
        }
        return null;
    }

    public String logout() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(false);
            httpSession.invalidate();
            return "homePage.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error", e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            e.printStackTrace();
        }
        return null;
    }

    public String deleteCoreBanking() {
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                return "homePage.xhtml";
            }
            getUtx().begin();
            getAudit().setAction("deleted the core banking records table");
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());

            getEm().createNativeQuery("truncate corebankingtable").executeUpdate();
            getUtx().commit();

        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            ex.printStackTrace();

        }
        return "empTransactions.xhtml";
    }

    public String deleteEmpTransactions() {
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                return "homePage.xhtml";
            }
            getUtx().begin();

            getAudit().setAction("deleted the EMP transactions table");
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());
            getEm().createNativeQuery("truncate emp").executeUpdate();
            getUtx().commit();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "Transactions deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            ex.printStackTrace();
            return "empTransactions.xhtml";
        }
        return null;
    }

    public String generateCoreBankingFile() {
        deleteCoreBanking();
        int count = 0;
        int count2 = 0;
        try {

            setEmpList((List<Emp>) getEm().createQuery("SELECT e FROM Emp e").getResultList());
            for (Emp em2 : getEmpList()) {

                setAccountsList((List<Accounts>) getEm().createQuery("SELECT a FROM Accounts a WHERE a.cardsID.cardnumber LIKE '" + em2.getPan() + "%'").getResultList());
                for (Accounts acct : getAccountsList()) {

                    if (em2.getOriginalMessageType().equalsIgnoreCase("05   ") && em2.getAmountIndicator().equals("DB")) {

                        if (acct.getLedgerCode() == 5772) {
                            System.out.println("Ledger code " + acct.getLedgerCode() + " card number " + em2.getPan() + " amount indicator " + em2.getAmountIndicator() + " origin message type " + em2.getOriginalMessageType());
                            if (StringUtils.isEmpty(getUsername())) {
                                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                                FacesContext context = FacesContext.getCurrentInstance();
                                return "homePage.xhtml";
                            }
                            getUtx().begin();

                            getCorebankingtable().setBatchcode("700");
                            getCorebankingtable().setBatchcode2("700");
                            getCorebankingtable().setBrcode(String.valueOf(acct.getBrcode()));
                            getCorebankingtable().setCurrency(String.valueOf(acct.getCurrency()));
                            getCorebankingtable().setCustomernumber(String.valueOf(acct.getCustomercode()));
                            getCorebankingtable().setLedgercode(String.valueOf(acct.getLedgerCode()));
                            getCorebankingtable().setNarration(em2.getTerminalLocation());
                            getCorebankingtable().setSubacccode(String.valueOf(acct.getSubacctcode()));
                            getCorebankingtable().setTrantype("1");
                            getCorebankingtable().setAmount(em2.getBillingAmount() / 100);
                            getAudit().setAction("Generated a DR in the core banking table");
                            getAudit().setUsername(getUsername());
                            getAudit().setDateperformed(new Date());
                            getEm().persist(getAudit());
                            getEm().persist(getCorebankingtable());
                            getUtx().commit();
                        }

                    }

                    if (em2.getOriginalMessageType().equalsIgnoreCase("RLDV ") && em2.getAmountIndicator().equals("CR")) {

                        if (acct.getLedgerCode() == 5773) {
                            if (StringUtils.isEmpty(getUsername())) {
                                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                                FacesContext context = FacesContext.getCurrentInstance();
                                return "homePage.xhtml";
                            }
                            getUtx().begin();

                            getCorebankingtable().setBatchcode("700");
                            getCorebankingtable().setBatchcode2("700");
                            getCorebankingtable().setBrcode(String.valueOf(acct.getBrcode()));
                            getCorebankingtable().setCurrency(String.valueOf(acct.getCurrency()));
                            getCorebankingtable().setCustomernumber(String.valueOf(acct.getCustomercode()));
                            getCorebankingtable().setLedgercode(String.valueOf(acct.getLedgerCode()));
                            getCorebankingtable().setNarration(em2.getTerminalLocation());
                            getCorebankingtable().setSubacccode(String.valueOf(acct.getSubacctcode()));
                            getCorebankingtable().setTrantype("2");
                            getCorebankingtable().setAmount(em2.getBillingAmount() / 100);
                            getAudit().setAction("Generated a DR in the core banking table");
                            getAudit().setUsername(getUsername());
                            getAudit().setDateperformed(new Date());
                            getEm().persist(getAudit());
                            getEm().persist(getCorebankingtable());
                            getUtx().commit();
                        }

                        if (acct.getLedgerCode() == 5772) {
                            if (StringUtils.isEmpty(getUsername())) {
                                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                                FacesContext context = FacesContext.getCurrentInstance();
                                return "homePage.xhtml";
                            }
                            getUtx().begin();
                            getCorebankingtable().setBatchcode("700");
                            getCorebankingtable().setBatchcode2("700");
                            getCorebankingtable().setBrcode(String.valueOf(acct.getBrcode()));
                            getCorebankingtable().setCurrency(String.valueOf(acct.getCurrency()));
                            getCorebankingtable().setCustomernumber(String.valueOf(acct.getCustomercode()));
                            getCorebankingtable().setLedgercode(String.valueOf(acct.getLedgerCode()));
                            getCorebankingtable().setNarration(em2.getTerminalLocation());
                            getCorebankingtable().setSubacccode(String.valueOf(acct.getSubacctcode()));
                            getCorebankingtable().setTrantype("1");
                            getCorebankingtable().setAmount(em2.getBillingAmount() / 100);
                            getAudit().setAction("Generated a DR in the core banking table");
                            getAudit().setUsername(getUsername());
                            getAudit().setDateperformed(new Date());
                            getEm().persist(getAudit());
                            getEm().persist(getCorebankingtable());
                            getUtx().commit();
                        }

                    }

                    if (em2.getOriginalMessageType().equalsIgnoreCase("RLOD ") && em2.getAmountIndicator().equals("DB")) {
                        if (acct.getLedgerCode() == 5773) {
                            if (StringUtils.isEmpty(getUsername())) {
                                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                                FacesContext context = FacesContext.getCurrentInstance();
                                return "homePage.xhtml";
                            }
                            getUtx().begin();
                            getCorebankingtable().setBatchcode("700");
                            getCorebankingtable().setBatchcode2("700");
                            getCorebankingtable().setBrcode(String.valueOf(acct.getBrcode()));
                            getCorebankingtable().setCurrency(String.valueOf(acct.getCurrency()));
                            getCorebankingtable().setCustomernumber(String.valueOf(acct.getCustomercode()));
                            getCorebankingtable().setLedgercode(String.valueOf(acct.getLedgerCode()));
                            getCorebankingtable().setNarration(em2.getTerminalLocation());
                            getCorebankingtable().setSubacccode(String.valueOf(acct.getSubacctcode()));
                            getCorebankingtable().setTrantype("1");
                            getCorebankingtable().setAmount(em2.getBillingAmount() / 100);
                            getAudit().setAction("Generated a CR in the core banking table");
                            getAudit().setUsername(getUsername());
                            getAudit().setDateperformed(new Date());
                            getEm().persist(getAudit());
                            getEm().persist(getCorebankingtable());
                            getUtx().commit();
                        }
                        if (acct.getLedgerCode() == 5772) {
                            if (StringUtils.isEmpty(getUsername())) {
                                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                                FacesContext context = FacesContext.getCurrentInstance();
                                return "homePage.xhtml";
                            }
                            getUtx().begin();
                            getCorebankingtable().setBatchcode("700");
                            getCorebankingtable().setBatchcode2("700");
                            getCorebankingtable().setBrcode(String.valueOf(acct.getBrcode()));
                            getCorebankingtable().setCurrency(String.valueOf(acct.getCurrency()));
                            getCorebankingtable().setCustomernumber(String.valueOf(acct.getCustomercode()));
                            getCorebankingtable().setLedgercode(String.valueOf(acct.getLedgerCode()));
                            getCorebankingtable().setNarration(em2.getTerminalLocation());
                            getCorebankingtable().setSubacccode(String.valueOf(acct.getSubacctcode()));
                            getCorebankingtable().setTrantype("2");
                            getCorebankingtable().setAmount(em2.getBillingAmount() / 100);
                            getAudit().setAction("Generated a DR in the core banking table");
                            getAudit().setUsername(getUsername());
                            getAudit().setDateperformed(new Date());
                            getEm().persist(getAudit());
                            getEm().persist(getCorebankingtable());
                            getUtx().commit();
                        }
                    }

                    if (em2.getOriginalMessageType().equalsIgnoreCase("06   ") && em2.getAmountIndicator().equals("CR")) {
                        if (acct.getLedgerCode() == 5772) {
                            if (StringUtils.isEmpty(getUsername())) {
                                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                                FacesContext context = FacesContext.getCurrentInstance();
                                return "homePage.xhtml";
                            }
                            // System.out.println("Ledger code " + acct.getLedgerCode() + " card number " + em2.getPan() + " amount indicator " + em2.getAmountIndicator() + " origin message type " + em2.getOriginalMessageType());
                            getUtx().begin();
                            getCorebankingtable().setBatchcode("700");
                            getCorebankingtable().setBatchcode2("700");
                            getCorebankingtable().setBrcode(String.valueOf(acct.getBrcode()));
                            getCorebankingtable().setCurrency(String.valueOf(acct.getCurrency()));
                            getCorebankingtable().setCustomernumber(String.valueOf(acct.getCustomercode()));
                            getCorebankingtable().setLedgercode(String.valueOf(acct.getLedgerCode()));
                            getCorebankingtable().setNarration(em2.getTerminalLocation());
                            getCorebankingtable().setSubacccode(String.valueOf(acct.getSubacctcode()));
                            getCorebankingtable().setTrantype("2");
                            getCorebankingtable().setAmount(em2.getBillingAmount() / 100);
                            getAudit().setAction("Generated a CR in the core banking table");
                            getAudit().setUsername(getUsername());
                            getAudit().setDateperformed(new Date());
                            getEm().persist(getAudit());
                            getEm().persist(getCorebankingtable());
                            getUtx().commit();
                        }
                    }
                    count2++;
                }
                count++;
            }

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "!SUCCESS!", "generated the core banking file click on the xls button to download your file thank you ");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("accountassign", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("accountassign", success);
            return "empTransactions.xhtml";
        }

    }

    public String assignAccount() {
        try {

            getEm().find(Cards.class, getCards().getCardnumber());
            getUtx().begin();
            getAccounts().setAcctype(1);
            getAccounts().setBrcode(getBrcode1());
            getAccounts().setCurrency(getCurrency1());
            getAccounts().setCustomercode(getCustno1());
            getAccounts().setLedgerCode(getLedgercode1());
            getAccounts().setSubacctcode(getSubacccode1());
            getAccounts().setCardsID(getCards());
            getEm().persist(getAccounts());
            getUtx().commit();

            getUtx().begin();
            getAccounts().setAcctype(2);
            getAccounts().setBrcode(getBrcode2());
            getAccounts().setCurrency(getCurrency2());
            getAccounts().setCustomercode(getCustno2());
            getAccounts().setLedgerCode(getLedgercode2());
            getAccounts().setSubacctcode(getSubacccode2());
            getAccounts().setCardsID(getCards());
            getEm().persist(getAccounts());
            getUtx().commit();

            getUtx().begin();
            getAccounts().setAcctype(3);
            getAccounts().setBrcode(getBrcode3());
            getAccounts().setCurrency(getCurrency3());
            getAccounts().setCustomercode(getCustno3());
            getAccounts().setLedgerCode(getLedgercode3());
            getAccounts().setSubacctcode(getSubacccode3());
            getAccounts().setCardsID(getCards());
            getEm().persist(getAccounts());
            getUtx().commit();

            getCards().setAssigned("1");
            getCards().setAcctnumber(getAcctnumber1() + "," + getAcctnumber2() + "," + getAcctnumber3());

            getAudit().setAction("assigned a card to an account");
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            getUtx().begin();
            getEm().merge(getCards());

            getEm().persist(getAudit());

            getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "!SUCCESS!", getCards().getCardnumber() + " has been assigned");
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage("accountassign", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("accountassign", success);
            return "insertAccount.xhtml";
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Quotes> getQuotesList() {
        quotesList = getEm().createQuery("SELECT  q from Quotes q").getResultList();
        return quotesList;
    }

    public void setQuotesList(List<Quotes> quotesList) {
        this.quotesList = quotesList;
    }

    public Cards getCards() {
        return Cards;
    }

    public void setCards(Cards Cards) {
        this.Cards = Cards;
    }

    public List<Cards> getCardList() {
        cardList = getEm().createQuery("SELECT c FROM Cards c WHERE (c.cardnumber LIKE '517322%' OR c.cardnumber LIKE '517314%' OR c.cardnumber LIKE '515450%'  OR c.cardnumber LIKE '417304%') AND c.assigned = '1'").getResultList();

        return cardList;
    }

    public void setCardList(List<Cards> cardList) {
        this.cardList = cardList;
    }

    public List<Cards> getEmptyCardList() {
        return emptyCardList;
    }

    public void setEmptyCardList(List<Cards> emptyCardList) {
        this.emptyCardList = emptyCardList;
    }

    public MeterGaugeChartModel getAssignedMeter() {
        return assignedMeter;
    }

    public void setAssignedMeter(MeterGaugeChartModel assignedMeter) {
        this.assignedMeter = assignedMeter;
    }

    public MeterGaugeChartModel getUnassignedMeter() {
        return unassignedMeter;
    }

    public void setUnassignedMeter(MeterGaugeChartModel unassignedMeter) {
        this.unassignedMeter = unassignedMeter;
    }

    public List<Cards> getUnAssignedcardList() {
        unAssignedcardList = getEm().createQuery("select c from Cards c WHERE  (c.cardnumber LIKE '517322%' OR c.cardnumber LIKE '517314%' OR c.cardnumber LIKE '515450%') AND c.assigned = '0'").getResultList();
        return unAssignedcardList;
    }

    public void setUnAssignedcardList(List<Cards> unAssignedcardList) {
        this.unAssignedcardList = unAssignedcardList;
    }

    public String getAcctnumber1() {
        return acctnumber1;
    }

    public void setAcctnumber1(String acctnumber1) {
        this.acctnumber1 = acctnumber1;
    }

    public String getAcctnumber2() {
        return acctnumber2;
    }

    public void setAcctnumber2(String acctnumber2) {
        this.acctnumber2 = acctnumber2;
    }

    public String getAcctnumber3() {
        return acctnumber3;
    }

    public void setAcctnumber3(String acctnumber3) {
        this.acctnumber3 = acctnumber3;
    }

    public Audittrails getAudit() {
        return audit;
    }

    public void setAudit(Audittrails audit) {
        this.audit = audit;
    }

    public List<Audittrails> getAuditList() {
        auditList = getEm().createQuery("SELECT a FROM Audittrails a").getResultList();
        return auditList;
    }

    public void setAuditList(List<Audittrails> auditList) {
        this.auditList = auditList;
    }

    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }

    public List<Emp> getEmpList() {
        EmpList = getEm().createQuery("SELECT e FROM Emp e").getResultList();
        return EmpList;
    }

    public void setEmpList(List<Emp> EmpList) {
        this.EmpList = EmpList;
    }

    /**
     * @return the lazyEventModel
     */
    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    /**
     * @param lazyEventModel the lazyEventModel to set
     */
    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * @return the utx
     */
    public UserTransaction getUtx() {
        return utx;
    }

    /**
     * @param utx the utx to set
     */
    public void setUtx(UserTransaction utx) {
        this.utx = utx;
    }

    /**
     * @return the eventModel
     */
    public ScheduleModel getEventModel() {
        return eventModel;
    }

    /**
     * @param eventModel the eventModel to set
     */
    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    /**
     * @return the Accounts
     */
    public Accounts getAccounts() {
        return Accounts;
    }

    /**
     * @param Accounts the Accounts to set
     */
    public void setAccounts(Accounts Accounts) {
        this.Accounts = Accounts;
    }

    /**
     * @return the AccountsList
     */
    public List<Accounts> getAccountsList() {
        AccountsList = getEm().createQuery("SELECT a FROM Accounts a").getResultList();
        return AccountsList;
    }

    /**
     * @param AccountsList the AccountsList to set
     */
    public void setAccountsList(List<Accounts> AccountsList) {
        this.AccountsList = AccountsList;
    }

    /**
     * @return the brcode1
     */
    public int getBrcode1() {
        return brcode1;
    }

    /**
     * @param brcode1 the brcode1 to set
     */
    public void setBrcode1(int brcode1) {
        this.brcode1 = brcode1;
    }

    /**
     * @return the custno1
     */
    public int getCustno1() {
        return custno1;
    }

    /**
     * @param custno1 the custno1 to set
     */
    public void setCustno1(int custno1) {
        this.custno1 = custno1;
    }

    /**
     * @return the currency1
     */
    public int getCurrency1() {
        return currency1;
    }

    /**
     * @param currency1 the currency1 to set
     */
    public void setCurrency1(int currency1) {
        this.currency1 = currency1;
    }

    /**
     * @return the ledgercode1
     */
    public int getLedgercode1() {
        return ledgercode1;
    }

    /**
     * @param ledgercode1 the ledgercode1 to set
     */
    public void setLedgercode1(int ledgercode1) {
        this.ledgercode1 = ledgercode1;
    }

    /**
     * @return the subacccode1
     */
    public int getSubacccode1() {
        return subacccode1;
    }

    /**
     * @param subacccode1 the subacccode1 to set
     */
    public void setSubacccode1(int subacccode1) {
        this.subacccode1 = subacccode1;
    }

    /**
     * @return the brcode2
     */
    public int getBrcode2() {
        return brcode2;
    }

    /**
     * @param brcode2 the brcode2 to set
     */
    public void setBrcode2(int brcode2) {
        this.brcode2 = brcode2;
    }

    /**
     * @return the custno2
     */
    public int getCustno2() {
        return custno2;
    }

    /**
     * @param custno2 the custno2 to set
     */
    public void setCustno2(int custno2) {
        this.custno2 = custno2;
    }

    /**
     * @return the currency2
     */
    public int getCurrency2() {
        return currency2;
    }

    /**
     * @param currency2 the currency2 to set
     */
    public void setCurrency2(int currency2) {
        this.currency2 = currency2;
    }

    /**
     * @return the ledgercode2
     */
    public int getLedgercode2() {
        return ledgercode2;
    }

    /**
     * @param ledgercode2 the ledgercode2 to set
     */
    public void setLedgercode2(int ledgercode2) {
        this.ledgercode2 = ledgercode2;
    }

    /**
     * @return the subacccode2
     */
    public int getSubacccode2() {
        return subacccode2;
    }

    /**
     * @param subacccode2 the subacccode2 to set
     */
    public void setSubacccode2(int subacccode2) {
        this.subacccode2 = subacccode2;
    }

    /**
     * @return the brcode3
     */
    public int getBrcode3() {
        return brcode3;
    }

    /**
     * @param brcode3 the brcode3 to set
     */
    public void setBrcode3(int brcode3) {
        this.brcode3 = brcode3;
    }

    /**
     * @return the custno3
     */
    public int getCustno3() {
        return custno3;
    }

    /**
     * @param custno3 the custno3 to set
     */
    public void setCustno3(int custno3) {
        this.custno3 = custno3;
    }

    /**
     * @return the currency3
     */
    public int getCurrency3() {
        return currency3;
    }

    /**
     * @param currency3 the currency3 to set
     */
    public void setCurrency3(int currency3) {
        this.currency3 = currency3;
    }

    /**
     * @return the ledgercode3
     */
    public int getLedgercode3() {
        return ledgercode3;
    }

    /**
     * @param ledgercode3 the ledgercode3 to set
     */
    public void setLedgercode3(int ledgercode3) {
        this.ledgercode3 = ledgercode3;
    }

    /**
     * @return the subacccode3
     */
    public int getSubacccode3() {
        return subacccode3;
    }

    /**
     * @param subacccode3 the subacccode3 to set
     */
    public void setSubacccode3(int subacccode3) {
        this.subacccode3 = subacccode3;
    }

    /**
     * @return the Corebankingtable
     */
    public Corebankingtable getCorebankingtable() {
        return Corebankingtable;
    }

    /**
     * @param Corebankingtable the Corebankingtable to set
     */
    public void setCorebankingtable(Corebankingtable Corebankingtable) {
        this.Corebankingtable = Corebankingtable;
    }

    /**
     * @return the CorebankingtableList
     */
    public List<Corebankingtable> getCorebankingtableList() {
        CorebankingtableList = getEm().createQuery("SELECT c FROM Corebankingtable c").getResultList();
        return CorebankingtableList;
    }

    /**
     * @param CorebankingtableList the CorebankingtableList to set
     */
    public void setCorebankingtableList(List<Corebankingtable> CorebankingtableList) {
        this.CorebankingtableList = CorebankingtableList;
    }

    /**
     * @return the visaCardsList
     */
    public List<Cards> getVisaCardsList() {
        visaCardsList = getEm().createQuery("SELECT c FROM Cards c WHERE c.cardnumber LIKE '417304%' AND c.assigned = '0'").getResultList();
        return visaCardsList;
    }

    /**
     * @param visaCardsList the visaCardsList to set
     */
    public void setVisaCardsList(List<Cards> visaCardsList) {
        this.visaCardsList = visaCardsList;
    }

    /**
     * @return the admins
     */
    public Admins getAdmins() {
        return admins;
    }

    /**
     * @param admins the admins to set
     */
    public void setAdmins(Admins admins) {
        this.admins = admins;
    }

    /**
     * @return the adminsList
     */
    public List<Admins> getAdminsList() {
        adminsList = getEm().createQuery("select a from Admins a").getResultList();
        return adminsList;
    }

    /**
     * @param adminsList the adminsList to set
     */
    public void setAdminsList(List<Admins> adminsList) {
        this.adminsList = adminsList;
    }

    /**
     * @return the adminsListBlank
     */
    public List<Admins> getAdminsListBlank() {
        return adminsListBlank;
    }

    /**
     * @param adminsListBlank the adminsListBlank to set
     */
    public void setAdminsListBlank(List<Admins> adminsListBlank) {
        this.adminsListBlank = adminsListBlank;
    }

    /**
     * @return the groups
     */
    public Groups getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    /**
     * @return the GroupsList
     */
    public List<Groups> getGroupsList() {
        GroupsList = getEm().createQuery("select g from Groups g GROUP BY g.groupName").getResultList();
        return GroupsList;
    }

    /**
     * @param GroupsList the GroupsList to set
     */
    public void setGroupsList(List<Groups> GroupsList) {
        this.GroupsList = GroupsList;
    }

    public boolean isUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(boolean user) {
        this.user = user;
    }

    /**
     * @return the group
     */
    public boolean isGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(boolean group) {
        this.group = group;
    }

    /**
     * @return the account
     */
    public boolean isAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(boolean account) {
        this.account = account;
    }

    /**
     * @return the audittrails
     */
    public boolean isAudittrails() {
        return audittrails;
    }

    /**
     * @param audittrails the audittrails to set
     */
    public void setAudittrails(boolean audittrails) {
        this.audittrails = audittrails;
    }

    /**
     * @return the empview
     */
    public boolean isEmpview() {
        return empview;
    }

    /**
     * @param empview the empview to set
     */
    public void setEmpview(boolean empview) {
        this.empview = empview;
    }

    /**
     * @return the corebanking
     */
    public boolean isCorebanking() {
        return corebanking;
    }

    /**
     * @param corebanking the corebanking to set
     */
    public void setCorebanking(boolean corebanking) {
        this.corebanking = corebanking;
    }

    /**
     * @return the accountsview
     */
    public boolean isAccountsview() {
        return accountsview;
    }

    /**
     * @param accountsview the accountsview to set
     */
    public void setAccountsview(boolean accountsview) {
        this.accountsview = accountsview;
    }

    /**
     * @return the groupsListBlank
     */
    public List<Groups> getGroupsListBlank() {
        return groupsListBlank;
    }

    /**
     * @param groupsListBlank the groupsListBlank to set
     */
    public void setGroupsListBlank(List<Groups> groupsListBlank) {
        this.groupsListBlank = groupsListBlank;
    }

    /**
     * @return the selectedResponsibilities
     */
    public String[] getSelectedResponsibilities() {
        return selectedResponsibilities;
    }

    /**
     * @param selectedResponsibilities the selectedResponsibilities to set
     */
    public void setSelectedResponsibilities(String[] selectedResponsibilities) {
        this.selectedResponsibilities = selectedResponsibilities;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return the confirmPword
     */
    public String getConfirmPword() {
        return confirmPword;
    }

    /**
     * @param confirmPword the confirmPword to set
     */
    public void setConfirmPword(String confirmPword) {
        this.confirmPword = confirmPword;
    }

    /**
     * @return the htmlDataTable
     */
    public HtmlDataTable getHtmlDataTable() {
        return htmlDataTable;
    }

    /**
     * @param htmlDataTable the htmlDataTable to set
     */
    public void setHtmlDataTable(HtmlDataTable htmlDataTable) {
        this.htmlDataTable = htmlDataTable;
    }

    /**
     * @param selectedResponsibilities the selectedResponsibilities to set
     */
}
