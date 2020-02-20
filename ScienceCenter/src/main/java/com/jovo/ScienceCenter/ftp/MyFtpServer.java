package com.jovo.ScienceCenter.ftp;


import com.jovo.ScienceCenter.certificate.CertificateService;
import com.jovo.ScienceCenter.config.SslConfig;
import com.jovo.ScienceCenter.ftp.dto.FTPInfo;
import com.jovo.ScienceCenter.ftp.dto.UserInfo;
import com.jovo.ScienceCenter.util.SslProperties;
import org.apache.ftpserver.*;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfiguration;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.usermanager.impl.*;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class MyFtpServer {
    private FtpServer ftpServer;
    private UserManager userManager;

    private static final int MAX_IDLE_TIME = 300;


    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.passive-ports}")
    private String passivePorts;

    @Value("${ftp.max-login}")
    private Integer maxLogin;

    @Value("${ftp.max-threads}")
    private Integer maxThreads;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.home-dir}")
    private Resource homeDir;

    @Value("${server.ssl.key-store}")
    private Resource keyStore;

    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;

    @Value("${server.ssl.key-alias}")
    private String keyAlias;

    @Value("${server.ssl.key-store-type}")
    private String keyStoreType;

    @Value("${server.ssl.trust-store}")
    private Resource trustStore;

    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;

    @Value("${server.ssl.trust-store-type}")
    private String trustStoreType;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SslProperties sslProperties;


    @EventListener(ApplicationReadyEvent.class)
    public void detectChangingOfKeyStoreOrTrustStore() {
        Path path = null;
        try {
            path = homeDir.getFile().toPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path directoryPath;
        Path singleFilePath;
        if(!Files.isDirectory(path)) {
            singleFilePath = path;
            directoryPath = path.getParent();
        }
        else {
            singleFilePath = null;
            directoryPath = path;
        }

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();


            directoryPath.register(
                    watchService,
                    //StandardWatchEventKinds.ENTRY_CREATE,
                    //StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY
                    );

            WatchKey key;

            while (true) {
                key = watchService.take(); // take je blokirajuca metoda
                if(key == null) continue;

                key.pollEvents().stream()
                        .map(event -> directoryPath.resolve((Path) event.context()).toFile())
                        .forEach(file -> {
                            String password;
                            String alias;
                            if ((file.getAbsolutePath().contains("keystore.jks"))) {
                                password = keyStorePassword;
                                alias = keyAlias;
                            }
                            else {
                                password = trustStorePassword;
                                alias = null;
                            }
                            boolean changed = certificateService.changePasswordOfKeyStore(file, alias,"changeme", password);

                            if (changed) {
                                try {
                                    updateSslContext();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                key.reset();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("realtimeScanLogs (END)");
    }

    private void updateSslContext() throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException,
                                            KeyStoreException, IOException, KeyManagementException {
        //ConfigurableApplicationContext configContext = (ConfigurableApplicationContext) applicationContext;
        //ConfigurableListableBeanFactory beanRegistry = configContext.getBeanFactory();
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

        SSLContext sslContext = new SSLContextBuilder()
                .loadKeyMaterial(new File(sslProperties.getKeyStoreFilePath()),
                        sslProperties.getKeyStorePassword(), sslProperties.getKeyStorePassword())
                //.loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .loadTrustMaterial(new File(sslProperties.getTrustStoreFilePath()), sslProperties.getTrustStorePassword())
                .build();

        Object oldSslContext = beanFactory.getBean("sslContext");
        if (oldSslContext != null) {
            beanFactory.destroyBean(oldSslContext);
        }

        // prvo smo obrisali oldSslContext pa sada ponovo dodajemo novi sslContext
        //beanRegistry.registerSingleton("sslContext", sslContext);
        beanFactory.initializeBean(sslContext, "sslContext");
        beanFactory.autowireBean(sslContext);
    }


    @PostConstruct
    private void start() {
        try {
            mkHomeDir(homeDir.getFile());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*try {
            createConfigFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // https://docs.spring.io/spring-integration/reference/html/ftp.html

        FtpServerFactory serverFactory = new FtpServerFactory();

        ConnectionConfigFactory connectionConfigFactory = new ConnectionConfigFactory();
        connectionConfigFactory.setAnonymousLoginEnabled(false);
        connectionConfigFactory.setMaxLogins(maxLogin);
        connectionConfigFactory.setMaxThreads(maxThreads);
        serverFactory.setConnectionConfig(connectionConfigFactory.createConnectionConfig());

        ListenerFactory listenerFactory = new ListenerFactory();

        listenerFactory.setPort(port);

        // define SSL configuration
        /*SslConfigurationFactory ssl = new SslConfigurationFactory();
        ssl.setSslProtocol("TLSv1.2");
        ssl.setClientAuthentication("NEED");

        try { ssl.setKeystoreFile(keyStore.getFile()); }
        catch (IOException e) { e.printStackTrace(); }
        ssl.setKeystorePassword(keyStorePassword);
        ssl.setKeyAlias(keyAlias);
        ssl.setKeystoreType(keyStoreType);

        try { ssl.setTruststoreFile(trustStore.getFile()); }
        catch (IOException e) { e.printStackTrace(); }
        ssl.setTruststorePassword(trustStorePassword);
        ssl.setTruststoreType(trustStoreType);

        // set the SSL configuration for the listener
        SslConfiguration sslConfiguration = ssl.createSslConfiguration();
        listenerFactory.setSslConfiguration(sslConfiguration);
        listenerFactory.setImplicitSsl(true);
        */
        DataConnectionConfigurationFactory dataConnectionConfigurationFactory = new DataConnectionConfigurationFactory();
        //dataConnectionConfigurationFactory.setImplicitSsl(true);
        //dataConnectionConfigurationFactory.setSslConfiguration(sslConfiguration);

        //listenerFactory.setDataConnectionConfiguration(dataConfigFactory.createDataConnectionConfiguration());

        if (!Objects.equals(passivePorts, "")) {
            //DataConnectionConfigurationFactory dataConnectionConfigurationFactory = new DataConnectionConfigurationFactory();

            dataConnectionConfigurationFactory.setPassivePorts(passivePorts);
            if (!(Objects.equals(host, "localhost") || Objects.equals(host, "127.0.0.1"))) {

                dataConnectionConfigurationFactory.setPassiveExternalAddress(host);
            }
            //listenerFactory.setDataConnectionConfiguration(dataConnectionConfigurationFactory.createDataConnectionConfiguration());
        }

        listenerFactory.setDataConnectionConfiguration(dataConnectionConfigurationFactory.createDataConnectionConfiguration());

        Map listenerMap = new HashMap();
        listenerMap.put("default", listenerFactory.createListener());
        serverFactory.addListener("default", listenerFactory.createListener());
        //serverFactory.setListeners(listenerMap);

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        //userManagerFactory.setFile(new File(usersFilePath));
        userManagerFactory.setAdminName(username);
        userManager = userManagerFactory.createUserManager();

        try {
            initUser();
        } catch (FtpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverFactory.setUserManager(userManager);

        ftpServer = serverFactory.createServer();
        try {
            ftpServer.start();
        } catch (FtpException e) {
            throw new RuntimeException(e);
        }
        System.out.println("FTP SERVER STARTOVAN NA:  " + host + ":" + port);
    }

    @PreDestroy
    private void stop() {
        if (ftpServer != null) {
            ftpServer.stop();
        }
    }

    private void initUser() throws FtpException, IOException {
        boolean exist = userManager.doesExist(username);
        // need to init user
        if (!exist) {
            List<Authority> authorities = new ArrayList<>();
            authorities.add(new WritePermission());
            authorities.add(new ConcurrentLoginPermission(0, 0));
            BaseUser user = new BaseUser();
            user.setName(username);
            user.setPassword(password);
            user.setHomeDirectory(homeDir.getFile().getAbsolutePath());
            user.setMaxIdleTime(MAX_IDLE_TIME);
            user.setAuthorities(authorities);
            userManager.save(user);
        }
    }


    public void setPassword(UserInfo userInfo) throws FtpException {
        String username = userManager.getAdminName();
        User savedUser = userManager.authenticate(new UsernamePasswordAuthentication(username, userInfo.getOldPassword()));
        BaseUser baseUser = new BaseUser(savedUser);
        baseUser.setPassword(userInfo.getPassword());
        userManager.save(baseUser);
    }


    /*public void setHomeDir(String homeDir) throws FtpException, IOException {
        User userInfo = userManager.getUserByName(userManager.getAdminName());
        BaseUser baseUser = new BaseUser(userInfo);
        mkHomeDir(homeDir);
        baseUser.setHomeDirectory(homeDir);
        userManager.save(baseUser);

        Properties ftpProperties = PropertiesHelper.getProperties(CONFIG_FILE_NAME);
        if (!homeDir.endsWith("/")) {
            homeDir += "/";
        }
        ftpProperties.setProperty("ftp.home-dir", homeDir);
        PropertiesHelper.saveProperties(ftpProperties, CONFIG_FILE_NAME);
    }*/


    public void setMaxDownloadRate(int maxDownloadRate) throws FtpException {
        int maxUploadRate = getFTPInfo().getMaxUploadRate();
        saveTransferRateInfo(maxUploadRate * 1024, maxDownloadRate * 1024);
    }


    public void setMaxUploadRate(int maxUploadRate) throws FtpException {
        int maxDownloadRate = getFTPInfo().getMaxDownloadRate();
        saveTransferRateInfo(maxUploadRate * 1024, maxDownloadRate * 1024);
    }


    private void saveTransferRateInfo(int maxUploadRate, int maxDownloadRate) throws FtpException {
        User userInfo = userManager.getUserByName(userManager.getAdminName());
        BaseUser baseUser = new BaseUser(userInfo);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new WritePermission());
        authorities.add(new TransferRatePermission(maxDownloadRate, maxUploadRate));
        baseUser.setAuthorities(authorities);
        userManager.save(baseUser);
    }


    public FTPInfo getFTPInfo() throws FtpException {
        User userInfo = userManager.getUserByName(userManager.getAdminName());
        TransferRateRequest transferRateRequest = (TransferRateRequest) userInfo
                .authorize(new TransferRateRequest());
        File homeDir = Paths.get(userInfo.getHomeDirectory()).toFile();
        long totalSpace = homeDir.getTotalSpace();
        long usedSpace = totalSpace - homeDir.getUsableSpace();

        return new FTPInfo(host, port, homeDir.getAbsolutePath(),
                transferRateRequest.getMaxDownloadRate() / 1024,
                transferRateRequest.getMaxUploadRate() / 1024,
                usedSpace, totalSpace);
    }

    private void mkHomeDir(File homeDir) throws Exception {
       if(!homeDir.exists()) {
           boolean success = homeDir.mkdir();
           if(!success) throw new Exception("Error during home directory!");
       }
    }

    /*private void createConfigFile() throws IOException {
        File configFile = new File(configFilePath);
        if (!configFile.exists()) {
            boolean result = configFile.createNewFile();
            if (!result) {
                System.out.println("Greska prilikom kreiranja " + configFilePath + "fajla!");
            }
        }

        File usersFile = new File(usersFilePath);
        if (!usersFile.exists()) {
            boolean result = usersFile.createNewFile();
            if (!result) {
                System.out.println("Greska prilikom kreiranja " + usersFilePath + "fajla!");
            }
        }
    }*/
}