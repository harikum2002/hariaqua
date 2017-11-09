package com.ericsson.research;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;

import com.microsoft.azure.servicebus.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.ServiceBusException;

/**
 * Servlet implementation class HelloWorldH
 */
@WebServlet("/HelloWorldH")
public class HelloWorldH extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorldH() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter printWriter  = response.getWriter();
		printWriter.println("Hello World!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter printWriter  = response.getWriter();
		printWriter.println("Hello World Post!");


		 URI uri = null;
		try {
			uri = new URI("sb://eappiotsens.servicebus.windows.net");
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

         String amqpServicePath = "datacollectoroutbox/publishers/ae92e22a-f0c0-487b-b62d-ed7ba8fa3329";

         String amqpSas = "SharedAccessSignature sr=sb%3a%2f%2feappiotsens.servicebus.windows.net%2fdatacollectoroutbox%2fpublishers%2fae92e22a-f0c0-487b-b62d-ed7ba8fa3329&sig=CKibtayK1Zwdct0GVOQUwIK%2f9zJdu40U%2f1LS7KblqgA%3d&se=4662254428&skn=SendAccessPolicy";




         ConnectionStringBuilder connStr = new ConnectionStringBuilder(uri,amqpServicePath, amqpSas);

         EventHubClient ehClient=null;
		try {
			ehClient = EventHubClient

			                                 .createFromConnectionStringSync(connStr.toString());
		} catch (ServiceBusException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



         /*

         * Get the values in < > from the registration ticket or from AppIoT.

         * Remove any backslashes in the URL's.

         */



         String ammoniaId = "5378019c-59b5-4f73-8c56-5f2e2fc9e227";

         String ammoniaValue = "0.13145"; // anything you want

         long timestamp = System.currentTimeMillis();



         String nitrateId = "a3f84325-41de-4ff6-9090-b1718066dcc6";

         String nitrateValue = "12.15272";

         String phId = "1e7cdf8c-920d-47ed-a431-f259e1496c9b";

         String phValue = "8.2";

         String conductivityId = "e4f1e29f-95d3-4885-989f-caec5478108a";

         String conductivityValue = "220";

         String doId = "6609c63a-d7a1-4c03-a199-2ff71dbf1bee";

         String doValue = "8.3";



            


                         Random random = new Random();

                         Random conductivityRandom = new Random(220);



                         nitrateValue = new Double(random.nextDouble() + 12.01563)

                                                         .toString();

                         phValue = new Double(random.nextDouble() + 8.2).toString();

                         conductivityValue = new Double(conductivityRandom.nextInt(226))

                                                         .toString();

                         doValue = new Double(random.nextDouble() + 8.3).toString();



                         String message = "[{id: \"" + ammoniaId + "\",v:[{m:["

                                                         + ammoniaValue + "],t:" + timestamp + "}]},{id: \""

                                                         + nitrateId + "\",v:[{m:[" + nitrateValue + "],t:"

                                                         + timestamp + "}]},{id: \"" + phId + "\",v:[{m:[" + phValue

                                                         + "],t:" + timestamp + "}]},{id: \"" + conductivityId

                                                         + "\",v:[{m:[" + conductivityValue + "],t:" + timestamp

                                                         + "}]},{id: \"" + doId + "\",v:[{m:[" + doValue + "],t:"

                                                         + timestamp + "}]}]";



                         byte[] payloadBytes = message.getBytes("UTF-8");

                         EventData sendEvent;

                         sendEvent = new EventData(payloadBytes);



                         // = new EventData(payloadBytes);



                         Map<String, String> properties = new HashMap<String, String>();

                         properties.put("DataCollectorId",

                                                         "ae92e22a-f0c0-487b-b62d-ed7ba8fa3329");

                         properties.put("SensorCollectionId",

                                                         "efd29db1-fe17-4cf5-a908-7a013cb66212");

                         properties.put("PayloadType", "Measurements");

                         sendEvent.setProperties(properties);



                         try {
							ehClient.sendSync(sendEvent); 
						} catch (ServiceBusException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

         

         ehClient.close();



         System.out.println("done");
		
	}

}
