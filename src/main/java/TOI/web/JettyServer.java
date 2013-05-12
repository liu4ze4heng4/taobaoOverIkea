package TOI.web;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: W.k
 * Date: 13-5-12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public class JettyServer {

   public static void main(String[] args){
       Server server = new Server();
       server.setThreadPool(createThreadPool());
        server.addConnector(createConnector());
       server.setHandler(createHandlers());
       server.setStopAtShutdown(true);
       try {
           server.start();
       } catch (Exception e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       }

   }

    private static ThreadPool createThreadPool() {
        // TODO: You should configure these appropriately
        // for your environment - this is an example only
        QueuedThreadPool _threadPool = new QueuedThreadPool();
        _threadPool.setMinThreads(5);
        _threadPool.setMaxThreads(9);
        return _threadPool;
    }

    private static SelectChannelConnector createConnector() {
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(8014);
        connector.setHost("127.0.0.1");
        connector.setAcceptors(4);
        connector.setForwarded(true);

        connector.setMaxIdleTime(60);
        connector.setLowResourcesMaxIdleTime(10);
        connector.setAcceptorPriorityOffset(10);
        connector.setAcceptQueueSize(10000);
        connector.setMaxBuffers(1024);
        connector.setRequestBufferSize(1000);
        connector.setRequestHeaderSize(1000);
        connector.setResponseBufferSize(1000);
        connector.setResponseHeaderSize(1000);
        connector.setReuseAddress(true);
//        connector.setSoLingerTime((int) config.soLingerTime.toMilliseconds());

        connector.setName("main-jetty");

        return connector;
    }

    private static HandlerCollection createHandlers() {

        System.setProperty("jetty.home", "/tmp/");

        WebAppContext _ctx = new WebAppContext();
        _ctx.setContextPath("/ikea");
        WebAppContext _ctx2 = new WebAppContext();
        _ctx2.setContextPath("//ikea");

        if (isRunningInShadedJar()) {
            _ctx.setWar(getShadedWarUrl());
            _ctx2.setWar(getShadedWarUrl());
        } else {
            _ctx.setWar("src/main/webapp");
            _ctx2.setWar("src/main/webapp");
        }

        List<Handler> _handlers = new ArrayList<Handler>();

        _handlers.add(_ctx);
        _handlers.add(_ctx2);


        HandlerList _contexts = new HandlerList();
        _contexts.setHandlers(_handlers.toArray(new Handler[2]));

//        RequestLogHandler _log = new RequestLogHandler();
//        _log.setRequestLog(createRequestLog());

        HandlerCollection _result = new HandlerCollection();
        _result.setHandlers(new Handler[]{_contexts});

        return _result;
    }

    private static RequestLog createRequestLog() {
        NCSARequestLog _log = new NCSARequestLog();

        File _logPath = new File(".");
        _logPath.getParentFile().mkdirs();

        _log.setFilename(_logPath.getPath());
        _log.setRetainDays(7);
        _log.setExtended(false);
        _log.setAppend(true);
        _log.setLogTimeZone("GMT");
        _log.setLogLatency(true);
        return _log;
    }

    //---------------------------
    // Discover the war path
    //---------------------------

    private static boolean isRunningInShadedJar() {
        try {
            Class.forName("");
            return false;
        } catch (ClassNotFoundException anExc) {
            return true;
        }
    }

    public static URL getResource(String aResource) {
        return Thread.currentThread().getContextClassLoader().getResource(aResource);
    }

    private static String getShadedWarUrl() {
        String _urlStr = getResource("webapp/WEB-INF/web.xml").toString();
        // Strip off "WEB-INF/web.xml"
        System.out.println("[AbstractServer.getShadedWarUrl]:url=" + _urlStr);
        return _urlStr.substring(0, _urlStr.length() - 15);
    }

}
