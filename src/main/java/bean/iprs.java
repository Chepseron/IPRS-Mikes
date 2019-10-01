package bean;

import db.Admins;
import db.Audittrails;
import db.Emp;
import db.Iprs;
import db.Quotes;
import db.Roles;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

@ManagedBean(name = "iprs")
@SessionScoped

public class iprs {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/10.1.1.5_9004/IPRSServerwcf.wsdl")
    private ServerInterface service;
    @PersistenceContext(name = "com.mycompany_cardMaven_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private String IDNumber;
    private String SerialNumber;
    private String username;
    private String password;
    private String newPassword;
    private String confirmPword;
    private Audittrails audit = new Audittrails();
    private List<Audittrails> auditList = new ArrayList();
    private String[] selectedResponsibilities = new String[30];
    private HtmlDataTable htmlDataTable = new HtmlDataTable();
    private Emp emp = new Emp();
    private List<Emp> EmpList = new ArrayList();
    private Iprs iprs = new Iprs();
    private List<Iprs> iprsList = new ArrayList();
    private Admins admins = new Admins();
    private List<Admins> adminsList = new ArrayList();
    private Groups groups = new Groups();
    private List<Groups> GroupsList = new ArrayList();
    private Roles role = new Roles();
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
                admins = (Admins) em.createQuery("select a from Admins a where a.username = '" + username + "' and  a.password = '" + password + "'").getSingleResult();
                admins.setPassword(getNewPassword());
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
            getAdmins().setPassword(encryptingPass("123456"));
            getAdmins().setStatus(1);
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

    public String encryptingPass(String args) {
        Base64.Encoder encoder = Base64.getEncoder();
        String str = encoder.encodeToString(args.getBytes());
        return str;
    }

    public String decryptingPass(String args) {
        Base64.Decoder decoder = Base64.getDecoder();
        String dStr = new String(decoder.decode(args));
        return dStr;
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

    public String deleteRole() {
        try {
            if (StringUtils.isEmpty(getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please login to the system");
                FacesContext context = FacesContext.getCurrentInstance();
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

    public String Query() {
        try {

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
            setAdmins((Admins) getEm().createQuery("select a from Admins a where a.username='" + getUsername() + "' and a.password = '" + decryptingPass(getPassword()) + "'").getSingleResult());
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

    public String verifyByIDCard2() {
        try { // Call Web Service Operation
            iprs.setPhoto("");
            System.out.println("id number = " + IDNumber);
            org.tempuri.IServiceIPRS port = service.getBasicHttpBindingIServiceIPRS();
            org.datacontract.schemas._2004._07.iprsmanager.HumanInfoFromIDCard result = port.getDataByIdCard("zhassan", "Nimo-2018*", IDNumber, SerialNumber);

            iprs.setIdnumber(Integer.parseInt(result.getIDNumber().getValue()));
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

            System.out.println(result.getPhoto().getValue());

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
            StringBuilder br = new StringBuilder();
            org.tempuri.IServiceIPRS port = service.getBasicHttpBindingIServiceIPRS();
            // TODO initialize WS operation arguments here

            org.datacontract.schemas._2004._07.iprsmanager.HumanInfoFromPassport result = port.getDataByPassport("zhassan", "Nimo-2018*", "24655940", "700982348");
            System.out.println("Result = " + result);

            br.append(result.getCitizenship().getValue() + "\n"
                    + result.getFirstName().getValue() + "\n"
                    + result.getClan().getValue() + "\n"
                    + result.getDateOfBirth().getValue() + "\n"
                    + result.getEthnicGroup().getValue() + "\n"
                    + result.getDateOfBirth().getValue() + "\n"
                    + result.getDateOfIssue().getValue() + "\n"
                    + result.getFamily().getValue() + "\n"
                    + result.getGender().getValue() + "\n"
                    + result.getIDNumber().getValue() + "\n"
                    + result.getOccupation().getValue() + "\n"
                    + result.getOtherName().getValue() + "\n"
                    + result.getPhoto().getValue() + "\n"
                    + result.getPin().getValue() + "\n"
                    + result.getSurname().getValue() + "\n"
                    + result.getErrorMessage().getValue() + "\n");

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "!SUCCESS!", br.toString());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("msgs", message);

            return result.toString();

        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("msgs", message);

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
     * @param selectedResponsibilities the selectedResponsibilities to set
     */
}
