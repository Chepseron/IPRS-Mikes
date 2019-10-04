package bean;

import db.Admins;
import db.Audittrails;
import db.Contacts;
import db.Iprs;
import db.Quotes;
import db.Roles;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
import javax.xml.ws.WebServiceRef;
import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.Base64;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.tempuri.ServerInterface;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ManagedBean(name = "iprs")
@SessionScoped
public class iprs implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/10.1.1.5_9004/IPRSServerwcf.wsdl")
    private ServerInterface service;
    @PersistenceContext(name = "com.mycompany_cardMaven_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private String IDNumber;
    private String passport;
    private String alien;
    private String SerialNumber;
    private String username;
    private String otp;
    private String password;
    private String newPassword;
    private String contactss;
    private String confirmPword;
    private Audittrails audit = new Audittrails();
    private List<Audittrails> auditList = new ArrayList();
    private Contacts contacts = new Contacts();
    private List<Contacts> contactsList = new ArrayList();
    private String[] selectedResponsibilities = new String[30];
    private HtmlDataTable htmlDataTable = new HtmlDataTable();
    private Iprs iprs = new Iprs();
    private List<Iprs> iprsList = new ArrayList();
    private Admins admins = new Admins();
    private Admins adminss = new Admins();
    private List<Admins> adminsList = new ArrayList();
    private Groups groups = new Groups();
    private List<Groups> GroupsList = new ArrayList();
    private Roles role = new Roles();
    private String citizenShip = new String();
    private String error = new String();
    private List<Roles> rolesList = new ArrayList();
    private List<Admins> adminsListBlank = new ArrayList();
    private List<Groups> groupsListBlank = new ArrayList();
    private List<Quotes> quotesList = new ArrayList();
    private ScheduleModel eventModel;
    private ScheduleModel lazyEventModel;
    private MeterGaugeChartModel assignedMeter;
    private MeterGaugeChartModel unassignedMeter;
    private boolean user = false;
    private boolean group = false;
    private boolean audittrails = false;
    private boolean iprsTransactionsView = false;
    private boolean iprsQuery = false;
    private boolean rolesView = false;
    private boolean userCreateView = false;

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
                admins = (Admins) em.createQuery("select a from Admins a where a.username = '" + username + "' and  a.password = '" + encryptingPass(password) + "'").getSingleResult();
                admins.setPassword(encryptingPass(getNewPassword()));
                getUtx().begin();
                getAdmins().setDateCreated(new java.util.Date());
                getAudit().setAction("changed password");
                admins.setStatus(0);
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

            admins.setPassword(encryptingPass("123456"));
            getUtx().begin();
            getAdmins().setDateCreated(new java.util.Date());
            getAudit().setAction("Reset password");
            getAudit().setUsername(admins.getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());
            getEm().merge(admins);
            getUtx().commit();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!success!", "You have successfully reset password to  " + admins.getUsername() + "'s password");
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
        System.out.println(adminss.getUsername());
        System.out.println(adminss.getFirstName());
        System.out.println(adminss.getEmailAdd());
        System.out.println(adminss.getLastName());
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }

            getUtx().begin();
            adminss.setDateCreated(new java.util.Date());
            adminss.setPassword(encryptingPass("123456"));
            adminss.setStatus(1);
            adminss.setUsername(adminss.getEmailAdd());

            getAudit().setUsername(getAdminss().getEmailAdd());
            getAudit().setDateperformed(new Date());
            getAudit().setAction("created an admin");
            getEm().persist(getAudit());
            getEm().persist(adminss);
            getUtx().commit();
            sendcredentials(adminss);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!success!", "You have successfully created " + getAdminss().getFirstName());
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

    public String encryptingPass(String args) {
        String str = new String(Base64.encodeBase64(args.getBytes()));
        return str;
    }

    public String decryptingPass(String args) {
        String str = new String(Base64.decodeBase64(args.getBytes()));
        return str;
    }

    public String createRole() {
        System.out.println("imefika hapa");
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }
            getUtx().begin();
            getAudit().setAction("created an admin");
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());
            getEm().persist(role);
            getUtx().commit();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!success!", "You have successfully created " + role.getRolename());
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

    public String editRole() {
        System.out.println("imefika hapa");
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }
            role = getEm().find(Roles.class, role.getIdroles());
            role.setRolename(role.getRolename());
            role.setCreatedOn(new java.util.Date());
            role.setCreatedBy(username);

            getUtx().begin();
            getAudit().setAction("Edied a role " + role.getRolename());
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());
            getEm().merge(role);
            getUtx().commit();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!success!", "You have successfully edited " + role.getRolename());
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

    public String CreateContacts() {
        System.out.println("imefika hapa");
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }
            getUtx().begin();
            getAudit().setAction("created an admin");
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            contacts.setCreatedBy(admins.getFirstName());
            contacts.setCreatedOn(new java.util.Date());
            getEm().persist(getAudit());
            getEm().persist(getContacts());
            getUtx().commit();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!success!", "You have successfully created " + getContacts().getContact());
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

    public String EditContacts() {
        System.out.println("imefika hapa");
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }
            contacts = getEm().find(Contacts.class, contacts.getIdcontacts());
            contacts.setCreatedBy(admins.getFirstName());
            contacts.setCreatedOn(new java.util.Date());

            getUtx().begin();
            getAudit().setAction("Updated a contact " + contacts.getContact());
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());
            getEm().merge(getContacts());
            getUtx().commit();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!success!", "You have successfully edited " + getContacts().getContact());
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
                getAudit().setAction("created a group");
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

    public String EditGroups() {

        System.out.println("imefika hapa");
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }
            groups = getEm().find(Groups.class, groups.getIdgroups());

            getUtx().begin();
            getAudit().setAction("Updated a contact " + contacts.getContact());
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());
            getEm().merge(getContacts());
            getUtx().commit();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!success!", "You have successfully edited " + getContacts().getContact());
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

    public String deleteRole() {
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }
            role = getEm().find(Roles.class, role.getIdroles());

            getUtx().begin();
            getAudit().setAction("deleted the role " + role.getRolename());
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());
            getEm().remove(role);
            getUtx().commit();

        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            ex.printStackTrace();

        }
        return "empTransactions.xhtml";
    }

    public String createIprs() {
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }
            getUtx().begin();
            getAdmins().setDateCreated(new java.util.Date());
            getAudit().setAction("created an iprs query");
            getAudit().setUsername(getUsername());
            getAudit().setDateperformed(new Date());
            getEm().persist(getAudit());
            getEm().persist(iprs);
            getUtx().commit();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!success!", "You have successfully created " + iprs.getFirstName());
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
            setAdmins((Admins) getEm().createQuery("select a from Admins a where a.username='" + getUsername() + "' and a.password = '" + encryptingPass(getPassword()) + "'").getSingleResult());
            GroupsList = em.createQuery("select g from Groups g where g.groupName = '" + admins.getGroupID().getGroupName() + "'").getResultList();

            if (getAdmins().getStatus() == 1) {
                return "changePassword.xhtml?faces-redirect=true";
            } else {

                String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                String Small_chars = "abcdefghijklmnopqrstuvwxyz";
                String numbers = "0123456789";
                // String symbols = "!@#$%^&*_=+-/.?<>)";
                String values = Capital_chars + Small_chars
                        + numbers;
                Random rndm_method = new Random();
                char[] password = new char[5];
                for (int i = 0; i < 5; i++) {
                    password[i] = values.charAt(rndm_method.nextInt(values.length()));
                }

                getUtx().begin();
                getAudit().setAction("Retrieved OTP");
                getAudit().setUsername(getUsername());
                getAudit().setDateperformed(new Date());
                admins.setOtp(password.toString());
                em.merge(admins);
                getEm().persist(getAudit());
                getUtx().commit();

                send(admins);
                return "otp.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            e.printStackTrace();
        }
        return null;
    }

