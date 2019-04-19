//package libraries.generalFunctions;
//
//import java.io.IOException;
//import javax.jms.Connection;
//import javax.jms.ConnectionFactory;
//import javax.jms.Queue;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.activemq.ActiveMQConnectionFactory;
//
//public class ConnectAMQ extends HttpServlet {
//    @Override
//    protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
//        try {
//            //created ConnectionFactory object for creating connection 
//            ConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61617");
//            //Establish the connection
//            Connection connection = factory.createConnection();
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            Queue queue = session.createQueue("Test");
//            //Added as a producer
//            javax.jms.MessageProducer producer = session.createProducer(queue);
//            // Create and send the message
//            TextMessage msg = session.createTextMessage();
//            msg.setText("TestMessage");
//            producer.send(msg);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
//}
