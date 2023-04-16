package util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import modelos.Reserva;
import modelos.Huesped;

public class HotelUtil {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                //Configuración de la conexión a la base de datos
                Properties settings = new Properties();
                settings.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
                settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/nombreBaseDeDatos");
                settings.put("hibernate.connection.username", "usuario");
                settings.put("hibernate.connection.password", "contraseña");
                settings.put("hibernate.show_sql", "true");
                settings.put("hibernate.hbm2ddl.auto", "update");
                configuration.setProperties(settings);
                //Mapeo de las clases a las tablas
                configuration.addAnnotatedClass(Huesped.class);
                configuration.addAnnotatedClass(Reserva.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}