//    public String getToken() {
//        String token = new String();
//        try {
//
//            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//
//                public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                }
//
//                public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                }
//
//            }};
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//            HostnameVerifier allHostsValid = new HostnameVerifier() {
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            };
//            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//            URL url = new URL("https://sms.metlec.co.ke");
//
//            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
//            con.addRequestProperty("grant_type", "token_request");
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type", "application/json; utf-8");
//            con.setRequestProperty("Accept", "application/json");
//            con.setDoOutput(true);
//
//            String tokenRequest = "{\n"
//                    + "\"username\": \"taajmoney\",\n"
//                    + "\"password\": \"taajmoney2020\"\n"
//                    + "}";
//
//            StringBuilder response = new StringBuilder();
//            try (OutputStream os = con.getOutputStream()) {
//                byte[] input = tokenRequest.getBytes("utf-8");
//                os.write(input, 0, input.length);
//                try (BufferedReader br = new BufferedReader(
//                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
//                    response = new StringBuilder();
//                    String responseLine = null;
//                    while ((responseLine = br.readLine()) != null) {
//                        response.append(responseLine.trim());
//                    }
//                    System.out.println(response.toString());
//                }
//            }
//            Object obj = new JSONParser().parse(response.toString());
//            JSONObject jo = (JSONObject) obj;
//            token = (String) jo.get("Token");
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return token;
//    }
//    public String send(Admins msg) {
//        String messageFromResponse = new String();
//        StringBuilder response = new StringBuilder();
//        try {
//
//            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//
//                public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                }
//
//                public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                }
//
//            }};
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//            HostnameVerifier allHostsValid = new HostnameVerifier() {
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            };
//
//            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//            URL url = new URL("https://sms.metlec.co.ke");
//
//            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
//            con.setRequestProperty("Authorization", "Bearer " + getToken());
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type", "application/json; utf-8");
//            con.setRequestProperty("Accept", "application/json");
//            con.setDoOutput(true);
//
//            String tokenRequest = "{\n"
//                    + "\"msisdn\": \"" + msg.getPhone() + "\",\n"
//                    + "\"message\": \"" + msg.getOtp() + "\",\n"
//                    + "\"sender_id\": \"" + new java.util.Date() + "\"\n"
//                    + "}";
//            try (OutputStream os = con.getOutputStream()) {
//                byte[] input = tokenRequest.getBytes("utf-8");
//                os.write(input, 0, input.length);
//                try (BufferedReader br = new BufferedReader(
//                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
//
//                    String responseLine = null;
//                    while ((responseLine = br.readLine()) != null) {
//                        response.append(responseLine.trim());
//                    }
//                    System.out.println(response.toString());
//                }
//            }
//
//            Object obj = new JSONParser().parse(response.toString());
//            JSONObject jo = (JSONObject) obj;
//            messageFromResponse = (String) jo.get("message");
//
//            return messageFromResponse;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return ex.getMessage();
//        }
//
//    }
    public void send(Admins args) {
        final String username = "taajiprs@gmail.com";
        final String password = "taaj1234";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("taajiprs@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(args.getEmailAdd())
            );
            message.setSubject("TAAJ IPRS ONE TIME PASSWORD");
            message.setText("Dear " + args.getFirstName() + " " + args.getLastName()
                    + "\n\nPlease Login to http://197.157.228.150:8080/iprs using the One time password sent below \n" + args.getOtp() + "\n\nKind Regards\n"
                    + "Taaj Money Transfer");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendcredentials(Admins args) {
        final String username = "taajiprs@gmail.com";
        final String password = "taaj1234";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("taajiprs@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(args.getEmailAdd())
            );
            message.setSubject("WELCOME TO TAAJ IPRS SEARCH");
            message.setText("Dear " + args.getFirstName() + " " + args.getLastName()
                    + "\n\nPlease Login to http://197.157.228.150:8080/iprs using the password and username sent below \n password : 123456\n username  : " + args.getUsername() + "\n\nKind Regards\n"
                    + "Taaj Money Transfer");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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

    public String confirmOtp() {
        try {
            setAdmins((Admins) getEm().createQuery("select a from Admins a where a.otp = '" + getOtp() + "'").getSingleResult());
            GroupsList = em.createQuery("select g from Groups g where g.groupName = '" + admins.getGroupID().getGroupName() + "'").getResultList();

            if (getAdmins().getStatus() == 1) {
                return "changePassword.xhtml?faces-redirect=true";
            } else {
                for (Groups g : GroupsList) {
                    if (g.getResponsibilities().equalsIgnoreCase("iprs")) {
                        setIprsQuery(true);
                    }
                    if (g.getResponsibilities().equalsIgnoreCase("users")) {
                        setUser(true);
                    }
                    if (g.getResponsibilities().equalsIgnoreCase("groups")) {
                        setGroup(true);
                    }
                    if (g.getResponsibilities().equalsIgnoreCase("transactions")) {
                        setIprsTransactionsView(true);
                    }
                    if (g.getResponsibilities().equalsIgnoreCase("audittrails")) {
                        setAudittrails(true);
                    }
                }

                getUtx().begin();
                getAudit().setAction("Logged into the system");
                getAudit().setUsername(getUsername());
                getAudit().setDateperformed(new Date());
                getEm().persist(getAudit());
                getUtx().commit();
                return "iprs.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error", e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            e.printStackTrace();
            return "homePage.xhtml?faces-redirect=true";
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

    public String sendOTP(String phoneNumber) {
        return "";
    }

    public String verifyByIDCard2() {
        try { // Call Web Service Operation
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }
            iprs.setPhoto("");

            //"zhassan", "Nimo-2018*", 
            ServerInterface services = new ServerInterface();
            System.out.println("id number = " + IDNumber);

            org.tempuri.IServiceIPRS port = services.getBasicHttpBindingIServiceIPRS();

            System.out.println(port.login("zhassan", "Nimo-2018*"));
            org.datacontract.schemas._2004._07.iprsmanager.HumanInfoFromIDCard result = port.getDataByIdCard("zhassan", "Nimo-2018*", IDNumber, SerialNumber);
            iprs = new Iprs();
            iprs.setIdnumber(result.getIDNumber().getValue());
            iprs.setCreatedOn(new java.util.Date().toString());
            iprs.setFirstName(result.getFirstName().getValue());
            iprs.setSublocation(result.getPlaceOfBirth().getValue());
            iprs.setDob(result.getDateOfBirth().getValue());
            iprs.setPlaceOfIssue(result.getPlaceOfLive().getValue());
            iprs.setSecondName(result.getOtherName().getValue());
            iprs.setLastName(result.getSurname().getValue());
            iprs.setGender(result.getGender().getValue());
            String imageString = new String(Base64.encodeBase64(result.getPhoto().getValue()));
            iprs.setPhoto(imageString);
            System.out.println(result.getErrorMessage().getValue());
            error = new String();
            error = result.getErrorMessage().getValue();
            utx.begin();
            em.persist(iprs);
            utx.commit();

            return result.toString();

        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("msgs", message);

            return ex.getMessage();
        }
    }

    public String verifyByPassport() {
        try { // Call Web Service Operation

            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }
            StringBuilder br = new StringBuilder();
            //"zhassan", "Nimo-2018*", 
            ServerInterface services = new ServerInterface();
            System.out.println("id number = " + passport);

            org.tempuri.IServiceIPRS port = services.getBasicHttpBindingIServiceIPRS();
            org.datacontract.schemas._2004._07.iprsmanager.HumanInfoFromPassport result = port.getDataByPassport("zhassan", "Nimo-2018*", passport, SerialNumber);
            System.out.println("Result = " + result.getFirstName().getValue());
            iprs = new Iprs();
            iprs.setIdnumber(passport);
            iprs.setCreatedOn(new java.util.Date().toString());
            iprs.setFirstName(result.getFirstName().getValue());
            iprs.setSublocation(result.getPlaceOfBirth().getValue());
            iprs.setDob(result.getDateOfBirth().getValue());
            iprs.setPlaceOfIssue(result.getPlaceOfLive().getValue());
            iprs.setSecondName(result.getOtherName().getValue());
            iprs.setLastName(result.getSurname().getValue());
            iprs.setGender(result.getGender().getValue());

            String imageString = new String(Base64.encodeBase64(result.getPhoto().getValue()));
            iprs.setPhoto(imageString);
            System.out.println(result.getErrorMessage().getValue());

            utx.begin();
            em.persist(iprs);
            utx.commit();

            return result.toString();

        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("msgs", message);
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    public String verifyByAlien() {
        try { // Call Web Service Operation

            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginInfoMessages", message);
                return "homePage.xhtml";
            }
            StringBuilder br = new StringBuilder();
            //"zhassan", "Nimo-2018*", 
            ServerInterface services = new ServerInterface();
            System.out.println("id number = " + alien);
            citizenShip = new String();
            setError(new String());
            org.tempuri.IServiceIPRS port = services.getBasicHttpBindingIServiceIPRS();
            org.datacontract.schemas._2004._07.iprsmanager.HumanInfoFromAlienCard result = port.getDataByAlienCard("zhassan", "Nimo-2018*", alien, SerialNumber);
            System.out.println("Result = " + result.getCitizenship().getValue());
            iprs = new Iprs();
            iprs.setIdnumber(alien);
            iprs.setCreatedOn(new java.util.Date().toString());
            iprs.setFirstName(result.getFirstName().getValue());
            iprs.setSublocation(result.getPlaceOfBirth().getValue());
            iprs.setDob(result.getDateOfBirth().getValue());
            iprs.setPlaceOfIssue(result.getPlaceOfLive().getValue());
            iprs.setSecondName(result.getOtherName().getValue());
            iprs.setLastName(result.getSurname().getValue());
            iprs.setGender(result.getGender().getValue());
            setCitizenShip(result.getCitizenship().getValue());

            String imageString = new String(Base64.encodeBase64(result.getPhoto().getValue()));
            setError(result.getErrorMessage().getValue());
            iprs.setPhoto(imageString);

            System.out.println(result.getErrorMessage().getValue());

            utx.begin();
            em.persist(iprs);
            utx.commit();

            return result.toString();

        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("msgs", message);
            ex.printStackTrace();
            return ex.getMessage();
        }
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
     * @return the iprsList
     */
    public List<Iprs> getIprsList() {
        iprsList = em.createQuery("select i from Iprs i").getResultList();
        return iprsList;
    }

    /**
     * @param iprsList the iprsList to set
     */
    public void setIprsList(List<Iprs> iprsList) {
        this.iprsList = iprsList;
    }

    /**
     * @return the iprs
     */
    public Iprs getIprs() {
        return iprs;
    }

    /**
     * @param iprs the iprs to set
     */
    public void setIprs(Iprs iprs) {
        this.iprs = iprs;
    }

    /**
     * @return the IDNumber
     */
    public String getIDNumber() {
        return IDNumber;
    }

    /**
     * @param IDNumber the IDNumber to set
     */
    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    /**
     * @return the SerialNumber
     */
    public String getSerialNumber() {
        return SerialNumber;
    }

    /**
     * @param SerialNumber the SerialNumber to set
     */
    public void setSerialNumber(String SerialNumber) {
        this.SerialNumber = SerialNumber;
    }

    /**
     * @return the role
     */
    public Roles getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Roles role) {
        this.role = role;
    }

    /**
     * @return the rolesList
     */
    public List<Roles> getRolesList() {
        rolesList = em.createQuery("select r from Roles r").getResultList();
        return rolesList;
    }

    /**
     * @param rolesList the rolesList to set
     */
    public void setRolesList(List<Roles> rolesList) {
        this.rolesList = rolesList;
    }

    /**
     * @return the iprsTransactionsView
     */
    public boolean isIprsTransactionsView() {
        return iprsTransactionsView;
    }

    /**
     * @param iprsTransactionsView the iprsTransactionsView to set
     */
    public void setIprsTransactionsView(boolean iprsTransactionsView) {
        this.iprsTransactionsView = iprsTransactionsView;
    }

    /**
     * @return the iprsQuery
     */
    public boolean isIprsQuery() {
        return iprsQuery;
    }

    /**
     * @param iprsQuery the iprsQuery to set
     */
    public void setIprsQuery(boolean iprsQuery) {
        this.iprsQuery = iprsQuery;
    }

    /**
     * @return the rolesView
     */
    public boolean isRolesView() {
        return rolesView;
    }

    /**
     * @param rolesView the rolesView to set
     */
    public void setRolesView(boolean rolesView) {
        this.rolesView = rolesView;
    }

    /**
     * @return the userCreateView
     */
    public boolean isUserCreateView() {
        return userCreateView;
    }

    /**
     * @param userCreateView the userCreateView to set
     */
    public void setUserCreateView(boolean userCreateView) {
        this.userCreateView = userCreateView;
    }

    /**
     * @return the citizenShip
     */
    public String getCitizenShip() {
        return citizenShip;
    }

    /**
     * @param citizenShip the citizenShip to set
     */
    public void setCitizenShip(String citizenShip) {
        this.citizenShip = citizenShip;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return the passport
     */
    public String getPassport() {
        return passport;
    }

    /**
     * @param passport the passport to set
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }

    /**
     * @return the alien
     */
    public String getAlien() {
        return alien;
    }

    /**
     * @param alien the alien to set
     */
    public void setAlien(String alien) {
        this.alien = alien;
    }

    /**
     * @return the otp
     */
    public String getOtp() {
        return otp;
    }

    /**
     * @param otp the otp to set
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }

    /**
     * @return the contacts
     */
    public Contacts getContacts() {
        return contacts;
    }

    /**
     * @param contacts the contacts to set
     */
    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    /**
     * @return the contactsList
     */
    public List<Contacts> getContactsList() {
        contactsList = em.createQuery("select c from Contacts c").getResultList();
        return contactsList;
    }

    /**
     * @param contactsList the contactsList to set
     */
    public void setContactsList(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    /**
     * @return the contactss
     */
    public String getContactss() {
        contactss = (String) em.createQuery("select c.contact from Contacts c").getSingleResult();
        return contactss;
    }

    /**
     * @param contactss the contactss to set
     */
    public void setContactss(String contactss) {
        this.contactss = contactss;
    }

    /**
     * @return the adminss
     */
    public Admins getAdminss() {
        return adminss;
    }

    /**
     * @param adminss the adminss to set
     */
    public void setAdminss(Admins adminss) {
        this.adminss = adminss;
    }

    /**
     * @param selectedResponsibilities the selectedResponsibilities to set
     */
}
